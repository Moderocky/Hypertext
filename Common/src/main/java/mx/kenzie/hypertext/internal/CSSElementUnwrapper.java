package mx.kenzie.hypertext.internal;

import mx.kenzie.hypertext.SourceUnwrapper;
import mx.kenzie.hypertext.css.Rule;
import mx.kenzie.hypertext.element.Page;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSSElementUnwrapper implements SourceUnwrapper<Page>, AutoCloseable {

    protected final InputStream stream;
    protected final Reader reader;
    protected boolean expectClosingTag;
    int c;
    boolean block, rule;
    StringBuilder builder = new StringBuilder();

    public CSSElementUnwrapper(Reader reader, boolean expectClosingTag) {
        this(null, reader, expectClosingTag);
    }

    public CSSElementUnwrapper(InputStream stream, Reader reader, boolean expectClosingTag) {
        this.stream = stream;
        this.reader = reader;
        this.expectClosingTag = expectClosingTag;
    }

    public CSSElementUnwrapper(String string) {
        this(new StringReader(string), false);
    }

    public CSSElementUnwrapper(InputStream stream) {
        this(stream, new InputStreamReader(stream), false);
    }

    @Override
    public Page unwrap() throws IOException {
        final Page page = new Page();
        final List<Rule> current = new ArrayList<>();
        this.reader.mark(1);
        read:
        while ((c = reader.read()) != -1) {
            switch (c) {
                case '{':
                    if (!block && !rule) {
                        final String selector = builder.toString().trim();
                        this.builder = new StringBuilder();
                        current.addFirst(new Rule(selector));
                        this.block = true;
                        break;
                    }
                case '}':
                    if (block && !rule) {
                        final Rule rule = current.getFirst();
                        this.block = false;
                        page.child(rule);
                        break;
                    }
                case ';':
                    if (block && rule) {
                        final Rule rule = current.getFirst();
                        this.rule = false;
                        final String thing = builder.toString().trim();
                        final int index = thing.indexOf(':');
                        final String key = thing.substring(0, index).trim();
                        final String value = thing.substring(index + 1).trim();
                        rule.rule(key, value);
                        this.builder = new StringBuilder();
                        break;
                    }
                case '<':
                    if (!block && expectClosingTag) {
                        this.reader.reset(); // need to feed < back in.
                        break read;
                    }
                default:
                    builder.append((char) c);
                    if (block && !builder.toString().isBlank()) this.rule = true;
            }
            if (expectClosingTag) this.reader.mark(1);
        }
        return page;
    }

    @Override
    public void close() throws IOException {
        if (stream != null) this.stream.close();
    }

}
