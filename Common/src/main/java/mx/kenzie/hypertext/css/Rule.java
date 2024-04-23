package mx.kenzie.hypertext.css;

import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.element.HTMElement;
import mx.kenzie.hypertext.internal.FormattedOutputStream;
import mx.kenzie.hypertext.internal.StringBuilderOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

public class Rule implements Writable {

    protected final List<String> selectors;
    protected final Map<String, String> rules;

    public Rule(String selector) {
        this();
        this.selectors.add(selector);
    }

    protected Rule() {
        this.selectors = new ArrayList<>();
        this.rules = new HashMap<>();
    }

    public Rule(String... selectors) {
        this();
        this.selectors.addAll(Arrays.asList(selectors));
    }

    public Rule(Object... selectors) {
        this();
        for (final Object selector : selectors) {
            if (selector instanceof HTMElement element) {
                this.selectors.add(element.getTag());
            } else {
                this.selectors.add(selector.toString());
            }
        }
    }

    public static Rule exact(HTMElement element) {
        final StringBuilder builder = new StringBuilder();
        builder.append(element.getTag());
        for (final Map.Entry<String, String> entry : element.getProperties().entrySet()) {
            builder.append('[');
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
            builder.append(']');
        }
        for (final String string : element.getClasses()) {
            builder.append('.').append(string);
        }
        return new Rule(builder.toString());
    }

    public static Rule inside(HTMElement outer, HTMElement inner) {
        return new Rule(outer.getTag() + " " + inner.getTag());
    }

    public static Rule child(HTMElement parent, HTMElement child) {
        return new Rule(parent.getTag() + " > " + child.getTag());
    }

    public static Rule after(HTMElement before, HTMElement after) {
        return new Rule(before.getTag() + " + " + after.getTag());
    }

    public static Rule before(HTMElement before, HTMElement after) {
        return new Rule(after.getTag() + " ~ " + before.getTag());
    }

    public static Rule of(HTMElement before, TargetQualifier qualifier) {
        return new Rule(before.getTag() + qualifier);
    }

    public static Rule of(HTMElement element) {
        return new Rule(element.getTag());
    }

    public static Rule of(String before, TargetQualifier qualifier) {
        return new Rule(before + qualifier);
    }

    public static Rule of(TargetQualifier qualifier) {
        return new Rule(qualifier.toString());
    }

    public static Rule all(HTMElement... elements) {
        final List<String> list = new ArrayList<>();
        for (final HTMElement element : elements) {
            list.add(element.getTag());
        }
        return new Rule(String.join(", ", list));
    }

    public static Rule everything() {
        return new Rule("*");
    }

    @Override
    public String toString() {
        final StringBuilderOutputStream stream = new StringBuilderOutputStream();
        try {
            this.write(stream, Charset.defaultCharset());
        } catch (IOException e) {
            return "Rule[" + selectors + "]";
        }
        return stream.toString();
    }

    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.write(stream, charset, String.join(" ", selectors));
        this.write(stream, charset, " {");
        if (stream instanceof FormattedOutputStream format) {
            format.increment();
        }
        for (final Map.Entry<String, String> entry : rules.entrySet()) {
            if (stream instanceof FormattedOutputStream format) format.writeLine();
            this.write(stream, charset, entry.getKey());
            this.write(stream, charset, ": ");
            this.write(stream, charset, entry.getValue());
            this.write(stream, charset, ";");
        }
        if (stream instanceof FormattedOutputStream format) {
            format.decrement();
            format.writeLine();
        }
        this.write(stream, charset, "}");
    }

    protected final void write(OutputStream stream, Charset charset, String string) throws IOException {
        stream.write(string.getBytes(charset));
    }

    public Rule rule(String key, String value, boolean important) {
        if (important) return this.rule(key, value + " !important");
        else return this.rule(key, value);
    }

    public Rule rule(String key, String value) {
        this.rules.put(key, value);
        return this;
    }

}
