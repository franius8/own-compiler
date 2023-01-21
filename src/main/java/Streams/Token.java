package Streams;

public record Token(TokenType type, String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type()) return false;
        return value.equals(token.value());
    }

}
