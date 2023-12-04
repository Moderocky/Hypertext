package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

record StringElement(String value) implements Writable {
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        stream.write(value.getBytes(charset));
    }
    
}
