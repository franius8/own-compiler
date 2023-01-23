package Makers;

import Exceptions.InvalidTypeException;
import Parser.Parser;
import ast.*;

public class JSMaker {

    final Parser parser;
    final StringBuilder result = new StringBuilder();

    public JSMaker(String program) {
        this.parser = new Parser(program);
    }

    public String makeJS () {
        ProgAST program = parser.parse();
        for (ASTToken token: program.getSequence()) {
                result.append(js(token));
            }
        return result.toString();
        }

    private String js(ASTToken token) {
        switch (token.getType()) {
            case NUM, STR, BOOL, VAR -> {
                return PrimitiveMaker.evaluatePrimitive(token);
            }
            case BINARY -> {
                return jsBinary(token);
            }
            case ASSIGN -> {
                return jsAssign(token);
            }
            case FUNC -> {
                return jsFunc(token);
            }
            case CALL -> {
                return jsCall(token);
            }
            case IF -> {
                return jsIf(token);
            }
            default -> throw new InvalidTypeException("Unknown token type: " + token.getType());
        }
    }

    private String jsBinary(ASTToken token) {
        BinaryAST binaryToken = (BinaryAST) token;
        return("(" + js(binaryToken.getLeft()) + binaryToken.getOperator() + js(binaryToken.getRight()) + ")");
    }

    private String jsAssign(ASTToken token) {
        AssignAST assignToken = (AssignAST) token;
        return("(" + js(assignToken.getLeft()) + assignToken.getOperator() + js(assignToken.getRight()) + ")");
    }

    private String jsFunc(ASTToken token) {
        FuncAST funcToken = (FuncAST) token;
        return "(function (" + String.join(", ", funcToken.getVars()) + ") { " +
                "return " + js((funcToken).getBody()) + " })";
    }

    private String jsCall(ASTToken token) {
        FuncCallAST funcCallToken = (FuncCallAST) token;
        return (js(funcCallToken.getFunc()) + "(" +
                String.join(", ", funcCallToken.getArgs().stream().map(this::js).toArray(String[]::new)) + ")");
    }

    private String jsIf(ASTToken token) {
        CondAST condToken = (CondAST) token;
        return "("
                +      js(condToken.getCond()) + " !== false"
                +      " ? " + js(condToken.getThen())
                +      " : " + (condToken.getElseToken() != null ? js(condToken.getElseToken()) : "false")
        +  ")";
    }
}
