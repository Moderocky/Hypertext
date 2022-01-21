package mx.kenzie.hypertext.test;

import mx.kenzie.hypertext.SourceUnwrapper;
import mx.kenzie.hypertext.element.Page;
import mx.kenzie.hypertext.internal.HTMElementUnwrapper;
import org.junit.Test;

import java.io.IOException;

public class UnwrapTest {
    
    @Test
    public void simple() throws IOException {
        final String test = """
            <html>
                <head>
                </head>
                <body>
                    <div>
                    </div>
                </body>
            </html>
            """;
        try (final SourceUnwrapper<Page> unwrapper = SourceUnwrapper.forHTML(test)) {
            final Page page = unwrapper.unwrap();
            assert page.toString().equals("<html><head></head><body><div></div></body></html>");
            assert page.navigate().tag("div").found();
            assert page.navigate().tag("div").findAll().length == 1;
        }
    }
    
    @Test
    public void contents() throws IOException {
        final String test = """
            <html>
                <head>
                    <title>hello there!</title>
                </head>
                <body>
                    <p>What's your name ?</p>
                    <p>Hello <a> there!
                    </a></p>
                </body>
            </html>
            """;
        final Page page = new HTMElementUnwrapper(test).unwrap();
        assert page.toString()
            .equals("<html><head><title>hello there!</title></head><body><p>What's your name ?</p><p>Hello <a> there!\n        </a></p></body></html>");
        assert page.navigate().tag("a").found();
        assert page.navigate().tag("p").findAll().length == 2;
    }
    
    @Test
    public void attributes() throws IOException {
        final String test = """
            <html lang="en">
                <head>
                    <title>hello there!</title>
                    <meta name="description" content="My description." />
                    <meta name="keywords" content="some, words" />
                </head>
                <body>
                    <p class="card-text text-danger">Beans</p>
                </body>
            </html>
            """;
        final Page page = new HTMElementUnwrapper(test).unwrap();
        assert page.toString()
            .equals("<html lang=\"en\"><head><title>hello there!</title><meta name=\"description\" content=\"My description.\" /><meta name=\"keywords\" content=\"some, words\" /><head></head><body><p class=\"card-text text-danger\">Beans<p></p><body></body><html></html></p></body></head></html>");
        assert page.navigate().tag("p").found();
        assert page.navigate().tag("p").classes("card-text").found();
        assert page.navigate().search("name", "keywords").found();
    }
    
}
