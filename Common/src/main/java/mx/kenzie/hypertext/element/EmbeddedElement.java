package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;

public class EmbeddedElement extends SizedElement {

    public EmbeddedElement(String tag, Writable... children) {
        super(tag, children);
    }

    public EmbeddedElement src(String source) {
        return (EmbeddedElement) this.set(Property.get(Properties.SRC), source);
    }

    @Override
    public EmbeddedElement single() {
        return (EmbeddedElement) super.single();
    }

    @Override
    protected EmbeddedElement clone() {
        return super.clone(new EmbeddedElement(tag));
    }

}
