import ast.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    @DisplayName("Parser does not generate any errors with correct integer input")
    void parse() {
        Parser parser = new Parser("123");
        assertDoesNotThrow(parser::parse);
    }

    @Test
    @DisplayName("Correctly parses an integer input")
    void parseInt() {
        Parser parser = new Parser("123");
        ASTToken[] astAry = { new NumAST(123) };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Correctly parses a string input")
    void parseString() {
        Parser parser = new Parser("\"test\"");
        ASTToken[] astAry = { new StrAST("test") };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Correctly parses boolean multiple boolean inputs")
    void parseBool() {
        Parser parser = new Parser("true false");
        ASTToken[] astAry = { new BoolAST(true), new BoolAST(false) };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Correctly parses an identifier")
    void parseVar() {
        Parser parser = new Parser("eggs");
        ASTToken[] astAry = { new VarAST("eggs") };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Correctly parses function")
    void parseFunc() {
        Parser parser = new Parser("function (x) 10");
        String[] args = { "x" };
        ASTToken[] astAry = { new FuncAST(args, new NumAST(10)) };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Correctly parses a function call")
    void parseCall() {
        Parser parser = new Parser("foo(a, 1)");
        String[] args = { "x" };
        ASTToken[] astAry = { new FuncAST(args, new NumAST(10)) };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Parses one branch if statement correctly")
    void parseSimpleIf() {
        Parser parser = new Parser("if eggs then chicken");
        ASTToken[] astAry = { new CondAST(new VarAST("eggs"), new VarAST("chicken")) };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Parses if-else statement correctly")
    void parseIfElse() {
        Parser parser = new Parser("if eggs then chicken else scrambled");
        ASTToken[] astAry = { new CondAST(new VarAST("eggs"), new VarAST("chicken"),
                new VarAST("scrambled")) };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Parses assignment correctly")
    void parseAssignment() {
        Parser parser = new Parser("a = 10");
        ASTToken[] astAry = { new AssignAST("=", new VarAST("a"), new NumAST(10)) };
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Parses a binary expression")
    void parseBinary() {
        Parser parser = new Parser("x + y * z");
        BinaryAST right = new BinaryAST("*", new VarAST("y"), new VarAST("z"));
        ASTToken[] astAry = { new BinaryAST("+", new VarAST("x"), right)};
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

    @Test
    @DisplayName("Entire program (with semicolons) parsed correctly")
    void parseProg() {
        Parser parser = new Parser("""
                  a = 5;
                  b = a * 2;
                  a + b;
                  """);
       ASTToken[] astAry = { new AssignAST("=", new VarAST("a"), new NumAST(5)),
        new AssignAST("=", new VarAST("b"), new BinaryAST("*", new VarAST("a"), new NumAST(2))),
        new BinaryAST("+", new VarAST("a"), new VarAST("b"))};
        ProgAST prog = new ProgAST(astAry);
        assertEquals(prog, parser.parse());
    }

}