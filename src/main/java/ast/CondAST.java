package ast;

import java.util.Objects;

final public class CondAST extends ASTToken {

    ASTToken cond;
    ASTToken then;
    ASTToken elseToken;

    public CondAST(ASTToken cond, ASTToken then) {
        super(ASTType.IF);
        this.cond = cond;
        this.then = then;
        this.elseToken = null;
    }

    public CondAST(ASTToken cond, ASTToken then, ASTToken elseToken) {
        super(ASTType.IF);
        this.cond = cond;
        this.then = then;
        this.elseToken = elseToken;
    }

    public ASTToken getCond() {
        return cond;
    }

    public ASTToken getThen() {
        return then;
    }

    public ASTToken getElseToken() {
        return elseToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CondAST condAST = (CondAST) o;

        if (!cond.equals(condAST.cond)) return false;
        if (!then.equals(condAST.then)) return false;
        return Objects.equals(elseToken, condAST.elseToken);
    }

    @Override
    public int hashCode() {
        int result = cond.hashCode();
        result = 31 * result + then.hashCode();
        result = 31 * result + (elseToken != null ? elseToken.hashCode() : 0);
        return result;
    }
}
