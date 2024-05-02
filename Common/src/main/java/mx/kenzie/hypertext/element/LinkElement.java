package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;

public class LinkElement extends HREFElement {

    public LinkElement(String tag, Writable... children) {
        super(tag, children);
    }

    public LinkElement(Writable... children) {
        super("link", children);
        this.single = true;
    }

    public LinkElement rel(String type) {
        return (LinkElement) this.set(Property.get(Properties.REL), type);
    }

    public LinkElement href(String url) {
        return (LinkElement) this.set(Property.get(Properties.HREF), url);
    }

    @Override
    protected LinkElement clone() {
        return super.clone(new LinkElement(tag));
    }

    public LinkElement integrity(String hash) {
        return (LinkElement) this.set(Property.get(Properties.INTEGRITY), hash);
    }

    public LinkElement crossOrigin(String origin) {
        return (LinkElement) this.set(Property.get(Properties.CROSSORIGIN), origin);
    }

}
