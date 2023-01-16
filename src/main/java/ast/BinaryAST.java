package ast;

public class BinaryAST extends ASTToken {

    String operator;
    ASTToken left;
    ASTToken right;
    public BinaryAST(String operator, ASTToken left, ASTToken right) {
        super(ASTType.BINARY);
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
