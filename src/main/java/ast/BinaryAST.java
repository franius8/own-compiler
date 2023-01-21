package ast;

public class BinaryAST extends ASTToken {

    final String operator;
    final ASTToken left;
    final ASTToken right;
    public BinaryAST(String operator, ASTToken left, ASTToken right) {
        super(ASTType.BINARY);
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public ASTToken getLeft() {
        return left;
    }

    public ASTToken getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "BinaryAST{" +
                "operator='" + operator + '\'' +
                ", left=" + left +
                ", right=" + right +
                ", type=" + type +
                '}';
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
