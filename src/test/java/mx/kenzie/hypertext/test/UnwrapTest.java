package mx.kenzie.hypertext.test;

import mx.kenzie.hypertext.SourceUnwrapper;
import mx.kenzie.hypertext.element.Page;
import mx.kenzie.hypertext.internal.CSSElementUnwrapper;
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
            .equals(
                "<html><head><title>hello there!</title></head><body><p>What's your name ?</p><p>Hello <a> there!\n        </a></p></body></html>");
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
            .equals(
                "<html lang=\"en\"><head><title>hello there!</title><meta name=\"description\" content=\"My description.\" /><meta name=\"keywords\" content=\"some, words\" /></head><body><p class=\"card-text text-danger\">Beans</p></body></html>");
        assert page.navigate().tag("p").found();
        assert page.navigate().tag("p").classes("card-text").found();
        assert page.navigate().search("name", "keywords").found();
    }
    
    @Test
    public void css() throws IOException {
        final String test = """
            h1.thing {
                color: red;
                border: 0 0 3em 3em;
                font-family: 'JetBrains Mono' sans-serif;
            }
            #id.thing > p {
            }
            """;
        final Page page = new CSSElementUnwrapper(test).unwrap();
        System.out.println(page);
        assert page.toString()
            .equals(
                "h1.thing {border: 0 0 3em 3em;color: red;font-family: 'JetBrains Mono' sans-serif;}#id.thing > p {}");
    }
    
    @Test
    public void mixed() throws IOException {
        final String test = """
            <html lang="en">
                <head>
                    <title>hello there!</title>
                    <meta name="description" content="My description." />
                    <style>
                    h1.thing {
                        color: red;
                    }
                    #id.thing > p {
                        border-bottom: 1px solid black;
                        color: blue;
                    }
                    </style>
                </head>
                <body>
                    <p class="card-text text-danger">Beans</p>
                </body>
            </html>
            """;
        final Page page = new HTMElementUnwrapper(test).unwrap();
        assert page.toString()
            .equals(
                "<html lang=\"en\"><head><title>hello there!</title><meta name=\"description\" content=\"My description.\" /><style>h1.thing {color: red;}#id.thing > p {color: blue;border-bottom: 1px solid black;}</style></head><body><p class=\"card-text text-danger\">Beans</p></body></html>");
    }
    
}
