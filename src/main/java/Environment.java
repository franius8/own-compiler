import java.util.HashMap;

public class Environment {

    private Environment parent;

    private HashMap<String, Integer> vars;

    public Environment(Environment parent) {
        this.parent = parent;
        vars = parent.getVars();
    }

    public Environment() {
        this.parent = null;
        vars = new HashMap<>();
    }

    public Environment extend() {
        return new Environment(this);
    }
    public Environment lookup(String name) {
        Environment scope = this;
        while (scope != null) {
            if (scope.getVars() != null) return scope;
            scope = scope.getParent();
        }
        return null;
    }

    public int get(String name) {
        if (vars.containsKey(name)) return vars.get(name);
        throw new RuntimeException("Undefined variable: " + name);
    }

    public int set(String name, int value) {
        Environment scope = lookup(name);
        if (scope == null && parent == null) {
            throw new RuntimeException("Undefined variable: " + name);
        }
        if (scope == null) {
            vars.put(name, value);
        } else {
            scope.putVar(name, value);
        }
        return value;
    }

    public void def(String name, int value) {
        vars.put(name, value);
    }

    public Environment getParent() {
        return parent;
    }

    public HashMap<String, Integer> getVars() {
        if (parent == null) {
            return null;
        }
        return vars;
    }

    private void putVar(String name, int value) {
        vars.put(name, value);
    }
}
