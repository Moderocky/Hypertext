package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;

public class HREFElement extends HTMElement {

    public HREFElement(String tag) {
        super(tag);
    }

    public HREFElement(String tag, Writable... children) {
        super(tag, children);
    }

    public HREFElement href(String url) {
        return (HREFElement) this.set("href", url);
    }

    @Override
    protected HREFElement clone() {
        return super.clone(new HREFElement(tag));
    }

}
