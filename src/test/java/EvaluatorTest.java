import Evaluator.Evaluator;
import Parser.Parser;
import ast.ProgAST;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @Test
    @DisplayName("Does not throw an exception")
    void evaluateTest() {
        ProgAST prog = new Parser("123").parse();
        Evaluator test = new Evaluator(prog);
        assertDoesNotThrow(test::evaluate);
    }

    @Test
    @DisplayName("Correctly evaluates a simple int expression")
    void evaluateInt() {
        ProgAST prog = new Parser("123").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("123", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a single string expression")
    void evaluateString() {
        ProgAST prog = new Parser("\"Test\"").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("Test", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a single boolean expression")
    void evauateBool() {
        ProgAST prog = new Parser("true").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("true", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a single int binary expression")
    void evaluateSimpleBinary() {
        ProgAST prog = new Parser("2 + 2").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("4", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a longer int binary expression")
    void evaluateLongerBinary() {
        ProgAST prog = new Parser("2 + 2 * 3").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("8", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a complicated int binary expression")
    void evaluateComplicatedBinary() {
        ProgAST prog = new Parser("15 * 4 + 8 * 3 - 4 / 2 + 3").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("85", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a complicated int binary expression with modulus")
    void evaluateBinaryWithModulo() {
        ProgAST prog = new Parser("15 % 4 + 12 % 5 - 16 % 7").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("3", test.evaluate());
    }

    @Test
    @DisplayName("Does not allow division by zero")
    void divisionByZeroNotAllowed() {
        ProgAST prog = new Parser("6 / 0").parse();
        Evaluator test = new Evaluator(prog);
        RuntimeException exception = assertThrows(RuntimeException.class, test::evaluate);
        assertEquals(exception.getMessage(), "Division by 0 not allowed");
    }

    @Test
    @DisplayName("Does not allow division by zero with modulus")
    void divisionByZeroNotAllowedMod() {
        ProgAST prog = new Parser("6 % 0").parse();
        Evaluator test = new Evaluator(prog);
        RuntimeException exception = assertThrows(RuntimeException.class, test::evaluate);
        assertEquals(exception.getMessage(), "Division by 0 not allowed");
    }

    @Test
    @DisplayName("Correctly evaluates a single boolean binary expression with ints - variant 1")
    void evaluateBoolIntBinary() {
        ProgAST prog = new Parser("5 > 1").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("true", test.evaluate());
        prog = new Parser("5 >= 5").parse();
        test = new Evaluator(prog);
        assertEquals("true", test.evaluate());
        prog = new Parser("16 < 5").parse();
        test = new Evaluator(prog);
        assertEquals("false", test.evaluate());
        prog = new Parser("16 != 5").parse();
        test = new Evaluator(prog);
        assertEquals("true", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a single binary expression with bools")
    void evaluateBoolBinary() {
        ProgAST prog = new Parser("true || false").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("true", test.evaluate());
        prog = new Parser("true && false").parse();
        test = new Evaluator(prog);
        assertEquals("false", test.evaluate());
        prog = new Parser("true == true").parse();
        test = new Evaluator(prog);
        assertEquals("true", test.evaluate());
        prog = new Parser("true != true").parse();
        test = new Evaluator(prog);
        assertEquals("false", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates expression with variables")
    void evaluateVar() {
        ProgAST prog = new Parser("x = 2;\n x + 2").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("4", test.evaluate());
        prog = new Parser("x = 2;\ny = 5;\n x + y").parse();
        test = new Evaluator(prog);
        assertEquals("7", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates an if expression")
    void evaluateIf() {
        ProgAST prog = new Parser("x = 2;\ny = 5;\n if true then x else y").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("2", test.evaluate());
        prog = new Parser("x = 2;\ny = 5;\n if false then x else y").parse();
        test = new Evaluator(prog);
        assertEquals("5", test.evaluate());
    }

    @Test
    @DisplayName("Does not throw when evaluating a function")
    void evaluateFunc() {
        ProgAST prog = new Parser("function test (x) x + 5").parse();
        Evaluator test = new Evaluator(prog);
        assertDoesNotThrow(test::evaluate);
    }

    @Test
    @DisplayName("Returns null when only function creation is evaluated")
    void evaluateFuncNull() {
        ProgAST prog = new Parser("function test (x) x + 5").parse();
        Evaluator test = new Evaluator(prog);
        assertNull(test.evaluate());
    }

    @Test
    @DisplayName("Previously defined function may be called")
    void evaluateFuncCall() {
        ProgAST prog = new Parser("function add (x) x + 5; add(5);").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("10", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a Fibonacci sequence recursive function")
    void evaluateFibs() {
        ProgAST prog = new Parser("function fib (n)  if n < 2 then n else fib(n - 1) + fib(n - 2); fib(8);").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("21", test.evaluate());
    }

    @Test
    @DisplayName("Correctly evaluates a factorial calculator recursive function")
    void evaulateFactorial() {
        ProgAST prog = new Parser("function factorial (n) if n == 1 then n else n * factorial(n - 1); factorial(5);").parse();
        Evaluator test = new Evaluator(prog);
        assertEquals("120", test.evaluate());
    }
}