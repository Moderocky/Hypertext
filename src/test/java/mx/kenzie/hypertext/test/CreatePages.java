package mx.kenzie.hypertext.test;

import mx.kenzie.autodoc.AutoDocs;

import java.io.File;
import java.io.IOException;

public class CreatePages {
    
    public static void main(String[] args) throws IOException {
        AutoDocs.generateDocumentation(
            "Hypertext",
            "The API documentation for Hypertext.",
            """
                
                ## Hypertext
                
                Visit the repository [here](https://github.com/Moderocky/Hypertext).
                
                This website contains documentation for the Hypertext API.
                """,
            new File("docs/"),
            new File("target/Hypertext.jar"),
            "mx.kenzie.hypertext");
        
    }
    
}
