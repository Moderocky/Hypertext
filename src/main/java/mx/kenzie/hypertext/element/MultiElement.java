package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class MultiElement extends HTMElement implements Cloneable {
    
    protected final HTMElement centre;
    protected final HTMElement[] nested;
    
    public MultiElement(HTMElement... nested) {
        super(null);
        this.nested = new HTMElement[nested.length];
        HTMElement previous = this;
        for (int i = 0; i < nested.length; i++) {
            final HTMElement element = nested[i].clone();
            previous.child(element);
            this.nested[i] = element;
            previous = element;
        }
        this.centre = previous;
    }
    
    @Override
    public HTMElement set(String key, String value) {
        final MultiElement target;
        if (this.finalise) target = this.clone();
        else target = this;
        for (final HTMElement element : target.nested) element.set(key, value);
        return target;
    }
    
    @Override
    @SuppressWarnings({"RedundantSuppression", "CloneDoesntCallSuperClone", "CloneDoesntDeclareCloneNotSupportedException"})
    protected MultiElement clone() {
        final HTMElement[] elements = new HTMElement[nested.length];
        for (int i = 0; i < nested.length; i++) {
            elements[i] = nested[i].clone();
            elements[i].children.clear();
        }
        return new MultiElement(elements);
    }
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.body(stream, charset);
    }
    
    @Override
    public HTMElement child(Writable... children) {
        if (this.centre == null) return super.child(children);
        final MultiElement target;
        if (this.finalise) target = this.clone();
        else target = this;
        target.centre.child(children);
        return target;
    }
    
    @Override
    public HTMElement classes(String... classes) {
        final MultiElement target;
        if (this.finalise) target = this.clone();
        else target = this;
        for (final HTMElement element : target.nested) element.classes(classes);
        return target;
    }
    
}
