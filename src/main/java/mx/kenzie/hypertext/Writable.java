package mx.kenzie.hypertext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

@FunctionalInterface
public interface Writable {
    
    static Writable of(InputStream stream) {
        return new InputWritable(stream);
    }
    
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
    void write(OutputStream stream, Charset charset) throws IOException;
    
}

record InputWritable(InputStream stream) implements Writable {
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.stream.transferTo(stream);
    }
    
}
