package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.internal.FormattedOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

record StringElement(String value) implements Writable {

    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        if (stream instanceof FormattedOutputStream format) {
            final String[] lines = value.split("\n");
            format.write(lines[0].getBytes(charset));
            for (int i = 1; i < lines.length; i++) {
                format.writeLine();
                format.write(lines[i].getBytes(charset));
            }
        } else stream.write(value.getBytes(charset));
    }

}
