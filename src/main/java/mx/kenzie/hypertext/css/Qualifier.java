package mx.kenzie.hypertext.css;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.autodoc.api.note.Ignore;

@Description("""
    A set of qualifiers.
    
    """)
public enum Qualifier implements TargetQualifier {
    ACTIVE(":active"),
    AFTER("::after"),
    BEFORE("::before"),
    CHECKED(":checked"),
    DEFAULT(":default"),
    DISABLED(":disabled"),
    ENABLED(":enabled"),
    EMPTY(":empty"),
    FOCUS(":focus"),
    FULLSCREEN(":fullscreen"),
    HOVER(":hover"),
    LINK(":link"),
    VISITED(":visited"),
    FIRST_CHILD(":first-child"),
    FIRST_LETTER("::first-letter"),
    FIRST_LINE("::first-line"),
    ATTRIBUTE("[", "]"),
    ATTRIBUTE_EQUALS("[", "=", "]"),
    ATTRIBUTE_CONTAINS("[", "~=", "]"),
    ATTRIBUTE_STARTS_EQ("[", "|=", "]"),
    ATTRIBUTE_STARTS("[", "^=", "]"),
    ATTRIBUTE_ENDS("[", "$=", "]"),
    ATTRIBUTE_SUB("[", "*=", "]");
    
    @Ignore
    final String target, first, last, joiner;
    
    @Ignore
    Qualifier(String target) {
        this.target = target;
        this.first = null;
        this.last = null;
        this.joiner = null;
    }
    
    @Ignore
    Qualifier(String first, String last) {
        this.target = null;
        this.first = first;
        this.last = last;
        this.joiner = "";
    }
    
    @Ignore
    Qualifier(String first, String joiner, String last) {
        this.target = null;
        this.first = first;
        this.last = last;
        this.joiner = joiner;
    }
    
    @Ignore
    @Override
    public String toString() {
        if (target == null) return first + null + last;
        return target;
    }
    
    @Description("""
        Creates a complex qualifier from this template.
        
        This is used with the `ATTRIBUTE...` qualifiers.
        """)
    @Override
    public TargetQualifier of(String... value) {
        if (target != null) return this;
        if (joiner == null) return new CompiledQualifier(first + String.join("", value) + last);
        return new CompiledQualifier(first + String.join(joiner, value) + last);
    }
}
