package ast;

import java.util.Arrays;

final public class FuncAST extends ASTToken {
    String[] vars;
    ASTToken body;
    public FuncAST(String[] vars, ASTToken body) {
        super(ASTType.FUNC);
        this.vars = vars;
        this.body = body;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuncAST funcAST = (FuncAST) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(vars, funcAST.vars)) return false;
        return body.equals(funcAST.body);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(vars);
        result = 31 * result + body.hashCode();
        return result;
    }
}
