package mx.kenzie.hypertext.internal;

import mx.kenzie.autodoc.api.note.Ignore;
import mx.kenzie.hypertext.ErrorHandler;

@Ignore
public class StandardErrorHandler implements ErrorHandler {
    @Override
    public void handle(Throwable error) {
        throw new RuntimeException(error);
    }
}
