package mx.kenzie.hypertext.rebuilder;

public class HTMLFormatError extends Error {
    
    public HTMLFormatError() {
        super();
    }
    
    public HTMLFormatError(String message) {
        super(message);
    }
    
    public HTMLFormatError(String message, Throwable cause) {
        super(message, cause);
    }
    
    public HTMLFormatError(Throwable cause) {
        super(cause);
    }
    
    protected HTMLFormatError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
