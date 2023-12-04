package mx.kenzie.hypertext;

@FunctionalInterface
public interface ErrorHandler {
    
    void handle(Throwable error);
    
}
