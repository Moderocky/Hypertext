package mx.kenzie.hypertext.internal;

import mx.kenzie.autodoc.api.note.Ignore;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Ignore
public class StringBuilderOutputStream extends OutputStream implements AutoCloseable {
    
    private StringBuilder builder;
    private Charset charset;
    
    public StringBuilderOutputStream() {
        this(new StringBuilder());
    }
    
    public StringBuilderOutputStream(StringBuilder builder) {
        this(builder, Charset.defaultCharset());
    }
    
    public StringBuilderOutputStream(StringBuilder builder, Charset charset) {
        this.builder = builder;
        this.charset = charset;
    }
    
    @Override
    public synchronized void write(int b) throws IOException {
        throw new IOException("This does not support writing individual bytes.");
    }
    
    @Override
    public synchronized void write(byte @NotNull [] bytes) {
        this.builder.append(new String(bytes, charset));
    }
    
    @Override
    public void close() {
        this.builder = null;
        this.charset = null;
    }
    
    public StringBuilder getBuilder() {
        return builder;
    }
    
    public Charset getCharset() {
        return charset;
    }
    
    @Override
    public String toString() {
        return builder.toString();
    }
}
