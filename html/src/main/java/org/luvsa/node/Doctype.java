package org.luvsa.node;

/**
 * 声明标签
 *
 * @author Aglet
 * @create 2023/2/18 13:54
 */
public class Doctype implements Nodule {

    private static final String DOCTYPE = "doctype";

    @Override
    public Node apply(String s) {
        if (s.startsWith("<!")) {
            var index = 2;
            for (var size = s.length(); index < size; index++) {
                var c = s.charAt(index);
                if (c == ' ' || c == '>') {
                    break;
                }
            }
            var trim = s.substring(2, index).trim();
            if (DOCTYPE.equalsIgnoreCase(trim)) {
                return new Builder(s);
            }
        }
        throw new UnsupportedOperationException(s);
    }

    static class Builder extends Leaf {

        private final String code;

        public Builder(String s) {
            this.code = s;
        }

        @Override
        public String getName() {
            return DOCTYPE;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}
