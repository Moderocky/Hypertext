package mx.kenzie.hypertext.test;

import mx.kenzie.hypertext.PageWriter;
import org.junit.Test;

import static mx.kenzie.hypertext.bootstrap.BootstrapElements.*;

public class BootstrapTest {
    
    @Test
    public void simple() {
        final StringBuilder builder = new StringBuilder();
        try (final PageWriter writer = new PageWriter(builder)) {
            writer.write(
                DOCTYPE_HTML,
                HTML.set("lang", "en").child(
                    HEAD.child(
                        BOOTSTRAP_CSS,
                        META_VIEWPORT,
                        TITLE.write("Boostrap Page")
                    ),
                    BODY.child(
                        MAIN.child(
                            ALERT.classes("alert-primary").write("hello?"),
                            P.write("goodbye! :) ").child(
                                BADGE.classes("bg-warning", "text-light").write("HELLO")
                            )
                        ),
                        BOOTSTRAP_JS
                    )
                )
            );
        }
        assert builder.toString()
            .equals("<!DOCTYPE html><html lang=\"en\"><head><link rel=\"stylesheet\" crossorigin=\"anonymous\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" /><title>Boostrap Page</title></head><body><main><div class=\"alert alert-primary \" role=\"alert\">hello?</div><p>goodbye! :) <span class=\"badge bg-warning text-light \">HELLO</span></p></main><script crossorigin=\"anonymous\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" type=\"text/javascript\"></script></body></html>");
    }
    
}
