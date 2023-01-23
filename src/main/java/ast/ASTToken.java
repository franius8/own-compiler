package ast;

public abstract class ASTToken {
    final protected ASTType type;

    public ASTToken(ASTType type) {
        this.type = type;
    }
    public ASTType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{ type=" + type.toString() + " }";
    }
}
