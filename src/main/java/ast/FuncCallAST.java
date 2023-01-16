package ast;

final public class FuncCallAST extends ASTToken {
    FuncAST func;
    PrimitiveAST<?>[] args;

    public FuncCallAST(FuncAST func, PrimitiveAST<?>[] args) {
        super(ASTType.CALL);
        this.func = func;
        this.args = args;
    }
}
