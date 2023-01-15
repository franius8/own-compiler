public class TokenStream {

    private final InputStream stream;

    public TokenStream(InputStream stream) {
        this.stream = stream;
    }

    public Token next() throws Throwable {
        clearWhitespace();
        if (stream.eof()) return null;
        char curr = stream.next();
        if (curr == '#') {
            clearLine();
            return next();
        }
        if (isPunc(curr)) return new Token(TokenType.PUNC, Character.toString(curr));

        throw stream.croak("Invalid character: " + curr);
    }

    public char peek() {
        return 0;
    }


    public boolean eof() {
        return false;
    }

    private void clearWhitespace() {
        while (!stream.eof() && isWhiteSpace(stream.peek())) stream.next();
    }

    private void clearLine() {
        while (!stream.eof() && stream.peek() != '\n') stream.next();
        stream.next();
    }

    private boolean isPunc(char x) {
        return ".:()[]{}".indexOf(x) != -1;
    }

    private boolean isWhiteSpace(char x) {
        return " \n\t".indexOf(x) != -1;
    }
}
