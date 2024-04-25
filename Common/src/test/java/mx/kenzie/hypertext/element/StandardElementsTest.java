package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.PageWriter;
import mx.kenzie.hypertext.Writable;
import org.junit.Test;

import java.io.IOException;

import static mx.kenzie.hypertext.element.StandardElements.*;

public class StandardElementsTest {

    @Test
    public void pre() throws IOException {
        this.test(PRE.write("hello there!"), "<pre>hello there!</pre>", true);
        this.test(DIV.child(PRE.child(CODE.write("hello there!"))),
                  """
                      <div>
                      \t<pre><code>hello there!</code></pre>
                      </div>""", true);
    }

    protected void test(Writable content, String expected, boolean pretty) throws IOException {
        final StringBuilder builder = new StringBuilder();
        try (final PageWriter writer = new PageWriter(builder)) {
            if (pretty) writer.format("\t");
            writer.write(content);
        }
        assert expected.contentEquals(builder) : "expected vs got:\n" + expected + System.lineSeparator() + builder;
    }

}