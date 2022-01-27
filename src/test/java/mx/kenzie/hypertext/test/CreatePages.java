package mx.kenzie.hypertext.test;

import mx.kenzie.autodoc.DocBuilder;

import java.io.File;
import java.io.IOException;

public class CreatePages {
    
    public static void main(String[] args) throws IOException {
        new DocBuilder("Hypertext", new File("docs/"))
            .setDescription("The API documentation for Hypertext.")
            .setBody("""
                
                ## Hypertext
                
                Visit the repository [here](https://github.com/Moderocky/Hypertext).
                
                This website contains documentation for the Hypertext API.
                
                """)
            .setSourceRoot(new File("src/main/java/"))
            .setJar(new File("target/Hypertext.jar"))
            .addClassesFrom("mx.kenzie.hypertext")
            .build();
        
    }
    
}
