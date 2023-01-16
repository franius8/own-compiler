package ast;

final public class AssignAST extends ASTToken{
    String operator;
    ASTToken left;
    ASTToken right;
    public AssignAST(String operator, ASTToken left, ASTToken right) {
        super(ASTType.ASSIGN);
        this.operator = operator;
        this.left = left;
        this.right = right;
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
