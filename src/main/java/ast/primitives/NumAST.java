package ast.primitives;

import ast.ASTType;

final public class NumAST extends PrimitiveAST<Integer> {
    public NumAST(Integer value) {
        super(ASTType.NUM, value);
    }



}
