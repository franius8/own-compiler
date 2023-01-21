import Evaluator.Evaluator;
import Parser.Parser;

import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        if (args.length > 0) {
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
