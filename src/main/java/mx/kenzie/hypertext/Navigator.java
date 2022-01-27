package mx.kenzie.hypertext;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.hypertext.element.HTMElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A query system for navigating an element tree structure.
 *
 * This is capable of filtering elements according to multiple factors.
 * The navigator must be reset between uses.
 *
 * When a search function is used, the navigator creates a query of matching elements.
 * If another search function is used, the existing query will be narrowed down.
 */
public abstract class Navigator implements Iterable<HTMElement>, AutoCloseable {
    
    protected final HTMElement element;
    protected List<HTMElement> found;
    
    protected Navigator(HTMElement element) {
        this.element = element;
    }
    
    @Description("Filter the current query by classes.")
    public abstract Navigator classes(String... values);
    
    public Navigator id(String value) {
        return this.search("id", value);
    }
    
    public abstract Navigator search(String key, String value);
    
    public abstract Navigator tag(String tag);
    
    public abstract Navigator tag(HTMElement tag);
    
    public abstract Navigator comments();
    
    public Navigator reset() {
        this.found = null;
        return this;
    }
    
    protected abstract String get(String key);
    
    public HTMElement findOne() {
        if (!this.found()) return null;
        return this.found.get(0);
    }
    
    public boolean found() {
        return this.found != null && this.found.size() > 0;
    }
    
    public HTMElement[] findAll() {
        if (this.found == null) return new HTMElement[0];
        return this.found.toArray(new HTMElement[0]);
    }
    
    public HTMElement source() {
        return element;
    }
    
    @Override
    public void close() {
        this.found = null;
    }
    
    @NotNull
    @Override
    public Iterator<HTMElement> iterator() {
        if (found != null) return found.iterator();
        return Collections.emptyIterator();
    }
    
}
