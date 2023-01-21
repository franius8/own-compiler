package ast;

import Evaluator.Environment;

import java.util.Arrays;
import java.util.Objects;

final public class FuncAST extends ASTToken {
    String name;
    String[] vars;
    ASTToken body;

    Environment env;

    public FuncAST(String[] vars, ASTToken body) {
        super(ASTType.FUNC);
        this.vars = vars;
        this.body = body;
        this.name = null;

    }

    public FuncAST(String name, String[] vars, ASTToken body) {
        super(ASTType.FUNC);
        this.name = name;
        this.vars = vars;
        this.body = body;
    }

    public String[] getVars() {
        return vars;
    }

    public ASTToken getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    @Override
    public String toString() {
        return "FuncAST{" +
                "name='" + name + '\'' +
                ", vars=" + Arrays.toString(vars) +
                ", body=" + body +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuncAST funcAST = (FuncAST) o;

        if (!Objects.equals(name, funcAST.name)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(vars, funcAST.vars)) return false;
        return body.equals(funcAST.body);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(vars);
        result = 31 * result + body.hashCode();
        return result;
    }
}
