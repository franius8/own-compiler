package ast;

final public class AssignAST extends ASTToken{
    String operator;
    PrimitiveAST<?> left;
    PrimitiveAST<?> right;
    public AssignAST() {
        super(ASTType.ASSIGN);

    }
}
