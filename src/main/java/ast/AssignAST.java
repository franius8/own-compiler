package ast;

final public class AssignAST extends ASTToken{
    private final String operator;
    private final VarAST left;
    private final ASTToken right;
    public AssignAST(String operator, VarAST left, ASTToken right) {
        super(ASTType.ASSIGN);
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public VarAST getLeft() {
        return left;
    }

    public ASTToken getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "Assign{" +
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

        AssignAST assignAST = (AssignAST) o;

        if (!operator.equals(assignAST.operator)) return false;
        if (!left.equals(assignAST.left)) return false;
        return right.equals(assignAST.right);
    }

    @Override
    public int hashCode() {
        int result = operator.hashCode();
        result = 31 * result + left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
