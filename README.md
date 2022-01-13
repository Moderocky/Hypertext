# Hypertext

### Opus 14

A clean, simple and well-structured framework for writing HTML pages in Java.

API documentation is available [here](https://hypertext.kenzie.mx).

Goals:
1. Use minimal resources when creating and writing pages.
2. Involve minimal boilerplate and screen-wastage for the user.
3. Be applicable both for static and procedurally-generated pages.

## Dependency Details
Hypertext is available in Kenzie's repository.

```xml
<repository>
  <id>kenzie</id>
  <name>Kenzie's Repository</name>
  <url>https://repo.kenzie.mx/releases</url>
</repository>
```

```xml
<dependency>
  <groupId>mx.kenzie</groupId>
  <artifactId>hypertext</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Using Hypertext

Pages can be written through the `PageWriter` resource, which deals with exceptions, charsets and output automatically.
Pages can also be written manually through the element `write` method, but this requires more effort on the part of the user.

```java 
try (final PageWriter writer = new PageWriter(file)) { // auto-handles streams
    writer.write(page);
}
```

To assemble a page, `HTMElement`s (HyperText Markup Elements) must be assembled using a hybrid constructor/builder process.
Each element references an individual HTML `<tag></tag>` pair (or single `<tag />`) and contains its metadata and children.

These can be assembled manually using `new HTMElement("tagname")` but, for simplicity, Hypertext contains an entire set of pre-defined HTML tag elements according to the HTML5 schema.

These can be used with a static import (for simplicity.)
```java
import static mx.kenzie.hypertext.element.StandardElements.*;
```

With this import, the tags may then be used by name.
```java 
new Page(
    DOCTYPE_HTML,
    HTML.child(
        HEAD.child(
            TITLE.write("Page Title"),
            META.set("name", "description").set("content", "My description.")
        ),
        BODY.child(
            DIV.child(
                P.write("goodbye! :)")
            )
        )
    )
);
```

Tags can be modified using their builder methods. If the tag is `finalised` (as the standard set are) this will create a modifiable clone that can be altered.
This is done to save memory where possible by reusing element objects unless they need to be different.

The `.child(element, element, ...)` method adds multiple elements as children.
```java 
BODY.child( // <body>
    DIV.child( // <div>
        P.write("goodbye! :)"), // <p></p>
        P.write("goodbye! :)")  // <p></p>
    ) // </div>
) // </body>
```

The `.set(key, value)` method sets a meta tag in the header to the given value.
```java 
META.set("name", "description").set("content", "My description.")
// <meta name="description" content="My description." />
```

The `.classes(class, class, ...)` method adds (multiple) classes to the element.
```java 
DIV.classes("col", "col-md-8", "col-lg-12")
// <div class="col col-md-8 col-lg-12"></div>
```

Some individual element types may have additional modifier methods.

## Adding CSS Rules

Hypertext supports constructing basic CSS selectors and rules.
These can either be written into a `STYLE` element in the head, or into a stylesheet using another `PageWriter`. Since Rules are writable elements, they can be written as well as normal HTML.

The simplest way to write CSS rules is using the constructor.
```java
HEAD.child( // <head>
    STYLE.child( // <style>
        new Rule("p").rule("color", "red"),
        new Rule("div").rule("border-bottom", "2px solid black")
    ) // </style>
) // </head>
```

```css
p {
  color: red;
}
div {
  border-bottom: 2px solid black;
}
```

This also supports using the `HTMElement`s directly to select tags.
```java
STYLE.child(
    new Rule(DIV).rule("background", "red")
)
```

However, more advanced selector-building methods are also available.

The multi-element selector:
```java
STYLE.child(
    Rule.all(UL, P, DIV, BR)
)
```

```css
ul, p, div, br {
}
```


Complex attribute selectors:
```java
STYLE.child(
    Rule.of(DIV, ATTRIBUTE_EQUALS.of("name", "hello"))
)
```

```css
div[name=hello] {
}
```

Many more types are available, with examples in the documentation.



