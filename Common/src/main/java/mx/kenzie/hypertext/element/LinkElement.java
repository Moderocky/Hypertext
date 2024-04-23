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
        return (LinkElement) this.set("rel", type);
    }

    public LinkElement href(String url) {
        return (LinkElement) this.set("href", url);
    }

    @Override
    protected LinkElement clone() {
        return super.clone(new LinkElement(tag));
    }

    public LinkElement integrity(String hash) {
        return (LinkElement) this.set("integrity", hash);
    }

    public LinkElement crossOrigin(String origin) {
        return (LinkElement) this.set("crossorigin", origin);
    }

}
