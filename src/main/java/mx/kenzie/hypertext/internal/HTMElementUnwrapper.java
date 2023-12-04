package mx.kenzie.hypertext.internal;

import mx.kenzie.hypertext.SourceUnwrapper;
import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.element.HTMElement;
import mx.kenzie.hypertext.element.Page;
import mx.kenzie.hypertext.element.StandardElements;
import mx.kenzie.hypertext.rebuilder.HTMLFormatError;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HTMElementUnwrapper implements SourceUnwrapper<Page>, AutoCloseable {
    
    private static final HTMElement[] STANDARD_ELEMENTS = getElements(StandardElements.class);
    
    protected final HTMElement[] available;
    protected final InputStream stream;
    protected final BufferedReader reader;
    protected HTMElement[] assembled;
    int i, brackets = 0, writing = 0;
    boolean close = false, building = false, singlet = false, escape = false;
    StringBuilder tag = new StringBuilder(),
        attribute = new StringBuilder(),
        contents = new StringBuilder();
    
    public HTMElementUnwrapper(String string) {
        this(STANDARD_ELEMENTS, new ByteArrayInputStream(string.getBytes()), Charset.defaultCharset());
    }
    
    public HTMElementUnwrapper(HTMElement[] available, InputStream stream, Charset charset) {
        this.available = available;
        this.stream = stream;
        this.reader = new BufferedReader(new InputStreamReader(stream, charset));
    }
    
    public HTMElementUnwrapper(InputStream stream) {
        this(STANDARD_ELEMENTS, stream, Charset.defaultCharset());
    }
    
    public HTMElementUnwrapper(InputStream stream, Charset charset) {
        this(STANDARD_ELEMENTS, stream, charset);
    }
    
    private static HTMElement[] getElements(Class<?> holder) {
        final List<HTMElement> list = new ArrayList<>();
        for (final Field field : holder.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!HTMElement.class.isAssignableFrom(field.getType())) continue;
            try {
                list.add(((HTMElement) field.get(null)));
            } catch (IllegalAccessException e) {
            }
        }
        return list.toArray(new HTMElement[0]);
    }
    
    public Page unwrap() throws IOException {
        final Page page = new Page();
        final List<HTMElement> current = new ArrayList<>();
        final List<String> attributes = new ArrayList<>();
        current.add(page);
        while ((i = reader.read()) != -1) {
            final char c = (char) i;
            switch (c) {
                case '<' -> {
                    if (writing == 2) break; // name="<>" is ok
                    check:
                    if (!contents.isEmpty()) { // write trailing contents: <p> blob <...
                        final String string = contents.toString();
                        if (string.isBlank()) break check;
                        current.get(0).write(string);
                    }
                    brackets++; // open <
                    building = true; // looking for tag name
                    close = false; // not a closing tag
                    writing = 0; // not looking for attributes yet
                    tag = new StringBuilder();
                    contents = new StringBuilder();
                    attribute = new StringBuilder();
                    attributes.clear();
                }
                case '>' -> {
                    if (writing == 2) break; // name="<>" is ok
                    brackets--;
                    if (brackets != 0)
                        throw new HTMLFormatError("Unmatched opening and closing `<>` brackets in tag: " + tag);
                    if (close) {
                        if (singlet) { // <br />
                            final HTMElement single = this.getOrCreate(tag.toString().trim());
                            this.handleAttributes(single, attributes);
                            current.get(0).child(single);
                            writing = 0;
                            singlet = false;
                        } else {
                            final String finished = tag.toString().trim();
                            if (!finished.equals(current.get(0).getTag())) {
                                throw new HTMLFormatError("Unmatched closing tag: " + tag);
                            }
                            current.remove(0);
                        }
                    } else {
                        final HTMElement element = this.getOrCreate(tag.toString().trim());
                        this.handleAttributes(element, attributes);
                        current.get(0).child(element);
                        current.add(0, element);
                        if (shouldBeSingle(current.get(0).getTag()))
                            current.remove(0);
                        if (element.getTag().equalsIgnoreCase("style")) {
                            final Page css = new CSSElementUnwrapper(reader, true).unwrap();
                            for (final Writable writable : css) element.child(writable);
                        }
                    }
                    building = false;
                    tag = null;
                }
                case ' ', '\t' -> {
                    if (building && !tag.toString().isBlank()) building = false; // <   html is ok ?
                    if (brackets == 0) contents.append(c);
                    if (writing > 0) attribute.append(c);
                }
                case '"' -> {
                    if (brackets == 0) contents.append(c);
                    else if (brackets > 0) {
                        if (writing > 0) attribute.append(c);
                        if (writing == 1) { // name=" <- here
                            writing++;
                        } else if (writing == 2) { // name="thing" <- here
                            writing = 0;
                            attributes.add(attribute.toString().trim());
                            attribute = new StringBuilder();
                        }
                    }
                }
                case '/' -> {
                    if (brackets > 0 && writing == 2) attribute.append(c);
                    else if (brackets == 0) contents.append(c);
                    else {
                        this.close = true;
                        if (tag != null && !tag.isEmpty()) singlet = true;
                    }
                }
                default -> {
                    if (building) tag.append(c);
                    if (brackets == 0) contents.append(c);
                    else if (brackets > 0 && !building) {
                        if (writing == 0) writing++;
                        attribute.append(c);
                    }
                }
            }
        }
        return page;
    }
    
    protected HTMElement getOrCreate(String tag) {
        if (!this.isValid(tag.trim())) throw new HTMLFormatError("Invalid tag name: '" + tag + "'");
        for (final HTMElement element : available) {
            if (element.getTag().equals(tag.trim())) return element.working();
        }
        return new HTMElement(tag.trim());
    }
    
    protected void handleAttributes(HTMElement element, List<String> attributes) {
        for (final String string : attributes) {
            this.handleAttribute(element, string.trim());
        }
        attributes.clear();
    }
    
    protected boolean shouldBeSingle(String tag) {
        for (final HTMElement element : STANDARD_ELEMENTS) {
            if (element.getTag().equals(tag.trim())) return element.isSingle();
        }
        return false;
    }
    
    protected boolean isValid(String tag) {
        return tag.matches("\\w+");
    }
    
    private void handleAttribute(HTMElement element, String string) {
        final int index = string.indexOf('=');
        if (string.indexOf('=') < 0) element.set(string, "");
        else {
            final String key = string.substring(0, index), text = string.substring(index + 1), value;
            value = text.substring(1, text.length() - 1); // remove ""
            if (key.equals("class")) {
                element.classes(value.trim().split("\\s+"));
            } else {
                element.set(key, value);
            }
        }
    }
    
    @Override
    public void close() throws IOException {
        this.stream.close();
    }
    
}
