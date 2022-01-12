package mx.kenzie.hypertext.content;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.autodoc.api.note.Ignore;
import mx.kenzie.hypertext.internal.MarkdownParser;
import mx.kenzie.hypertext.internal.SanitiseParser;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Description("""
    A content parser that converts raw input to formatted text.
    This can be used in the `write` function of elements.
    
    If the parser is unavailable, the [parse](method:parse(1)) method should return the raw, unformatted content.
    
    The parser always returns a text format even if its output is HTML.
    """)
@FunctionalInterface
public interface Parser {
    
    @Description("""
        A parser for markdown. If `commonmark` is unavailable this parser will not work.
        """)
    Parser MARKDOWN_PARSER = new MarkdownParser();
    @Description("""
        A simple HTML sanitiser that converts problem characters to their `&#..` format.
        
        This cannot sanitise all input, so a more precise version may be preferred.
        """)
    Parser SANITISER = new SanitiseParser();
    
    @Ignore
    default void parse(OutputStream stream, Charset charset, String content) throws IOException {
        final String parsed = this.parse(content);
        stream.write(parsed.getBytes(charset));
    }
    
    @Description("""
        The method to parse the raw content.
        
        This should return the parsed result.
        """)
    String parse(String content);
    
    @Description("""
        Whether this parser is available for use.
        
        If unavailable, the [parse](method:parse(1)) method should return the raw content.
        """)
    default boolean available() {
        return true;
    }
    
}
