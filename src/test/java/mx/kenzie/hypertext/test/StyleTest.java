package mx.kenzie.hypertext.test;

import mx.kenzie.hypertext.css.Qualifier;
import mx.kenzie.hypertext.css.Rule;
import mx.kenzie.hypertext.element.HTMElement;
import mx.kenzie.hypertext.element.Page;
import org.junit.Test;

import static mx.kenzie.hypertext.element.StandardElements.*;

public class StyleTest {
    
    @Test
    public void simple() {
        final HTMElement element = new Page(
            DOCTYPE_HTML,
            HTML.set("lang", "en").child(
                HEAD.child(
                    STYLE.child(
                        new Rule("p").rule("color", "red"),
                        new Rule("p").rule("border-bottom", "2px solid black")
                    )
                ),
                BODY.child(
                    P.write("hello")
                )
            )
        );
        assert element.toString()
            .equals("<!DOCTYPE html><html lang=\"en\"><head><style>p {color: red;}p {border-bottom: 2px solid black;}</style></head><body><p>hello</p></body></html>");
    }
    
    @Test
    public void complex() {
        final HTMElement element = new Page(
            DOCTYPE_HTML,
            HTML.set("lang", "en").child(
                HEAD.child(
                    STYLE.child(
                        Rule.of(P, Qualifier.ATTRIBUTE_EQUALS.of("blob", "hello")).rule("color", "blue"),
                        Rule.of(DIV).rule("border-bottom", "2px solid black")
                    )
                ),
                BODY.child(
                    DIV.child(
                        P.set("blob", "hello").write("hello"),
                        P.write("hello")
                    )
                )
            )
        );
        assert element.toString()
            .equals("<!DOCTYPE html><html lang=\"en\"><head><style>p[blob=hello] {color: blue;}div {border-bottom: 2px solid black;}</style></head><body><div><p blob=\"hello\">hello</p><p>hello</p></div></body></html>");
    }
    
}
