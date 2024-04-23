package mx.kenzie.hypertext.internal;

import mx.kenzie.hypertext.content.Parser;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownParser implements Parser {

    public static final Parser MARKDOWN_PARSER = new MarkdownParser();

    protected Object parser;
    protected Object renderer;

    public MarkdownParser() {
        try { // avoid CNF
            this.parser = org.commonmark.parser.Parser.builder().build();
            this.renderer = HtmlRenderer.builder().build();
        } catch (Exception ignore) {
        }
    }

    @Override
    public String parse(String content) {
        if (!available()) return content;
        final org.commonmark.parser.Parser parser = (org.commonmark.parser.Parser) this.parser;
        final Node node = parser.parse(content);
        final HtmlRenderer renderer = (HtmlRenderer) this.renderer;
        return renderer.render(node);
    }

    @Override
    public boolean available() {
        try {
            Class.forName("org.commonmark.renderer.html.HtmlRenderer", true, this.getClass().getClassLoader());
            return parser != null;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

}
