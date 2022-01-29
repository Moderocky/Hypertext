package mx.kenzie.hypertext.element;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.autodoc.api.note.GenerateExample;
import mx.kenzie.autodoc.api.note.Ignore;
import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.internal.FormattedOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Description("""
    A utility element for storing an entire page tree.
    
    This adds no tags or extra functionality.
    """)
@GenerateExample
public class Page extends HTMElement {
    
    @Description("""
        Create a new writable page.
        The parameter elements will be written at the root of the page.
        
        Example input:
        ```java
        new Page(
            HEAD,
            BODY
        );
        ```
        Example output:
        ```html
        <head>
        </head>
        <body>
        </body>
        ```
        """)
    @GenerateExample
    public Page(Writable... children) {
        super(null, children);
    }
    
    @Ignore
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.body(stream, charset);
    }
    
    @Ignore
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
