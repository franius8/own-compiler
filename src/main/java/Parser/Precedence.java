package Parser;

import java.util.Map;

public interface Precedence {

    Map<String, Integer> precedence = Map.ofEntries(
            Map.entry("=", 1),
            Map.entry("||", 2),
            Map.entry("&&", 3),
            Map.entry("<", 7),
            Map.entry(">", 7),
            Map.entry("<=", 7),
            Map.entry(">=", 7),
            Map.entry("==", 7),
            Map.entry("!=", 7),
            Map.entry("+", 10),
            Map.entry("-", 10),
            Map.entry("*", 20),
            Map.entry("/", 20),
            Map.entry("%", 20)
    );

    static int getPrecedence(String operator) {
        return precedence.get(operator);
    }
}
