package ast;

final public class BoolAST extends PrimitiveAST<Boolean> {
    public BoolAST(Boolean value) {
        super(ASTType.BOOL, value);
    }
}
