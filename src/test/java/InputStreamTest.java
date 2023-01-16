import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputStreamTest {

    InputStream test;

    @BeforeEach
    void setUp() {
        test = new InputStream("abcd");
    }

    @Test
    @DisplayName("Next method should return the next character in the stream")
    void next() {
        char testChar = 'a';
        for (int i = 0; i < 4; i++) {
            assertEquals(testChar, test.next());
            testChar++;
        }
    }

    @Test
    @DisplayName("Peek method should return the next character in the stream without removing it")
    void peek() {
        for (int i = 0; i < 4; i++) {
            assertEquals('a', test.peek());
        }
    }

   @Test
    @DisplayName("Eof should return false when there are characters remaining in the stream")
    void eof() {
       assertFalse(test.eof());
        test.next();
    }

    @Test
    @DisplayName("Eof should return true when end of file has been reached")
    void eof2() {
        for (int i = 0; i < 4; i++) {
            test.next();
        }
        assertTrue(test.eof());
    }

    @Test
    @DisplayName("Correctly reads a whole string")
    void wholeString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            result.append(test.next());
        }
        assertEquals("abcd", result.toString());
    }

    @Test
    @DisplayName("Correctly returns an exception with a given message")
    void croak() {
        RuntimeException exception = test.croak("test");
        assertEquals("test(0:0)", exception.getMessage());
    }

    @Test
    @DisplayName("Correctly displays line number")
    void croak2() {
        test.next();
        test.next();
        RuntimeException exception = test.croak("test");
        assertEquals("test(0:2)", exception.getMessage());
    }
}