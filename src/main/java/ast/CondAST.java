package ast;

final public class CondAST extends ASTToken {

    PrimitiveAST<?> cond;
    PrimitiveAST<?> then;
    PrimitiveAST<?> elseToken;

    public CondAST(PrimitiveAST<?> cond, PrimitiveAST<?> then) {
        super(ASTType.IF);
        this.cond = cond;
        this.then = then;
    }

    public CondAST(PrimitiveAST<?> cond, PrimitiveAST<?> then, PrimitiveAST<?> elseToken) {
        super(ASTType.IF);
        this.cond = cond;
        this.then = then;
        this.elseToken = elseToken;
    }
}
