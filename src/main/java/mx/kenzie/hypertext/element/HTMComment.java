package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class HTMComment extends HTMElement implements Writable {
    
    public HTMComment(Writable... children) {
        super("comment", children);
    }
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.write(stream, charset, "<!--");
        this.body(stream, charset);
        this.write(stream, charset, "-->");
    }
}
