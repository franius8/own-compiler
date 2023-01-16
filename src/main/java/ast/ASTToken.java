package ast;

public abstract class ASTToken {
    ASTType type;

    public ASTToken(ASTType type) {
        this.type = type;
    }
    public ASTType getType() {
        return type;
    };
}
