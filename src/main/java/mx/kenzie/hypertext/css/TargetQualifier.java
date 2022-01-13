package mx.kenzie.hypertext.css;

import mx.kenzie.autodoc.api.note.Ignore;

@Ignore
interface TargetQualifier {
    
    TargetQualifier of(String... value);
    
    String toString();
    
    class CompiledQualifier implements TargetQualifier {
        final String value;
        
        public CompiledQualifier(String value) {
            this.value = value;
        }
        
        @Override
        public TargetQualifier of(String... value) {
            return this;
        }
        
        @Override
        public String toString() {
            return value;
        }
    }
    
}
