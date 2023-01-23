package ast.primitives;

import ast.ASTType;

final public class VarAST extends PrimitiveAST<String> {
    public VarAST(String value) {
        super(ASTType.VAR, value);
    }
}
