package mx.kenzie.hypertext;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.autodoc.api.note.GenerateExample;
import mx.kenzie.autodoc.api.note.Ignore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Description("""
    An element that can be written directly to a stream or given to a writer.
    If no charset is given, the default charset should be used.
    """)
@FunctionalInterface
public interface Writable {
    
    @Ignore
    default void write(OutputStream stream) throws IOException {
        this.write(stream, Charset.defaultCharset());
    }
    
    /**
     * Write the element's content to the stream, using the given charset if necessary.
     * <p>
     * This can be used for String to byte array conversion:
     * ```java
     * stream.write(string.getBytes(charset));
     * ```
     */
    @GenerateExample
    void write(OutputStream stream, Charset charset) throws IOException;
    
    static Writable of(InputStream stream) {
        return new InputWritable(stream);
    }
    
}

record InputWritable(InputStream stream) implements Writable {
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.stream.transferTo(stream);
    }
    
}
