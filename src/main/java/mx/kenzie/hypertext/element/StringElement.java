package mx.kenzie.hypertext.element;

import mx.kenzie.autodoc.api.note.Ignore;
import mx.kenzie.hypertext.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Ignore
final class StringElement implements Writable {
    
    final String value;
    
    StringElement(String value) {
        this.value = value;
    }
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        stream.write(value.getBytes(charset));
    }
    
}
