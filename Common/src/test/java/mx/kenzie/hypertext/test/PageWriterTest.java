package mx.kenzie.hypertext.test;

import mx.kenzie.hypertext.PageWriter;
import mx.kenzie.hypertext.element.HTMElement;
import mx.kenzie.hypertext.element.MultiElement;
import org.junit.Test;

import static mx.kenzie.hypertext.element.StandardElements.*;

public class PageWriterTest {

    @Test
    public void simple() {
        final String expected = """
            <!DOCTYPE html><html lang="en"><head><title>My Page</title></head><body><div class="col-md-8 col-lg-4"><p>hello! :)</p></div><div><p>goodbye! :)</p></div></body></html>""";
        final StringBuilder builder = new StringBuilder();
        try (final PageWriter writer = new PageWriter(builder)) {
            writer.write(
                DOCTYPE_HTML,
                HTML.set("lang", "en").child(
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
        assert expected.contentEquals(builder);
    }

    @Test
    public void metaTags() {
        final String expected = """
            <!DOCTYPE html><html lang="en"><head><title>Cool Page</title><meta name="description" content="My description." /><meta name="keywords" content="some, words" /></head><body><p>hello <a href="#">link</a> there</p><div><p>goodbye! :)</p></div></body></html>""";
        final StringBuilder builder = new StringBuilder();
        try (final PageWriter writer = new PageWriter(builder)) {
            writer.write(
                DOCTYPE_HTML,
                HTML.set("lang", "en").child(
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
        assert expected.contentEquals(builder);
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
                      .equals("<div class=\"first\"><div class=\"second\"><div " +
                                  "class=\"third\"><p>hello</p></div></div></div>");
        assert element.classes("bean").toString()
                      .equals(
                          "<div class=\"first bean\"><div class=\"second bean\"><div class=\"third " +
                              "bean\"></div></div></div>");
        try (final PageWriter writer = new PageWriter(builder)) {
            writer.write(
                DOCTYPE_HTML,
                HTML.set("lang", "en").child(
                    BODY.child(
                        element.child(
                            P
                                     )
                              )
                                            )
                        );
        }
        assert builder.toString()
                      .equals(
                          "<!DOCTYPE html><html lang=\"en\"><body><div class=\"first\"><div class=\"second\"><div " +
                              "class=\"third\"><p></p></div></div></div></body></html>");
    }

    @Test
    public void formatted() {
        final String expected = """
            <!DOCTYPE html>
            <html lang="en">
            	<body>
            		<div></div>
            		<div>
            			<p>hello <b>there</b></p>
            		</div>
            	</body>
            </html>""";
        final StringBuilder builder = new StringBuilder();
        try (final PageWriter writer = new PageWriter(builder).format("\t")) {
            writer.write(
                DOCTYPE_HTML,
                HTML.set("lang", "en").child(
                    BODY.child(
                        DIV,
                        DIV.child(
                            P.write("hello ").child(B.write("there"))))));
        }
        assert builder.toString().equals(expected) : builder;
    }

}
