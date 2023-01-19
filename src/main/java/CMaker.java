import ast.*;

public class CMaker {

    Parser parser;
    StringBuilder body = new StringBuilder();
    StringBuilder header = new StringBuilder("#include <stdio.h> \n");
    StringBuilder main = new StringBuilder("int main() {");

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
        return String.valueOf(header) + main + body + " return 0; }";
    }

    private String c(ASTToken token) {
        switch (token.getType()) {
            case NUM, STR, BOOL, VAR -> {
                return cPrimitive(token);
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
            default -> throw new RuntimeException("Unknown token type: " + token.getType());
        }
    }

    private String cIf(ASTToken token) {
        return null;
    }

    private String cCall(ASTToken token) {
        return null;
    }

    private String cFunc(ASTToken token) {
        return null;
    }

    private String cAssign(ASTToken token) {
        AssignAST assignToken = (AssignAST) token;
        String type;
        switch (assignToken.getRight().getType()) {
            case NUM -> type = "int";
            case BOOL -> type = "bool";
            case STR -> type = "char[]";
            default -> throw new RuntimeException("Invalid operation type");
        }
        return(type + " " + c(assignToken.getLeft()) + assignToken.getOperator() + c(assignToken.getRight()));
    }

    private String cBinary(ASTToken token) {
        return null;
    }

    private String cPrimitive(ASTToken token) {
        switch (token.getType()) {
            case NUM -> {
                return Integer.toString(((NumAST) token).getValue());
            }
            case BOOL -> {
                return Boolean.toString(((BoolAST) token).getValue());
            }
            case STR -> {
                return '"' + ((StrAST) token).getValue() + '"';
            }
            case VAR -> {
                return ((VarAST) token).getValue();
            }
            default -> throw new RuntimeException("Unknown primitive type: " + token.getType());
        }
    }
}
