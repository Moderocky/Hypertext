package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;
import org.valross.constantine.RecordConstant;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public record ConstantElement(String content) implements Writable, RecordConstant {

    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        stream.write(content.getBytes(charset));
    }

}
