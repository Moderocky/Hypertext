package mx.kenzie.hypertext.test;

import mx.kenzie.hypertext.PageWriter;
import mx.kenzie.hypertext.content.Parser;
import mx.kenzie.hypertext.element.HTMElement;
import mx.kenzie.hypertext.element.MultiElement;
import org.junit.Test;

import static mx.kenzie.hypertext.element.StandardElements.*;

public class PageWriterTest {
    
    @Test
    public void simple() {
        final String expected = """
            <!DOCTYPE html><html lang="en"><head><title>My Page</title></head><body><div class="col-md-8 col-lg-4 "><p>hello! :)</p></div><div><p>goodbye! :)</p></div></body></html>""";
        final StringBuilder builder = new StringBuilder();
        try (final PageWriter writer = new PageWriter(builder)) {
            writer.write(
                DOCTYPE_HTML,
                HTML.child(
                    HEAD.child(
                        TITLE.write("My Page")
                    ),
                    BODY.child(
                        DIV.classes("col-md-8", "col-lg-4").child(
                            P.write("hello! :)")
                        ),
                        DIV.child(
                            P.write("goodbye! :)")
                        )
                    )
                )
            );
        }
        assert expected.equals(builder.toString());
    }
    
    @Test
    public void metaTags() {
        final String expected = """
            <!DOCTYPE html><html lang="en"><head><title>Cool Page</title><meta name="description" content="My description." /><meta name="keywords" content="some, words" /></head><body><p>hello <a href="#">link</a> there</p><div><p>goodbye! :)</p></div></body></html>""";
        final StringBuilder builder = new StringBuilder();
        try (final PageWriter writer = new PageWriter(builder)) {
            writer.write(
                DOCTYPE_HTML,
                HTML.child(
                    HEAD.child(
                        TITLE.write("Cool Page"),
                        META.set("name", "description").set("content", "My description."),
                        META.set("name", "keywords").set("content", "some, words")
                    ),
                    BODY.child(
                        P.write("hello ").child(A.href("#").write("link")).write(" there"),
                        DIV.child(
                            P.write("goodbye! :)")
                        )
                    )
                )
            );
        }
        assert expected.equals(builder.toString());
    }
    
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
                HTML.child(
                    HEAD.child(
                        TITLE.write("Markdown Parsing")
                    ),
                    BODY.child(
                        DIV.child(
                            P.write("markdown below!")
                        ),
                        DIV.write(Parser.MARKDOWN_PARSER, """
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
        assert expected.equals(builder.toString());
    }
    
    @Test
    public void multiElements() {
        final StringBuilder builder = new StringBuilder();
        final HTMElement element = new MultiElement(
            DIV.classes("first"),
            DIV.classes("second"),
            DIV.classes("third")
        ).finalise();
        assert element instanceof MultiElement;
        assert element.child(P.write("hello")).toString()
            .equals("<div class=\"first \"><div class=\"second \"><div class=\"third \"><p>hello</p></div></div></div>");
        assert element.classes("bean").toString()
            .equals("<div class=\"first bean \"><div class=\"second bean \"><div class=\"third bean \"></div></div></div>");
        try (final PageWriter writer = new PageWriter(builder)) {
            writer.write(
                DOCTYPE_HTML,
                HTML.child(
                    BODY.child(
                        element.child(
                            P
                        )
                    )
                )
            );
        }
        assert builder.toString()
            .equals("<!DOCTYPE html><html lang=\"en\"><body><div class=\"first \"><div class=\"second \"><div class=\"third \"><p></p></div></div></div></body></html>");
    }
    
}
