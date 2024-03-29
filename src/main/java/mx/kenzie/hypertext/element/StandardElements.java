package mx.kenzie.hypertext.element;

public interface StandardElements {
    
    HTMElement
        DOCTYPE_HTML = new Doctype("html"),
        HTML = new HTMElement("html")
//            .set("lang", "en") pollution
            .finalise(),
        COMMENT = new HTMComment().finalise(),
        HEAD = new HTMElement("head").finalise(),
        BODY = new HTMElement("body").finalise(),
    //region head elements
    BASE = new HTMElement("base").single().finalise();
    LinkElement
        LINK = (LinkElement) new LinkElement().finalise();
    HTMElement
        META = new HTMElement("meta").single().finalise(),
        STYLE = new HTMElement("style").finalise(),
        TITLE = new HTMElement("title").finalise();
    //endregion
    //region body elements
    HREFElement
        A = (HREFElement) new HREFElement("a").inline().finalise();
    
    HTMElement
        ABBR = new HTMElement("abbr").finalise(),
        ADDRESS = new HTMElement("address").finalise(),
        AREA = new HTMElement("area").finalise(),
        ARTICLE = new HTMElement("article").finalise(),
        ASIDE = new HTMElement("aside").finalise(),
        B = new HTMElement("b").inline().finalise(),
        BDI = new HTMElement("bdi").finalise(),
        BDO = new HTMElement("bdo").finalise(),
        BLOCKQUOTE = new HTMElement("blockquote").finalise(),
        BR = new HTMElement("br").single().finalise(),
        BUTTON = new HTMElement("button").finalise(),
        CANVAS = new HTMElement("canvas").finalise(),
        CAPTION = new HTMElement("caption").finalise(),
        CITE = new HTMElement("cite").finalise(),
        CODE = new HTMElement("code").finalise(),
        COL = new HTMElement("col").single().finalise(),
        COLGROUP = new HTMElement("colgroup").finalise(),
        DATA = new HTMElement("data").finalise(),
        DATALIST = new HTMElement("datalist").finalise(),
        DD = new HTMElement("dd").finalise(),
        DEL = new HTMElement("del").finalise(),
        DETAILS = new HTMElement("details").finalise(),
        DFN = new HTMElement("dfn").finalise(),
        DIALOG = new HTMElement("dialog").finalise(),
        DIV = new HTMElement("div").finalise(),
        DL = new HTMElement("dl").finalise(),
        DT = new HTMElement("dt").finalise(),
        EM = new HTMElement("em").finalise(),
        EMBED = new HTMElement("embed").finalise(),
        FIELDSET = new HTMElement("fieldset").finalise(),
        FIGCAPTION = new HTMElement("figcaption").finalise(),
        FIGURE = new HTMElement("figure").finalise(),
        FOOTER = new HTMElement("footer").finalise(),
        FORM = new HTMElement("form").finalise(),
    //region headings
    H1 = new HTMElement("h1").finalise(),
        H2 = new HTMElement("h2").finalise(),
        H3 = new HTMElement("h3").finalise(),
        H4 = new HTMElement("h4").finalise(),
        H5 = new HTMElement("h5").finalise(),
        H6 = new HTMElement("h6").finalise(),
    //endregion
    HEADER = new HTMElement("header").finalise(),
        HR = new HTMElement("hr").single().finalise(),
        I = new HTMElement("i").inline().finalise(),
        IFRAME = new HTMElement("iframe").finalise(),
        IMAGE = new HTMElement("img").single().finalise(),
        INPUT = new HTMElement("input").single().finalise(),
        INS = new HTMElement("ins").finalise(),
        KBD = new HTMElement("kbd").finalise(),
        LABEL = new HTMElement("label").finalise(),
        LEGEND = new HTMElement("legend").finalise(),
        LI = new HTMElement("li").finalise(),
        MAIN = new HTMElement("main").finalise(),
        MAP = new HTMElement("map").finalise(),
        MARK = new HTMElement("mark").finalise(),
        METER = new HTMElement("meter").finalise(),
        NAV = new HTMElement("nav").finalise(),
        NOSCRIPT = new HTMElement("noscript").finalise(),
        OBJECT = new HTMElement("object").finalise(),
        OL = new HTMElement("ol").finalise(),
        OPTGROUP = new HTMElement("optgroup").finalise(),
        OPTION = new HTMElement("option").finalise(),
        OUTPUT = new HTMElement("output").finalise(),
        P = new HTMElement("p").finalise(),
        PARAM = new HTMElement("param").single().finalise(),
        PICTURE = new HTMElement("picture").finalise(),
        PRE = new HTMElement("pre").finalise(),
        PROGRESS = new HTMElement("progress").finalise(),
        Q = new HTMElement("q").finalise(),
        RP = new HTMElement("rp").finalise(),
        RT = new HTMElement("rt").finalise(),
        RUBY = new HTMElement("ruby").finalise(),
        S = new HTMElement("s").inline().finalise(),
        SAMP = new HTMElement("samp").finalise(),
        SCRIPT = new HTMElement("script").finalise(),
        SECTION = new HTMElement("section").finalise(),
        SELECT = new HTMElement("select").finalise(),
        SMALL = new HTMElement("small").finalise(),
        SOURCE = new HTMElement("source").single().finalise(),
        SPAN = new HTMElement("span").finalise(),
        STRONG = new HTMElement("strong").finalise(),
        SUB = new HTMElement("sub").finalise(),
        SUMMARY = new HTMElement("summary").finalise(),
        SUP = new HTMElement("sup").finalise(),
        SVG = new HTMElement("svg").finalise(),
        TABLE = new HTMElement("table").finalise(),
        TBODY = new HTMElement("tbody").finalise(),
        TD = new HTMElement("td").finalise(),
        TEMPLATE = new HTMElement("template").finalise(),
        TEXTAREA = new HTMElement("textarea").finalise(),
        TFOOT = new HTMElement("tfoot").finalise(),
        TH = new HTMElement("th").finalise(),
        THEAD = new HTMElement("thead").finalise(),
        TIME = new HTMElement("time").finalise(),
        TR = new HTMElement("tr").finalise(),
        TRACK = new HTMElement("track").single().finalise(),
        U = new HTMElement("u").finalise(),
        UL = new HTMElement("ul").finalise(),
        VAR = new HTMElement("var").finalise(),
        VIDEO = new HTMElement("video").finalise(),
        WBR = new HTMElement("wbr").single().finalise();
    //endregion
    
}
