package ast;

abstract class PrimitiveAST<T> extends ASTToken {
    T value;

    public PrimitiveAST(ASTType type, T value) {
        super(type);
        this.value = value;
    }

    public T getValue() {
        return value;
    };
}
