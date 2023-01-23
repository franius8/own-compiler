package Parser;

import Streams.Token;
import Streams.TokenStream;
import Streams.TokenType;
import ast.*;
import ast.primitives.BoolAST;
import ast.primitives.NumAST;
import ast.primitives.StrAST;
import ast.primitives.VarAST;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Parser {
    final TokenStream stream;

    public Parser(String input) {
        stream = new TokenStream(input);
    }

    public ProgAST parse() {
        return parseTopLevel();
    }

    private <T> ArrayList<T> delimited(String start, String stop, String separator, Supplier<T> func) {
        ArrayList<T> ary = new ArrayList<>();
        boolean first = true;
        skipPunc(start);
        while(stream.eof()) {
            if (isPunc(stop)) break;
            if (first) first = false;
            else skipPunc(separator);
            if (isPunc(stop)) break;
            ary.add(func.get());
        }
        skipPunc(stop);
        return ary;
    }

    private ASTToken parseCall(ASTToken  func) {
        return new FuncCallAST(func, delimited("(", ")", ",", this::parseExpression));
    }

    private ASTToken parseExpression() {
        return maybeCall(() -> maybeBinary(parseAtom(), 0));
    }

    private String parseVarName() {
        Token name = stream.next();
        if (name.type() != TokenType.VAR) throw stream.croak("Expecting variable name");
        return name.value();
    }

    private ProgAST parseTopLevel() {
        ArrayList<ASTToken> progList = new ArrayList<>();
        while(stream.eof()) {
            progList.add(parseExpression());
            if (stream.eof()) skipPunc(";");
        }
        return new ProgAST(progList.toArray(new ASTToken[0]));
    }

    private ASTToken parseProg() {
        ArrayList<ASTToken> prog = delimited("{", "}", ";", this::parseExpression);
        if (prog.size() == 0) return new BoolAST(false);
        if (prog.size() == 1) return prog.get(0);
        return new ProgAST(prog.toArray(new ASTToken[0]));
    }

    private ASTToken parseBool() {
        return new BoolAST(stream.next().value().equals("true"));
    }

    private ASTToken parseAtom() {
        return maybeCall(() -> {
            if (isPunc("(")) {
                stream.next();
                ASTToken exp = parseExpression();
                skipPunc(")");
                return exp;
            }
            if (isPunc("{")) return parseProg();
            if (isKwd("if")) return parseIf();
            if (isKwd("true") || isKwd("false")) return parseBool();
            if (isKwd("function")) {
                stream.next();
                return parseFunction();
            }
            Token tok = stream.next();
            if (tok.type() == TokenType.VAR) return new VarAST(tok.value());
            if (tok.type() == TokenType.NUM) return new NumAST(Integer.parseInt(tok.value()));
            if (tok.type() == TokenType.STRING) return new StrAST(tok.value());
            unexpected();
            return null;
        });
    }

    private ASTToken parseIf() {
        skipKwd("if");
        ASTToken cond = parseExpression();
        if (!isPunc("{")) skipKwd("then");
        ASTToken then = parseExpression();
        CondAST ifBlock;
        if (isKwd("else")) {
            stream.next();
            ASTToken elseEx = parseExpression();
            ifBlock = new CondAST(cond, then, elseEx);
        } else {
            ifBlock = new CondAST(cond, then);
        }
        return ifBlock;
    }

    private ASTToken parseFunction() {
        Token tok = stream.peek();
        if (tok.type() == TokenType.VAR) {
            return new FuncAST(stream.next().value(), delimited("(", ")", ",",
                    this::parseVarName).toArray(new String[0]), parseExpression());
        } else {
            return new FuncAST(delimited("(", ")", ",",
                    this::parseVarName).toArray(new String[0]), parseExpression());
        }
    }

    private ASTToken maybeCall(Supplier<ASTToken> func) {
        ASTToken token = func.get();
        return isPunc("(") ? parseCall(token) : token;
    }

    private ASTToken maybeBinary(ASTToken left, int myPrecedence) {
        if(isOp(null)) {
            Token tok = stream.peek();
            int theirPrecedence = Precedence.getPrecedence(tok.value());
            if (theirPrecedence > myPrecedence) {
                stream.next();
                ASTToken right = maybeBinary(parseAtom(), theirPrecedence);
                ASTToken binary;
                if (tok.value().equals("=")) {
                    if (left.getType() != ASTType.VAR) throw stream.croak("Cannot assign to " + left);
                    binary = new AssignAST(tok.value(), (VarAST) left, right);
                } else {
                    binary = new BinaryAST(tok.value(), left, right);
                }
                return maybeBinary(binary, myPrecedence);
            }
        }
        return left;
    }

    private boolean isPunc(String ch) {
        Token token = stream.peek();
        return token != null && token.type() == TokenType.PUNC && (ch == null || token.value().equals(ch));
    }

    private boolean isKwd(String ch) {
        Token token = stream.peek();
        return token != null && token.type() == TokenType.KWD && (ch == null || token.value().equals(ch));
    }

    private boolean isOp(String ch) {
        Token token = stream.peek();
        return token != null && token.type() == TokenType.OP && (ch == null || token.value().equals(ch));
    }

    private void skipPunc(String ch) {
        if (isPunc(ch)) stream.next();
        else throw stream.croak("Expecting punctuation: \"" + ch + "\"");
    }

    private void skipKwd(String ch) {
        if (isKwd(ch)) stream.next();
        else throw stream.croak("Expecting keyword: \"" + ch + "\"");
    }

    private void skipOp(String ch) {
        if (isOp(ch)) stream.next();
        else throw stream.croak("Expecting operator: \"" + ch + "\"");
    }

    private void unexpected() {
        throw stream.croak("Unexpected token: " + stream.peek().value());
    }
}
