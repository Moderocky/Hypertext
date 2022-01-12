package mx.kenzie.hypertext;

import mx.kenzie.autodoc.api.note.Description;

@Description("""
    The behaviour for handling an error caught during page writing.
    """)
@FunctionalInterface
public interface ErrorHandler {
    
    void handle(Throwable error);
    
}
