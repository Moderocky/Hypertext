package mx.kenzie.hypertext.css;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.autodoc.api.note.Example;
import mx.kenzie.autodoc.api.note.GenerateExample;
import mx.kenzie.autodoc.api.note.Ignore;
import mx.kenzie.hypertext.Writable;
import mx.kenzie.hypertext.element.HTMElement;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

@Description("""
    A CSS style rule.
    A block is created with provided selector(s), which can then have individual key/value rules written to it.
    
    Basic rules can be created with the constructor. Advanced rules can be created via the static methods.
    
    These rules can be written into a style element.
    """)
@Example("Rule.of(DIV).rule(\"border-bottom\", \"2px solid black\")")
@Example("Rule.of(P, Qualifier.ATTRIBUTE_EQUALS.of(\"blob\", \"hello\")).rule(\"color\", \"blue\")")
@Example("new Rule(DIV).rule(\"background\", \"red\")")
public class Rule implements Writable {
    
    @Ignore
    protected final List<String> selectors;
    @Ignore
    protected final Map<String, String> rules;
    
    @Ignore
    public Rule(String selector) {
        this();
        this.selectors.add(selector);
    }
    
    @Ignore
    protected Rule() {
        this.selectors = new ArrayList<>();
        this.rules = new HashMap<>();
    }
    
    @Description("""
        A rule with multiple selectors.
        These will be written as `first second third {...}`.
        """)
    @GenerateExample
    public Rule(String... selectors) {
        this();
        this.selectors.addAll(Arrays.asList(selectors));
    }
    
    @Description("""
        A rule with multiple selectors.
        These will be written as `first second third {...}`.
        
        This can accept `HTMElement`s or string tag names.
        """)
    @GenerateExample
    public Rule(Object... selectors) {
        this();
        for (final Object selector : selectors) {
            if (selector instanceof HTMElement element) {
                this.selectors.add(element.getTag());
            } else {
                this.selectors.add(selector.toString());
            }
        }
    }
    
    @Description("""
        Writes a new selector for inner tags inside an outer tag.
        """)
    @Example("Rule.inside(DIV, P)")
    @Example(language = "css", value = """
        div p {
        }
        """)
    public static Rule inside(HTMElement outer, HTMElement inner) {
        return new Rule(outer.getTag() + " " + inner.getTag());
    }
    
    @Description("""
        Writes a new selector for child tags inside an immediate parent tag.
        """)
    @Example("Rule.child(DIV, P)")
    @Example(language = "css", value = """
        div > p {
        }
        """)
    public static Rule child(HTMElement parent, HTMElement child) {
        return new Rule(parent.getTag() + " > " + child.getTag());
    }
    
    @Description("""
        Writes a new selector for tags immediately after each other.
        """)
    @Example("Rule.after(UL, P)")
    @Example(language = "css", value = """
        ul + p {
        }
        """)
    public static Rule after(HTMElement before, HTMElement after) {
        return new Rule(before.getTag() + " + " + after.getTag());
    }
    
    @Description("""
        Writes a new selector for tags immediately before each other.
        """)
    @Example("Rule.before(UL, P)")
    @Example(language = "css", value = """
        p ~ ul {
        }
        """)
    public static Rule before(HTMElement before, HTMElement after) {
        return new Rule(after.getTag() + " ~ " + before.getTag());
    }
    
    @Description("""
        Writes a new selector with an advanced qualifier.
        These can be simple tags such as `p::before` or advanced attribute selectors
        like `div[name=bean]`.
        
        These can be written using the [Qualifier](Qualifier.html) enum.
        """)
    @Example("Rule.of(DIV, ATTRIBUTE_EQUALS.of(\"name\", \"hello\"))")
    @Example(language = "css", value = """
        div[name=hello] {
        }
        """)
    public static Rule of(HTMElement before, TargetQualifier qualifier) {
        return new Rule(before.getTag() + qualifier);
    }
    
    @Description("""
        Writes a simple selector for this element's tag.
        """)
    public static Rule of(HTMElement element) {
        return new Rule(element.getTag());
    }
    
    @Description("""
        Writes a selector for the string tag with a qualifier.
        
        This can be used to build complex selectors.
        """)
    public static Rule of(String before, TargetQualifier qualifier) {
        return new Rule(before + qualifier);
    }
    
    @Description("""
        Writes a selector for a qualifier.
        
        This can be used to select special elements.
        """)
    public static Rule of(TargetQualifier qualifier) {
        return new Rule(qualifier.toString());
    }
    
    @Description("""
        Writes a selector for all of the given elements.
        """)
    @Example("Rule.all(UL, P, DIV, BR)")
    @Example(language = "css", value = """
        ul, p, div, br {
        }
        """)
    public static Rule all(HTMElement... elements) {
        final List<String> list = new ArrayList<>();
        for (final HTMElement element : elements) {
            list.add(element.getTag());
        }
        return new Rule(String.join(", ", list));
    }
    
    @Description("""
        Writes a selector for everything.
        """)
    @Example("Rule.everything()")
    @Example(language = "css", value = """
        * {
        }
        """)
    public static Rule everything() {
        return new Rule("*");
    }
    
    @Description("""
        Adds a new rule to the block with the `!important` tag.
        """)
    @Example("new Rule(DIV).rule(\"background\", \"red\", true)")
    @Example(language = "css", value = """
        div {
            background: red !important;
        }
        """)
    public Rule rule(String key, String value, boolean important) {
        if (important) return this.rule(key, value + " !important");
        else return this.rule(key, value);
    }
    
    @Description("""
        Adds a new rule to the block.
        """)
    @Example("new Rule(DIV).rule(\"background\", \"red\")")
    @Example(language = "css", value = """
        div {
            background: red;
        }
        """)
    public Rule rule(String key, String value) {
        this.rules.put(key, value);
        return this;
    }
    
    @Ignore
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.write(stream, charset, String.join(" ", selectors));
        this.write(stream, charset, " {");
        for (final Map.Entry<String, String> entry : rules.entrySet()) {
            this.write(stream, charset, entry.getKey());
            this.write(stream, charset, ": ");
            this.write(stream, charset, entry.getValue());
            this.write(stream, charset, ";");
        }
        this.write(stream, charset, "}");
        
    }
    
    @Ignore
    protected final void write(OutputStream stream, Charset charset, String string) throws IOException {
        stream.write(string.getBytes(charset));
    }
    
}
