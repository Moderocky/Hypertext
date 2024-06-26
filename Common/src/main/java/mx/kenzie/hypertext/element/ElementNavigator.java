package mx.kenzie.hypertext.element;

import mx.kenzie.hypertext.Navigator;

import java.util.ArrayList;
import java.util.Map;

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
            final Map<String, CharSequence> properties = element.getProperties();
            if (!properties.containsKey(key)) return true;
            return !properties.get(key).equals(value);
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
        final CharSequence sequence = this.element.getProperties().get(key);
        if (sequence == null) return null;
        return sequence.toString();
    }

    protected void resetQuery() {
        this.found = new ArrayList<>(this.element.getAllChildren());
    }

}
