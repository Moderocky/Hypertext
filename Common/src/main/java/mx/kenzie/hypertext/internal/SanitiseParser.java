package mx.kenzie.hypertext.internal;

import mx.kenzie.hypertext.content.Parser;

public class SanitiseParser implements Parser {

    @Override
    public String parse(String string) {
        final StringBuilder builder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c > 127 || c == '"' || c == '\'' || c == '<' || c == '>' || c == '&') {
                builder.append("&#");
                builder.append((int) c);
                builder.append(';');
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

}
