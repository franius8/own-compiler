package ast;

import java.util.Arrays;

public class ProgAST extends ASTToken {
    ASTToken[] sequence;
    public ProgAST(ASTToken[] sequence) {
        super(ASTType.PROG);
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "ProgAST{" +
                "sequence=" + Arrays.toString(sequence) +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgAST progAST = (ProgAST) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(sequence, progAST.sequence);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(sequence);
    }
}
