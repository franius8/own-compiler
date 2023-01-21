package Streams;

public class Token {

    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType type() {
        return type;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type()) return false;
        return value.equals(token.value());
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
