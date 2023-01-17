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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryAST binaryAST = (BinaryAST) o;

        if (!operator.equals(binaryAST.operator)) return false;
        if (!left.equals(binaryAST.left)) return false;
        return right.equals(binaryAST.right);
    }

    @Override
    public int hashCode() {
        int result = operator.hashCode();
        result = 31 * result + left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
