package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.internal.FormattedOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;

public class PreElement extends HTMElement {

    public PreElement(String tag) {
        super(tag);
        this.inline = true;
    }

    public PreElement(String tag, Writable... children) {
        super(tag, children);
        this.inline = true;
    }

    @Override
    protected void body(OutputStream stream, Charset charset) throws IOException {
        if (children.isEmpty()) return;
        if (stream instanceof FormattedOutputStream format) {
            final SneakyStream sneaky = new SneakyStream(format);
            for (final Writable child : children) child.write(sneaky, charset);
        } else super.body(stream, charset);
    }

    @Override
    protected PreElement clone() {
        return super.clone(new PreElement(tag));
    }

    private static class SneakyStream extends OutputStream {
        private final OutputStream out;

        private SneakyStream(OutputStream out) {
            this.out = out;
        }

        @Override
        public void write(int b) throws IOException {
            this.out.write(b);
        }

        @Override
        public void write(@NotNull byte[] b, int off, int len) throws IOException {
            this.out.write(b, off, len);
        }

        @Override
        public void write(@NotNull byte[] b) throws IOException {
            this.out.write(b);
        }

        @Override
        public void flush() throws IOException {
            this.out.flush();
        }

    }

}
