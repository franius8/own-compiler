package ast;

final public class StrAST extends PrimitiveAST<String> {
    public StrAST(String value) {
        super(ASTType.STR, value);
    }
}
