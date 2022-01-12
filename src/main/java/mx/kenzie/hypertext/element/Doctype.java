package mx.kenzie.hypertext.element;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.hypertext.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Description("""
    A special element for the irregular `<!DOCTYPE xyz>` tag.
    """)
public final class Doctype extends HTMElement implements Writable {
    
    public Doctype(String type) {
        super("!DOCTYPE " + type);
        this.finalise = true;
    }
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.write(stream, charset, START + tag + END);
    }
}
