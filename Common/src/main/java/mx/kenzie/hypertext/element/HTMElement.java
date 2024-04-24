package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Navigator;
import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.content.Parser;
import mx.kenzie.hypertext.internal.FormattedOutputStream;
import mx.kenzie.hypertext.internal.StringBuilderOutputStream;
import org.jetbrains.annotations.NotNull;
import org.valross.constantine.Constantive;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

public class HTMElement implements Iterable<Writable>, Writable, Constantive {

    protected static final String START = "<", END = ">", END_SINGLE = " />", CLOSE = "</";
    protected final String tag;
    protected final Collection<String> classes;
    protected final Map<String, String> properties;
    protected final List<Writable> children;
    protected boolean single;
    protected boolean finalise;
    protected boolean inline;

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

    public HTMElement inline() {
        final HTMElement element = this.working();
        element.inline = true;
        return element;
    }

    public HTMElement working() {
        if (this.finalise) return this.clone();
        else return this;
    }

    @SuppressWarnings({"RedundantSuppression", "CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected HTMElement clone() {
        final HTMElement element = new HTMElement(this.tag);
        return this.clone(element);
    }

    @Override
    public String toString() {
        final StringBuilderOutputStream stream = new StringBuilderOutputStream();
        try {
            this.write(stream, Charset.defaultCharset());
        } catch (IOException ex) {
            return "HTMElement[" + tag + "]";
        }
        return stream.toString();
    }

    protected <Type extends HTMElement> Type clone(Type element) {
        element.classes.addAll(this.classes);
        element.children.addAll(this.children);
        element.properties.putAll(this.properties);
        element.single = this.single;
        element.finalise = false;
        element.inline = this.inline;
        return element;
    }

    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.open(stream, charset);
        this.body(stream, charset);
        this.close(stream, charset);
    }

    protected void open(OutputStream stream, Charset charset) throws IOException {
        this.write(stream, charset, START + tag);
        final Map<String, String> properties = this.getEffectiveProperties();
        if (!properties.isEmpty()) {
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
        if (children.isEmpty()) return;
        if (stream instanceof FormattedOutputStream format) format.increment();
        for (final Writable child : children) {
            if (!inline && stream instanceof FormattedOutputStream format) format.writeLine();
            child.write(stream, charset);
        }
        if (stream instanceof FormattedOutputStream format) format.decrement();
        if (!inline && stream instanceof FormattedOutputStream format) format.writeLine();
    }

    protected void close(OutputStream stream, Charset charset) throws IOException {
        if (single) return;
        this.write(stream, charset, CLOSE + tag + END);
    }

    protected final void write(OutputStream stream, Charset charset, String string) throws IOException {
        stream.write(string.getBytes(charset));
    }

    protected Map<String, String> getEffectiveProperties() {
        final Map<String, String> map = new HashMap<>(properties.size() + 2);
        map.putAll(properties);
        if (!classes.isEmpty()) map.put("class", String.join(" ", classes));
        return map;
    }

    public HTMElement id(String id) {
        return this.set("id", id);
    }

    public HTMElement set(String key, String value) {
        final HTMElement element = this.working();
        element.properties.put(key, value);
        return element;
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
        if (this.single || children.length == 0) return this;
        final HTMElement result = this.working();
        final List<Writable> list = result.children;
        for (final Writable child : children) {
            if (child instanceof HTMElement element && element.finalise) list.add(element.working());
            else list.add(child);
        }
        return result;
    }

    public HTMElement classes(String... classes) {
        final HTMElement element = this.working();
        element.classes.addAll(Arrays.asList(classes));
        return element;
    }

    public HTMElement finalise() {
        this.finalise = true;
        return this;
    }

    public HTMElement single() {
        final HTMElement element = this.working();
        element.single = true;
        return element;
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

    public boolean isSingle() {
        return single;
    }

    public Navigator navigate() {
        return new ElementNavigator(this);
    }

    public String getTag() {
        return tag;
    }

    public Map<String, String> getProperties() {
        return new HashMap<>(properties);
    }

    public List<String> getClasses() {
        return new ArrayList<>(classes);
    }

    @NotNull
    @Override
    public Iterator<Writable> iterator() {
        return children.iterator();
    }

    @Override
    public ConstantElement constant() {
        return new ConstantElement(this.toString());
    }

}
