package ast;

import java.util.ArrayList;

final public class FuncCallAST extends ASTToken {
    FuncAST func;
    ArrayList<ASTToken> args;

    public FuncCallAST(FuncAST func, ArrayList<ASTToken> args) {
        super(ASTType.CALL);
        this.func = func;
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuncCallAST that = (FuncCallAST) o;

        if (!func.equals(that.func)) return false;
        return args.equals(that.args);
    }

    @Override
    public int hashCode() {
        int result = func.hashCode();
        result = 31 * result + args.hashCode();
        return result;
    }
}