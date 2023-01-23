package Streams;

import Exceptions.ParserException;

public class InputStream {

    final private char[] stream;
    private int position = 0;
    private int column = 0;
    private int line = 0;

    public InputStream(String input) {
        stream = input.toCharArray();
    }

    public char next() {
        char curr = stream[position++];
        if (curr == '\n') {
            line++;
            column = 0;
        } else {
            column++;
        }
        return curr;
    }

    public char peek() {
        return stream[position];
    }

    public boolean eof() {
        return position == stream.length;
    }

    public RuntimeException croak(String msg) {
        return new ParserException(msg + "(" + line + ":" + column + ")");
    }
}
