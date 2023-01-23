package Makers;

import Exceptions.InvalidTypeException;
import ast.ASTToken;
import ast.primitives.BoolAST;
import ast.primitives.NumAST;
import ast.primitives.StrAST;
import ast.primitives.VarAST;

public class PrimitiveMaker {

    public static String evaluatePrimitive(ASTToken token) {
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
            default -> throw new InvalidTypeException("Unknown primitive type: " + token.getType());
        }
    }
}
