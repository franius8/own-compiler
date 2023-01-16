package ast;

final public class FuncAST extends ASTToken {
    String[] vars;
    ASTToken[] body;
    public FuncAST(String[] vars, ASTToken[] body) {
        super(ASTType.FUNC);
        this.vars = vars;
        this.body = body;

    }
}
