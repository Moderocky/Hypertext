package mx.kenzie.hypertext.element;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public interface Property extends CharSequence {

    ThreadLocal<Map<Property, Property>> properties = ThreadLocal.withInitial(() -> {
        var map = new HashMap<Property, Property>();
        for (Properties value : Properties.values()) map.put(value, value);
        return map;
    });

    static Property get(Property property) {
        return properties.get().getOrDefault(property, property);
    }

    String getName();

    @NotNull
    @Override
    default CharSequence subSequence(int start, int end) {
        return this.getName().subSequence(start, end);
    }

    @Override
    default int length() {
        return this.getName().length();
    }

    @Override
    default char charAt(int index) {
        return this.getName().charAt(index);
    }

    @NotNull
    @Override
    default IntStream codePoints() {
        return this.getName().codePoints();
    }

}
