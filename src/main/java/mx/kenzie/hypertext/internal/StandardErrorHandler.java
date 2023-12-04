package mx.kenzie.hypertext.internal;

import mx.kenzie.hypertext.ErrorHandler;

public class StandardErrorHandler implements ErrorHandler {
    
    @Override
    public void handle(Throwable error) {
        throw new RuntimeException(error);
    }
    
}
