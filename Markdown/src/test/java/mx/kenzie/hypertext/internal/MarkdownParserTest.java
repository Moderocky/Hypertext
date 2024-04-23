package mx.kenzie.hypertext.internal;

import mx.kenzie.hypertext.PageWriter;
import mx.kenzie.hypertext.content.Parser;
import org.junit.Test;

import static mx.kenzie.hypertext.element.StandardElements.*;
import static mx.kenzie.hypertext.element.StandardElements.DIV;
import static org.junit.Assert.*;

public class MarkdownParserTest {

    @Test
    public void simpleParser() {
        final String expected = """
            <!DOCTYPE html><html lang="en"><head><title>Markdown Parsing</title></head><body><div><p>markdown below!</p></div><div><p>My cool
            markdown.
            This is <code>code</code>.</p>
            <pre><code class="language-java">code block
            </code></pre>
            <p>This <em>is</em> <em>cool</em> :)</p>
            </div></body></html>""";
        final StringBuilder builder = new StringBuilder();
        try (final PageWriter writer = new PageWriter(builder)) {
            writer.write(
                DOCTYPE_HTML,
                HTML.set("lang", "en").child(
                    HEAD.child(
                        TITLE.write("Markdown Parsing")
                              ),
                    BODY.child(
                        DIV.child(
                            P.write("markdown below!")
                                 ),
                        DIV.write(MarkdownParser.MARKDOWN_PARSER, """
                            My cool
                            markdown.
                            This is `code`.
                            ```java
                            code block
                            ```
                            This _is_ *cool* :)
                            """)
                              )
                                            )
                        );
        }
        assert expected.contentEquals(builder);
    }

}