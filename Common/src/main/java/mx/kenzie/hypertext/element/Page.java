package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.internal.FormattedOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Page extends HTMElement {

    public Page(Writable... children) {
        super(null, children);
    }

    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.body(stream, charset);
    }

    @Override
    protected void body(OutputStream stream, Charset charset) throws IOException {
        if (single) return;
        if (children.size() > 0) {
            for (final Writable child : children) {
                if (!inline && stream instanceof FormattedOutputStream format) format.writeLine();
                child.write(stream, charset);
            }
            if (!inline && stream instanceof FormattedOutputStream format) format.writeLine();
        }
    }

}
