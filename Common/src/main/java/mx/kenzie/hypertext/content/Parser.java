package mx.kenzie.hypertext.content;

import mx.kenzie.hypertext.internal.SanitiseParser;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@FunctionalInterface
public interface Parser {

    Parser SANITISER = new SanitiseParser();

    default void parse(OutputStream stream, Charset charset, String content) throws IOException {
        final String parsed = this.parse(content);
        stream.write(parsed.getBytes(charset));
    }

    String parse(String content);

    default boolean available() {
        return true;
    }

}
