package mx.kenzie.hypertext.element;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum Properties implements Property, Supplier<Property> {
    CLASS,
    STYLE,
    ID, ALT, HREF, REL, INTEGRITY, CROSSORIGIN, SRC, HEIGHT, WIDTH;

    @Override
    public String getName() {
        return this.name().toLowerCase();
    }

    @Override
    public Property get() {
        return this;
    }

    @Override
    public @NotNull String toString() {
        return this.getName();
    }

}
