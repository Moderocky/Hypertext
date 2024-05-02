package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;

public class SizedElement extends HREFElement {

    public SizedElement(String tag, Writable... children) {
        super(tag, children);
    }

    public SizedElement dimensions(int width, int height) {
        return this.width(Integer.toString(width)).height(Integer.toString(height));
    }

    public SizedElement width(String width) {
        return (SizedElement) this.set(Property.get(Properties.WIDTH), width);
    }

    public SizedElement height(String height) {
        return (SizedElement) this.set(Property.get(Properties.HEIGHT), height);
    }

    @Override
    protected SizedElement clone() {
        return super.clone(new SizedElement(tag));
    }

}
