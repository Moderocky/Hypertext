package mx.kenzie.hypertext.internal;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class FormattedOutputStream extends OutputStream {

    protected final OutputStream stream;
    protected final Charset charset;
    protected final byte[] indent;
    protected final byte[] line;
    protected volatile int counter;

    public FormattedOutputStream(OutputStream stream, Charset charset, byte[] indent) {
        this.stream = stream;
        this.indent = indent;
        this.charset = charset;
        this.line = System.lineSeparator().getBytes(charset);
    }

    public synchronized void increment() {
        this.counter++;
    }

    public synchronized void decrement() {
        this.counter--;
    }

    public void writeLine() throws IOException {
        this.write(line);
        for (int i = 0; i < counter; i++) this.write(indent);
    }

    @Override
    public void write(int b) throws IOException {
        this.stream.write(b);
    }

    @Override
    public void write(byte @NotNull [] b) throws IOException {
        this.stream.write(b);
    }

    @Override
    public void write(byte @NotNull [] b, int off, int len) throws IOException {
        this.stream.write(b, off, len);
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

}
