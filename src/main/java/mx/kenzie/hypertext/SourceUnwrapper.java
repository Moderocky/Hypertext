package mx.kenzie.hypertext;

import mx.kenzie.hypertext.element.Page;
import mx.kenzie.hypertext.internal.CSSElementUnwrapper;
import mx.kenzie.hypertext.internal.HTMElementUnwrapper;

import java.io.*;

public interface SourceUnwrapper<Type> extends AutoCloseable {
    
    /**
     * Returns a new wrapper for unwrapping a string of HTML.
     * This should be closed when finished.
     */
    static SourceUnwrapper<Page> forHTML(String source) {
        return new HTMElementUnwrapper(source);
    }
    
    /**
     * Returns a new wrapper for unwrapping a stream of HTML.
     * This should be closed when finished.
     */
    static SourceUnwrapper<Page> forHTML(InputStream source) {
        return new HTMElementUnwrapper(source);
    }
    
    /**
     * Unwraps a stream of CSS to a Page of Rule elements.
     * This should be closed when finished.
     */
    static SourceUnwrapper<Page> forCSS(InputStream source) {
        return new CSSElementUnwrapper(source);
    }
    
    /**
     * Unwraps a string of CSS to a Page of Rule elements.
     * This should be closed when finished.
     */
    static SourceUnwrapper<Page> forCSS(String source) {
        return new CSSElementUnwrapper(source);
    }
    
    static SourceUnwrapper<Page> forHTML(File source) throws FileNotFoundException {
        return new HTMElementUnwrapper(new FileInputStream(source));
    }
    
    Type unwrap() throws IOException;
    
    @Override
    void close() throws IOException;
    
}
