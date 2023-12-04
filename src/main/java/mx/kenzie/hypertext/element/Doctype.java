package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.internal.FormattedOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public final class Doctype extends HTMElement implements Writable {
    
    public Doctype(String type) {
        super("!DOCTYPE " + type);
        this.finalise = true;
    }
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.write(stream, charset, START + tag + END);
        if (stream instanceof FormattedOutputStream format)
            format.writeLine();
    }
    
}
