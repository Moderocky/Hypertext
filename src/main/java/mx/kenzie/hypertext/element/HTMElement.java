package mx.kenzie.hypertext.element;

import mx.kenzie.autodoc.api.note.Ignore;
import mx.kenzie.hypertext.Navigator;
import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.content.Parser;
import mx.kenzie.hypertext.internal.StringBuilderOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

public class HTMElement implements Writable {
    protected static final String START = "<", END = ">", END_SINGLE = " />", CLOSE = "</";
    
    protected final String tag;
    protected final Collection<String> classes;
    protected final Map<String, String> properties;
    protected final List<Writable> children;
    protected boolean single;
    protected boolean finalise;
    
    public HTMElement(String tag) {
        this.tag = tag;
        this.children = new ArrayList<>(0);
        this.classes = new ArrayList<>(0);
        this.properties = new HashMap<>(0);
        this.finalise = false;
    }
    
    public HTMElement(String tag, Writable... children) {
        this.tag = tag;
        this.children = new ArrayList<>(Arrays.asList(children));
        this.classes = new ArrayList<>(0);
        this.properties = new HashMap<>(0);
        this.finalise = false;
    }
    
    public HTMElement id(String id) {
        return this.set("id", id);
    }
    
    public HTMElement set(String key, String value) {
        if (this.finalise) {
            final HTMElement element = this.clone();
            element.properties.put(key, value);
            return element;
        } else {
            this.properties.put(key, value);
            return this;
        }
    }
    
    @Ignore
    protected HTMElement clone() {
        final HTMElement element = new HTMElement(this.tag);
        return this.clone(element);
    }
    
    protected <Type extends HTMElement> Type clone(Type element) {
        element.classes.addAll(this.classes);
        element.children.addAll(this.children);
        element.properties.putAll(this.properties);
        element.single = this.single;
        element.finalise = false;
        return element;
    }
    
    @Override
    public String toString() {
        final StringBuilderOutputStream stream = new StringBuilderOutputStream();
        try {
            this.write(stream, Charset.defaultCharset());
        } catch (IOException e) {
            return "HTMElement[" + tag + "]";
        }
        return stream.toString();
    }
    
    @Ignore
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.open(stream, charset);
        this.body(stream, charset);
        this.close(stream, charset);
    }
    
    protected void open(OutputStream stream, Charset charset) throws IOException {
        this.write(stream, charset, START + tag);
        if (classes.size() > 0) {
            this.write(stream, charset, " ");
            this.write(stream, charset, "class=\"");
            for (final String thing : classes) {
                this.write(stream, charset, thing + ' ');
            }
            this.write(stream, charset, "\"");
        }
        if (properties.size() > 0) {
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                this.write(stream, charset, " ");
                this.write(stream, charset, entry.getKey());
                this.write(stream, charset, "=\"");
                this.write(stream, charset, entry.getValue());
                this.write(stream, charset, "\"");
            }
        }
        this.write(stream, charset, (single ? END_SINGLE : END));
    }
    
    protected void body(OutputStream stream, Charset charset) throws IOException {
        if (single) return;
        for (final Writable child : children) child.write(stream, charset);
    }
    
    protected void close(OutputStream stream, Charset charset) throws IOException {
        if (single) return;
        this.write(stream, charset, CLOSE + tag + END);
    }
    
    protected final void write(OutputStream stream, Charset charset, String string) throws IOException {
        stream.write(string.getBytes(charset));
    }
    
    public HTMElement write(Parser parser, String content) {
        if (this.single) return this;
        return this.write(parser.parse(content));
    }
    
    public HTMElement write(String content) {
        if (this.single) return this;
        return this.child(new StringElement(content));
    }
    
    public HTMElement child(Writable... children) {
        if (this.single) return this;
        final List<Writable> list;
        final HTMElement result;
        if (this.finalise) list = (result = this.clone()).children;
        else list = (result = this).children;
        for (final Writable child : children) {
            if (child instanceof HTMElement element && element.finalise) list.add(element.clone());
            else list.add(child);
        }
        return result;
    }
    
    public HTMElement classes(String... classes) {
        if (this.finalise) {
            final HTMElement element = this.clone();
            element.classes.addAll(Arrays.asList(classes));
            return element;
        } else {
            this.classes.addAll(Arrays.asList(classes));
            return this;
        }
    }
    
    public HTMElement finalise() {
        this.finalise = true;
        return this;
    }
    
    public HTMElement single() {
        if (this.finalise) {
            final HTMElement element = this.clone();
            element.single = true;
            return element;
        }
        this.single = true;
        return this;
    }
    
    protected List<HTMElement> getAllChildren() {
        final List<HTMElement> list = new ArrayList<>();
        for (final Writable child : this.children) {
            if (!(child instanceof HTMElement element)) continue;
            list.add(element);
            list.addAll(element.getAllChildren());
        }
        return list;
    }
    
    public Navigator navigate() {
        return new ElementNavigator(this);
    }
}
