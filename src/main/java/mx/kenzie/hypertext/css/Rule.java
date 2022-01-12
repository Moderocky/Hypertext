package mx.kenzie.hypertext.css;

import mx.kenzie.autodoc.api.note.Ignore;
import mx.kenzie.hypertext.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

@Ignore
public class Rule implements Writable {
    
    protected final List<String> selectors;
    protected final Map<String, String> rules;
    
    public Rule(String... selectors) {
        this.selectors = new ArrayList<>(Arrays.asList(selectors));
        this.rules = new HashMap<>();
    }
    
    @Override
    public void write(OutputStream stream, Charset charset) throws IOException {
        this.write(stream, charset, String.join(" ", selectors));
        this.write(stream, charset, " {");
        for (final Map.Entry<String, String> entry : rules.entrySet()) {
            this.write(stream, charset, entry.getKey());
            this.write(stream, charset, ": ");
            this.write(stream, charset, entry.getValue());
            this.write(stream, charset, ";");
        }
        this.write(stream, charset, "}");
        
    }
    
    protected final void write(OutputStream stream, Charset charset, String string) throws IOException {
        stream.write(string.getBytes(charset));
    }
}
