import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSMakerTest {

    @Test
    @DisplayName("Does not throw")
    void doesNotThrow() {
        JSMaker test = new JSMaker("true");
        assertDoesNotThrow(test::makeJS);
    }

    @Test
    @DisplayName("Correctly generates a single boolean")
    void generateBinary() {
        JSMaker test = new JSMaker("true");
        assertEquals("true", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates a single int")
    void generateInt() {
        JSMaker test = new JSMaker("5");
        assertEquals("5", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates a single string")
    void generateString() {
        JSMaker test = new JSMaker("\"test\"");
        assertEquals("\"test\"", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates a single var expression")
    void generateVar() {
        JSMaker test = new JSMaker("test");
        assertEquals("test", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates a simple binary expression")
    void generateSimpleBinary() {
        JSMaker test = new JSMaker("2 + 2");
        assertEquals("(2+2)", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates a more complicated binary expression")
    void generatesComplicatedBinary() {
        JSMaker test = new JSMaker("2 + 2 * 3");
        assertEquals("(2+(2*3))", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates an assign expression")
    void generatesAssign() {
        JSMaker test = new JSMaker("a = 2");
        assertEquals("(a=2)", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates function expression")
    void generateFunction() {
        JSMaker test = new JSMaker("function (x) 10");
        assertEquals("(function (x) { return 10 })", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates a conditional expression")
    void generateIf() {
        JSMaker test = new JSMaker("if eggs then chicken");
        assertEquals("(eggs !== false ? chicken : false)", test.makeJS());
    }

    @Test
    @DisplayName("Correctly generates a call expression")
    void generateCall() {
        JSMaker test = new JSMaker("foo(a, 1)");
        assertEquals("foo(a, 1)", test.makeJS());
    }

}