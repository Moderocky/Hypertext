package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Navigator;

import java.util.ArrayList;

class ElementNavigator extends Navigator {
    
    protected ElementNavigator(HTMElement element) {
        super(element);
    }
    
    @Override
    public Navigator classes(String... values) {
        if (found == null) this.resetQuery();
        this.found.removeIf(element -> {
            for (final String value : values) {
                if (!element.classes.contains(value)) return true;
            }
            return false;
        });
        return this;
    }
    
    @Override
    public Navigator search(String key, String value) {
        if (found == null) this.resetQuery();
        this.found.removeIf(element -> {
            if (!element.properties.containsKey(key)) return true;
            return !element.properties.get(key).equals(value);
        });
        return this;
    }
    
    @Override
    public Navigator tag(String tag) {
        if (found == null) this.resetQuery();
        this.found.removeIf(element -> !element.tag.equals(tag));
        return this;
    }
    
    @Override
    public Navigator tag(HTMElement tag) {
        return this.tag(tag.tag);
    }
    
    @Override
    public Navigator comments() {
        if (found == null) this.resetQuery();
        this.found.removeIf(element -> !(element instanceof HTMComment));
        return this;
    }
    
    @Override
    protected String get(String key) {
        return this.element.properties.get(key);
    }
    
    protected void resetQuery() {
        this.found = new ArrayList<>(this.element.getAllChildren());
    }
    
}
