public class InputStream {

    final private char[] stream;
    int position = 0;
    int column = 0;
    int line = 0;

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

    public Throwable croak(String msg) {
        return new RuntimeException(msg + "(" + line + ":" + column + ")");
    }
}
