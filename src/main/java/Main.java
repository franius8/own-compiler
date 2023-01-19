import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println(args[0]);
        if (args[0] != null) {
            String expression = args[0];
            System.out.println( new Evaluator( new Parser(expression).parse()).evaluate());
        } else {
            System.out.println("Enter expression to evaluate:");
            String expression = scanner.nextLine();
            System.out.println( new Evaluator( new Parser(expression).parse()).evaluate());
        }
        scanner.close();
    }

}
