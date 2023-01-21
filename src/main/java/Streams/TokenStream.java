package Streams;

import Streams.InputStream;

import java.util.Arrays;
import java.util.function.Predicate;

public class TokenStream {

    private final InputStream stream;

    private Token current = null;

    private final String[] keywords = { "if", "else", "then", "true", "false", "function" };

    public TokenStream(String stream) {
        this.stream = new InputStream(stream);
    }

    public Token next() {
        Token tok = current;
        current = null;
        if (tok != null) {
            return tok;
        } else {
            return read_next();
        }
    }

    public Token peek() {
        if (current == null) {
            current = read_next();
        }
        return current;
    }

    public boolean eof() {
        return peek() == null;
    }

    public RuntimeException croak(String msg) {
        return stream.croak(msg);
    }

    private Token read_next() {
        clearWhitespace();
        if (stream.eof()) return null;
        char curr = stream.peek();
        if (curr == '#') {
            clearLine();
            return read_next();
        }
        if (curr == '"') return readString();
        if (isDigit.test(curr)) return readNumber();
        if (isIdStart.test(curr)) return readIdent();
        if (isPunc.test(curr)) return new Token(TokenType.PUNC, Character.toString(stream.next()));
        if (isOpChar.test(curr)) return new Token(TokenType.OP, readWhile(isOpChar));

        throw stream.croak("Invalid character: " + curr);
    }

    private String readWhile(Predicate<Character> predicate) {
        StringBuilder result = new StringBuilder();
        while(!stream.eof() && predicate.test(stream.peek())) {
            result.append(stream.next());
        }
        return result.toString();
    }

    private Token readString() {
        return new Token(TokenType.STRING, readEscaped('"'));
    }

    private Token readNumber() {
        return new Token(TokenType.NUM, readWhile(isDigit));
    }

    private Token readIdent() {
        String id = readWhile(isId);
        return new Token(isKeyword.test(id) ? TokenType.KWD : TokenType.VAR, id);
    }

    private String readEscaped(char end) {
        boolean escaped = false;
        StringBuilder result = new StringBuilder();
        stream.next();
        while(!stream.eof()) {
            char x = stream.next();
            if (escaped) {
                result.append(x);
                escaped = false;
            } else if (x == '\\') {
                escaped = true;
            } else if (x == end) {
                break;
            } else {
                result.append(x);
            }
        }
        return result.toString();
    }

    private void clearWhitespace() {
        while (!stream.eof() && isWhiteSpace.test(stream.peek())) stream.next();
    }

    private void clearLine() {
        while (!stream.eof() && stream.peek() != '\n') stream.next();
        stream.next();
    }

    private final Predicate<Character> isDigit = x -> "0123456789".indexOf(x) != -1;

    private final Predicate<Character> isOpChar = x -> "+-*/%=&|<>!".indexOf(x) != -1;

    private final Predicate<Character> isPunc = x -> ",;()[]{}".indexOf(x) != -1;

    private final Predicate<Character> isWhiteSpace = x -> " \n\t".indexOf(x) != -1;

    private final Predicate<String> isKeyword = x -> Arrays.asList(keywords).contains(x);

    private final Predicate<Character> isIdStart = x -> Character.toString(x).matches("[a-z_]");

    private final Predicate<Character> isId = x -> isIdStart.test(x) || Character.toString(x).matches("[A-Z?!-]");

}
