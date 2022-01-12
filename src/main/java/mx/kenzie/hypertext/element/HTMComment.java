package mx.kenzie.hypertext.element;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.hypertext.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Description("""
    A special handler for the irregular comment tag.
    ```html
    <!-- comment here -->
    ```
    The child elements will be written inside.
    """)
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
