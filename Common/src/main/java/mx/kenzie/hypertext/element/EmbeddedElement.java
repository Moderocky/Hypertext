package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;

public class EmbeddedElement extends HREFElement {

    public EmbeddedElement(String tag, Writable... children) {
        super(tag, children);
    }

    public EmbeddedElement src(String source) {
        return (EmbeddedElement) this.set("src", source);
    }

    @Override
    protected EmbeddedElement clone() {
        return super.clone(new EmbeddedElement(tag));
    }

}
