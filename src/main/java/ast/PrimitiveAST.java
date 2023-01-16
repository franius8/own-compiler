package ast;

public abstract class PrimitiveAST<T> extends ASTToken {
    T value;

    public PrimitiveAST(ASTType type, T value) {
        super(type);
        this.value = value;
    }

    public T getValue() {
        return value;
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
