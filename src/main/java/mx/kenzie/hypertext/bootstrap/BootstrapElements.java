package mx.kenzie.hypertext.bootstrap;

import mx.kenzie.hypertext.element.HTMElement;
import mx.kenzie.hypertext.element.LinkElement;
import mx.kenzie.hypertext.element.MultiElement;
import mx.kenzie.hypertext.element.StandardElements;

public interface BootstrapElements extends StandardElements {
    
    HTMElement
        BOOTSTRAP_CSS = new LinkElement()
        .href("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css")
        .rel("stylesheet")
        .integrity("sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3")
        .crossOrigin("anonymous").finalise(),
        BOOTSTRAP_JS = new LinkElement("script")
            .href("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js")
            .integrity("sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p")
            .crossOrigin("anonymous")
            .set("type", "text/javascript").finalise(),
        META_VIEWPORT = new HTMElement("meta").single()
            .set("name", "viewport")
            .set("content", "width=device-width, initial-scale=1")
            .finalise(),
        ALERT = DIV.set("role", "alert").classes("alert").finalise(),
        BADGE = SPAN.classes("badge").finalise(),
        BREADCRUMBS = new MultiElement(
            NAV.set("aria-label", "breadcrumb"),
            OL.classes("breadcrumb")
        ).finalise(),
        BREADCRUMB = LI.classes("breadcrumb-item").finalise(),
        BUTTON = StandardElements.BUTTON.set("type", "button").classes("btn").finalise(),
        BUTTON_GROUP = DIV.set("role", "group").classes("btn-group").finalise();
    
    
}
