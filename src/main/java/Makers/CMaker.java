package Makers;

import Exceptions.InvalidTypeException;
import Parser.Parser;
import ast.*;

import java.util.Arrays;

public class CMaker {

    final Parser parser;
    final StringBuilder body = new StringBuilder();
    final StringBuilder header = new StringBuilder("#include <stdio.h> \n");

    final StringBuilder functionDeclarations = new StringBuilder();
    final StringBuilder main = new StringBuilder("int main() {");

    final StringBuilder functionBlock = new StringBuilder();

    //Work in progress - methods need to be implemented

    public CMaker (String program) {
        this.parser = new Parser(program);
    }

    public String makeC() {
        ProgAST program = parser.parse();
        for (ASTToken token: program.getSequence()) {
            body.append(c(token));
            body.append(";");
        }
        if (!functionDeclarations.isEmpty()) {
            return header.toString() + functionDeclarations + main + body + " return 0; }";
        } else {
            return header.toString() + main + body + " return 0; }";
        }

    }

    private String c(ASTToken token) {
        switch (token.getType()) {
            case NUM, STR, BOOL, VAR -> {
                return PrimitiveMaker.evaluatePrimitive(token);
            }
            case BINARY -> {
                return cBinary(token);
            }
            case ASSIGN -> {
                return cAssign(token);
            }
            case FUNC -> {
                return cFunc(token);
            }
            case CALL -> {
                return cCall(token);
            }
            case IF -> {
                return cIf(token);
            }
            default -> throw new InvalidTypeException("Unknown token type: " + token.getType());
        }
    }

    private String cIf(ASTToken token) {
        return null;
    }

    private String cCall(ASTToken token) {
        return null;
    }

    private String cFunc(ASTToken token) {
        FuncAST funcToken = (FuncAST) token;
        functionDeclarations.append("int ").append(funcToken.getName())
                .append(" (")
                .append(String.join(", ", Arrays.stream(funcToken.getVars()).map(x -> "int " + x).toArray(String[]::new)))
                .append(");");
        return null;
    }

    private String cAssign(ASTToken token) {
        AssignAST assignToken = (AssignAST) token;
        String type;
        switch (assignToken.getRight().getType()) {
            case NUM -> type = "int";
            case BOOL -> type = "bool";
            case STR -> type = "char[]";
            default -> throw new InvalidTypeException("Invalid operation type");
        }
        return(type + " " + c(assignToken.getLeft()) + assignToken.getOperator() + c(assignToken.getRight()));
    }

    private String cBinary(ASTToken token) {
        return null;
    }

}
