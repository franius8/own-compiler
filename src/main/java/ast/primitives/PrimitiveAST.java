package ast.primitives;

import ast.ASTToken;
import ast.ASTType;

public abstract class PrimitiveAST<T> extends ASTToken {
    final T value;

    public PrimitiveAST(ASTType type, T value) {
        super(type);
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Primitive{" +
                "value=" + value +
                ", type=" + type.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrimitiveAST<?> that = (PrimitiveAST<?>) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
