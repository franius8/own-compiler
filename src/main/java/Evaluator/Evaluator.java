package Evaluator;

import Exceptions.CannotEvaluateException;
import Exceptions.UnknownOperatorException;
import ast.*;
import ast.primitives.BoolAST;
import ast.primitives.NumAST;
import ast.primitives.StrAST;
import ast.primitives.VarAST;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class Evaluator {

    final Environment globalEnvironment;
    final ProgAST prog;

    public Evaluator(ProgAST prog, Environment globalEnvironment) {
        this.globalEnvironment = globalEnvironment;
        this.prog = prog;
    }

    public Evaluator(ProgAST prog) {
        this.globalEnvironment = new Environment();
        this.prog = prog;
    }

    public String evaluate() {
        String val = "";
        for (ASTToken token: prog.getSequence()) {
            val = evaluateExp(token, globalEnvironment);
        }
        return val;
    }

    private String evaluateExp(ASTToken token, Environment env) {
        switch (token.getType()) {
            case NUM -> {
                return ((NumAST) token).getValue().toString();
            }
            case STR -> {
                return ((StrAST) token).getValue();
            }
            case BOOL -> {
                return ((BoolAST) token).getValue().toString();
            }
            case VAR -> {
                return Integer.toString(env.get(((VarAST) token).getValue()));
            }
            case ASSIGN -> {
                AssignAST AssignToken = (AssignAST) token;
                if (AssignToken.getLeft().getType() != ASTType.VAR) {
                    throw new RuntimeException("Cannot assign to:" + AssignToken);
                }
                int value = Integer.parseInt(Objects.requireNonNull(evaluateExp(((AssignAST) token).getRight(), env)));
                String name = ((AssignAST) token).getLeft().getValue();
                if (env.lookup(name) == null) {
                    env.def(name, value);
                } else {
                    env.set(name, value);
                }
                return Integer.toString(value);
            }
            case BINARY -> {
                BinaryAST binaryToken = (BinaryAST) token;
                String op = binaryToken.getOperator();
                String left = evaluateExp(binaryToken.getLeft(), env);
                String right = evaluateExp(binaryToken.getRight(), env);
                Integer intLeft = tryParseInt(left);
                Integer intRight = tryParseInt(right);
                if (intLeft != null && intRight != null) {
                    if (isBoolOp.test(op)) {
                        return Boolean.toString(applyBoolOp(op, intLeft, intRight));
                    } else if (isMAthOp.test(op)) {
                        return Integer.toString(applyMathOp(op, intLeft, intRight));
                    }
                } else if (isBoolOp.test(op)) {
                    boolean boolLeft = Boolean.parseBoolean(left);
                    boolean boolRight = Boolean.parseBoolean(right);
                    return Boolean.toString(applyBoolOp(op, boolLeft, boolRight));
                }
            }
            case FUNC -> {
                makeFunction((FuncAST) token, env);
                return null;
            }
            case IF -> {
                CondAST condToken = (CondAST) token;
                boolean cond = Boolean.parseBoolean(evaluateExp(condToken.getCond(), env));
                if (cond) return evaluateExp(condToken.getThen(), env);
                if (condToken.getElseToken() != null) return evaluateExp(condToken.getElseToken(), env);
                return null;
            }
            case CALL -> {
                return callFunction((FuncCallAST) token, env);
            }
            case PROG -> {
                String val = "";
                for (ASTToken tok: ((ProgAST) token).getSequence()) {
                    val = evaluateExp(tok, globalEnvironment);
                }
                return val;
            }
        }
        throw new CannotEvaluateException("Cannot evaluate: " + token);
    }

    private void makeFunction(FuncAST token, Environment env) {
        Environment scope = env.extend();
        for (String funcVar: token.getVars()) {
            scope.def(funcVar, 0);
        }
        token.setEnv(scope);
        env.defFunc(token.getName(), token);
    }

    private String callFunction(FuncCallAST token, Environment env) {
        FuncAST func = env.getFunc(((VarAST) token.getFunc()).getValue());
        Environment scope = env.extend();
        func.setEnv(scope);
        for (int i = 0; i < token.getArgs().size(); i++) {
            ASTToken x = token.getArgs().get(i);
            scope.def(func.getVars()[i], Integer.parseInt(Objects.requireNonNull(evaluateExp(x, env))));
        }
        return evaluateExp(func.getBody(), scope);
    }

    private int applyMathOp(String op, int a, int b) {
        switch (op) {
            case "+" -> {
                return a + b;
            }
            case "-" -> {
                return a - b;
            }
            case "*" -> {
                return a * b;
            }
            case "/" -> {
                return a / div(b);
            }
            case "%" -> {
                return a % div(b);
            }
            default -> throw new UnknownOperatorException("Unknown operator: " + op);
        }
    }
    private boolean applyBoolOp(String op, int a, int b) {
        switch (op) {
            case ">" -> {
                return a > b;
            }
            case "<" -> {
                return a < b;
            }
            case "<=" -> {
                return a <= b;
            }
            case ">=" -> {
                return a >= b;
            }
            case "==" -> {
                return a == b;
            }
            case "!=" -> {
                return a != b;
            }
            default -> throw new UnknownOperatorException("Unknown operator: " + op);
        }
    }

    private boolean applyBoolOp(String op, boolean a, boolean b) {
        switch (op) {
            case "==" -> {
                return a == b;
            }
            case "!=" -> {
                return a != b;
            }
            case "||" -> {
                return a || b;
            }
            case "&&" -> {
                return a && b;
            }
            default -> throw new UnknownOperatorException("Unknown operator: " + op);
        }
    }

    private int div(int x) {
        if (x == 0) throw new NumberFormatException("Division by 0 not allowed");
        return x;
    }

    private final Predicate<String> isBoolOp = x ->
            Arrays.asList(new String[]{">", "<", "<=", ">=", "==", "!=", "||", "&&"}).contains(x);

    private final Predicate<String> isMAthOp = x ->  Arrays.asList(new String[]{"+", "-", "*", "/", "%"}).contains(x);

    private Integer tryParseInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return null;
        }
    }
}
