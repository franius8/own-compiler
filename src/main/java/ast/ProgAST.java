package ast;

public class ProgAST extends ASTToken {
    ASTToken[] sequence;
    public ProgAST(ASTToken[] sequence) {
        super(ASTType.PROG);
        this.sequence = sequence;
    }
}
