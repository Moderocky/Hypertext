package mx.kenzie.hypertext.rebuilder;

import mx.kenzie.autodoc.api.note.Description;
import mx.kenzie.autodoc.api.note.Example;
import mx.kenzie.autodoc.api.note.Ignore;

@Description("""
    An error thrown by an HTML formatter if the tags provided are invalid or incorrect.
    
    This is not a checked exception, so you do not need to catch it.
    """)
@Example("new HTMLFormatError()")
public class HTMLFormatError extends Error {
    
    @Ignore
    public HTMLFormatError() {
        super();
    }
    
    @Ignore
    public HTMLFormatError(String message) {
        super(message);
    }
    
    @Ignore
    public HTMLFormatError(String message, Throwable cause) {
        super(message, cause);
    }
    
    @Ignore
    public HTMLFormatError(Throwable cause) {
        super(cause);
    }
    
    @Ignore
    protected HTMLFormatError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
