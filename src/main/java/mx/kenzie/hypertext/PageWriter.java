package mx.kenzie.hypertext;

import mx.kenzie.hypertext.internal.FormattedOutputStream;
import mx.kenzie.hypertext.internal.StandardErrorHandler;
import mx.kenzie.hypertext.internal.StringBuilderOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class PageWriter implements AutoCloseable {
    
    public Charset charset = Charset.defaultCharset();
    protected OutputStream stream;
    protected ErrorHandler handler = new StandardErrorHandler();
    
    public PageWriter() {
        this(new StringBuilderOutputStream());
    }
    
    public PageWriter(OutputStream stream) {
        this.stream = stream;
    }
    
    public PageWriter(StringBuilder builder) {
        this(new StringBuilderOutputStream(builder));
    }
    
    public PageWriter(File file) {
        this(createStream(file));
    }
    
    private static FileOutputStream createStream(File file) {
        try {
            if (!file.exists()) file.createNewFile();
            return new FileOutputStream(file);
        } catch (IOException ex) {
            return null;
        }
    }
    
    public PageWriter format(String indent) {
        this.stream = new FormattedOutputStream(stream, charset, indent.getBytes(charset));
        return this;
    }
    
    public void write(Writable... writables) {
        for (final Writable writable : writables) {
            this.write(writable);
        }
    }
    
    public void write(Writable writable) {
        try {
            writable.write(stream, charset);
        } catch (IOException ex) {
            this.handler.handle(ex);
        }
    }
    
    @Override
    public void close() {
        try {
            this.stream.close();
        } catch (IOException ex) {
            this.handler.handle(ex);
        } finally {
            this.stream = null;
            this.charset = null;
            this.handler = null;
        }
    }
    
    public ErrorHandler getHandler() {
        return handler;
    }
    
    public void setHandler(ErrorHandler handler) {
        if (handler == null) return;
        this.handler = handler;
    }
    
}
