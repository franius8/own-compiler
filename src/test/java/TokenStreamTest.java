import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenStreamTest {

    TokenStream test;
    Token testToken;

    @Test
    @DisplayName("Token stream correctly read an operator")
    void opTest() {
        char[] operators = "+-*/%=&|<>!".toCharArray();

        for (char c: operators) {
            String cString = Character.toString(c);
            test = new TokenStream(cString);
            testToken = new Token(TokenType.OP, cString);
            assertEquals(testToken, test.next());
        }
    }

    @Test
    @DisplayName("Token stream correctly read punctuation")
    void puncTest() {
        char[] punctuation = ".:()[]{}".toCharArray();

        for (char c: punctuation) {
            String cString = Character.toString(c);
            test = new TokenStream(cString);
            testToken = new Token(TokenType.PUNC, cString);
            assertEquals(testToken, test.next());
        }
    }

    @Test
    @DisplayName("Token stream correctly reads strings")
    void stringTest() {
        String[] testStrings = {"\"aaa\"", "\"test\"", "\"Cat sat on the mat\"", "\"Test, test, test\""};
        for (String str: testStrings) {
            test = new TokenStream(str);
            testToken = new Token(TokenType.STRING, str.replace("\"", ""));
            assertEquals(testToken, test.next());
        }
    }

    @Test
    @DisplayName("Token stream correctly reads numbers")
    void numberTest() {
        String[] testStrings = {"1", "1234", "13445", "434546556", "213721372137"};
        for (String str: testStrings) {
            test = new TokenStream(str);
            testToken = new Token(TokenType.NUM, str);
            assertEquals(testToken, test.next());
        }
    }

    @Test
    @DisplayName("Token stream correctly reads keywords")
    void keywordTest() {
        String[] testStrings = { "if", "else" };
        for (String str: testStrings) {
            test = new TokenStream(str);
            testToken = new Token(TokenType.KWD, str);
            assertEquals(testToken, test.next());
        }
    }

    @Test
    @DisplayName("Token stream correctly reads identifiers")
    void idTest() {
        String[] testStrings = { "cat", "mat", "_pope", "pope?", "testing!", "_testing", "snakeCase", "_testingSnakeCase"};
        for (String str: testStrings) {
            test = new TokenStream(str);
            testToken = new Token(TokenType.VAR, str);
            assertEquals(testToken, test.next());
        }
    }

    @Test
    @DisplayName("Input stream correctly returns null when eof reached")
    void nullTest() {
        TokenStream test = new TokenStream("");
        assertEquals(null, test.next());
    }

    @Test
    @DisplayName("Input stream correctly ignores comments")
    void commentTest() {
        TokenStream test  = new TokenStream("#Comment\n");
        assertEquals(null, test.next());
    }

    @Test
    @DisplayName("Input stream correctly ignores whitespace")
    void whiteSpaceTest() {
        TokenStream test = new TokenStream("  .    ");
        Token testToken = new Token(TokenType.PUNC, ".");
        assertEquals(testToken, test.next());
    }

    @Test
    @DisplayName("Input stream correctly read an entire line with int assignment")
    void intLineTest() {
        TokenStream test = new TokenStream("test = 5");
        Token testToken = new Token(TokenType.VAR, "test");
        assertEquals(testToken, test.next());
        testToken = new Token(TokenType.OP, "=");
        assertEquals(testToken, test.next());
        testToken = new Token(TokenType.NUM, "5");
        assertEquals(testToken, test.next());
    }

    @Test
    @DisplayName("Input stream correctly read an entire line with string assignment")
    void stringLineTest() {
        TokenStream test = new TokenStream("test = \"test\"");
        Token testToken = new Token(TokenType.VAR, "test");
        assertEquals(testToken, test.next());
        testToken = new Token(TokenType.OP, "=");
        assertEquals(testToken, test.next());
        testToken = new Token(TokenType.STRING, "test");
        assertEquals(testToken, test.next());
    }

    @Test
    @DisplayName("Input stream correctly read a bool keyword")
    void boolTest() {
        TokenStream test = new TokenStream("true false");
        Token testToken = new Token(TokenType.KWD, "true");
        assertEquals(testToken, test.next());
        testToken = new Token(TokenType.KWD, "false");
        assertEquals(testToken, test.next());
    }
    @Test
    @DisplayName("Input stream correctly read an entire line with string assignment and semicolon ending")
    void stringLineSemicolonTest() {
        TokenStream test = new TokenStream("test = \"test\";");
        Token testToken = new Token(TokenType.VAR, "test");
        assertEquals(testToken, test.next());
        testToken = new Token(TokenType.OP, "=");
        assertEquals(testToken, test.next());
        testToken = new Token(TokenType.STRING, "test");
        assertEquals(testToken, test.next());
        testToken = new Token(TokenType.PUNC, ";");
        assertEquals(testToken, test.next());
    }

    @Test
    @DisplayName("Does not allow unknown characters")
    void unknownCharactersTest() {
        TokenStream test = new TokenStream("@");
        assertThrows(RuntimeException.class, test::next);
    }
}