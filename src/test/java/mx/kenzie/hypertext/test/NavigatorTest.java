package mx.kenzie.hypertext.test;

import mx.kenzie.hypertext.Navigator;
import mx.kenzie.hypertext.element.Page;
import org.junit.Test;

import static mx.kenzie.hypertext.element.StandardElements.*;

public class NavigatorTest {
    
    @Test
    public void tagSearch() {
        final Page page = new Page(
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
        try (final Navigator navigator = page.navigate().tag(META)) {
            assert navigator.found();
            assert navigator.findAll().length == 2;
        }
        try (final Navigator navigator = page.navigate().tag(TITLE)) {
            assert navigator.found();
            assert navigator.findAll().length == 1;
            navigator.reset();
            assert !navigator.found();
        }
        try (final Navigator navigator = page.navigate().tag(P)) {
            assert navigator.found();
            assert navigator.findAll().length == 2;
        }
    }
    
    @Test
    public void complexSearch() {
        final Page page = new Page(
            DOCTYPE_HTML,
            HTML.child(
                HEAD.child(
                    TITLE.write("Cool Page"),
                    META.set("name", "description").set("content", "My description."),
                    META.set("name", "description").set("bean", "thing"),
                    META.set("name", "description").set("content", "My description."),
                    META.set("name", "keywords").set("content", "some, words")
                ),
                BODY.child(
                    P.write("hello ").child(A.href("#").write("link")).write(" there"),
                    DIV.id("bean").child(
                        P.write("goodbye! :)")
                    )
                )
            )
        );
        try (final Navigator navigator = page.navigate().id("bean")) {
            assert navigator.found();
            assert navigator.findAll().length == 1;
        }
        try (final Navigator navigator = page.navigate().search("href", "#")) {
            assert navigator.found();
            assert navigator.findAll().length == 1;
        }
        try (final Navigator navigator = page.navigate().search("name", "description")) {
            assert navigator.found();
            assert navigator.findAll().length == 3;
            navigator.search("bean", "thing");
            assert navigator.found();
            assert navigator.findAll().length == 1;
        }
    }
    
}
