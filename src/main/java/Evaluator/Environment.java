package Evaluator;

import ast.FuncAST;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private final HashMap<String, FuncAST> funcs;
    private final Environment parent;
    private final HashMap<String, Integer> vars;

    public Environment(Environment parent) {
        this.parent = parent;
        vars = new HashMap<>();
        this.funcs = new HashMap<>();
    }

    public Environment() {
        this.parent = null;
        vars = new HashMap<>();
        this.funcs = new HashMap<>();
    }

    public Environment extend() {
        return new Environment(this);
    }
    public Environment lookup(String name) {
        Environment scope = this;
        while (scope != null) {
            if (scope.getVars() != null && scope.hasVar(name)) return scope;
            scope = scope.getParent();
        }
        return null;
    }

    public int get(String name) {
        if (vars.containsKey(name)) return vars.get(name);
        Environment scope = lookup(name);
        if (scope != null && scope.getVars().containsKey(name)) return scope.get(name);
        throw new RuntimeException("Undefined variable: " + name);
    }

    public void set(String name, int value) {
        Environment scope = lookup(name);
        if (scope == null && parent == null) {
            throw new RuntimeException("Undefined variable: " + name);
        }
        if (scope == null) {
            vars.put(name, value);
        } else {
            scope.def(name, value);
        }
    }

    public void def(String name, int value) {
        vars.put(name, value);
    }

    public Environment getParent() {
        return parent;
    }

    public Map<String, Integer> getVars() {
        return vars;
    }

    public Map<String, FuncAST> getFuncs() {
        return funcs;
    }

    public void defFunc(String name, FuncAST func) {
        funcs.put(name, func);
    }

    public FuncAST getFunc(String name) {
        if (funcs.containsKey(name)) return funcs.get(name);
        Environment scope = lookupFunc(name);
        if (scope != null) return scope.getFunc(name);
        throw new RuntimeException("Undefined variable: " + name);
    }

    public Environment lookupFunc(String name) {
        Environment scope = this;
        while (scope != null) {
            if (scope.getFuncs() != null && scope.hasFunc(name)) return scope;
            scope = scope.getParent();
        }
        return null;
    }

    public FuncAST setFunc(String name, FuncAST value) {
        Environment scope = lookup(name);
        if (scope == null && parent == null) {
            throw new RuntimeException("Undefined variable: " + name);
        }
        if (scope == null) {
            funcs.put(name, value);
        } else {
            scope.defFunc(name, value);
        }
        return value;
    }

    private boolean hasVar(String name) {
        return vars.containsKey(name);
    }

    private boolean hasFunc(String name) {
        return funcs.containsKey(name);
    }

    @Override
    public String toString() {
        return "Environment{" +
                ", parent=" + parent +
                ", vars=" + vars +
                '}';
    }
}
