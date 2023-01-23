import Evaluator.Evaluator;
import Exceptions.CustomRuntimeException;
import Makers.CMaker;
import Makers.JSMaker;
import Parser.Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        if (args.length > 0) {
            String expression = args[0];
            System.out.println( new Evaluator( new Parser(expression).parse()).evaluate());
        } else {
            outerloop:
            while (true) {
                System.out.println("Choose action:");
                System.out.println("1. Input program directly into the terminal and evaluate");
                System.out.println("2. Load a program from file and evaluate");
                System.out.println("3. Load a program from file and compile");
                System.out.println("4. Exit");
                switch (scanner.next()) {
                    case "1" -> evaluateFromTerminal();
                    case "2" -> evaluateFromFile();
                    case "4" -> {
                        break outerloop;
                    }
                    default -> System.out.println("Invalid choice, try again");
                }
            }
        }
        scanner.close();
    }

    private static void evaluateFromTerminal() {
        while (true) {
            System.out.println("Enter expression to evaluate or type 'exit':");
            String expression = scanner.nextLine().replace("\n", "n");
            if (expression.equals("exit")) return;
            try {
                System.out.println(new Evaluator(new Parser(expression).parse()).evaluate());
            } catch (CustomRuntimeException e) {
                System.out.println("Program evaluation failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unknown error: " + e.getMessage());
            }
        }
    }

    private static void evaluateFromFile() {

        System.out.println("Enter the name of the file to open:");

        String contents;

        try {
           contents = Files.readString(Paths.get(scanner.next()));
        } catch (IOException e) {
            System.out.println("Unable to open file");
            return;
        }

        try {
            System.out.println(new Evaluator(new Parser(contents).parse()).evaluate());
        } catch (CustomRuntimeException e) {
            System.out.println("Program evaluation failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unknown error: " + e.getMessage());
        }
    }

    private static void compileFromFile() {
        System.out.println("Enter the name of the file to open:");

        String contents;

        try {
            contents = Files.readString(Paths.get(scanner.next()));
        } catch (IOException e) {
            System.out.println("Unable to open file");
            return;
        }

        int language;

        System.out.println("Choose the output language: 1 - JavaScript, 2 - C:");
        switch (scanner.next()) {
            case "1" -> language = 1;
            case "2" -> language = 2;
            default -> {
                System.out.println("Invalid choice");
                return;
            }
        }

        System.out.println("Enter the output file name:");

        String outputName = scanner.next();

        try (FileWriter output = new FileWriter(outputName)) {
            switch (language) {
                case 1 -> output.write(new JSMaker(contents).makeJS());
                case 2 -> output.write(new CMaker(contents).makeC());
            }
        } catch (IOException e) {
            System.out.println("Unable to write to file");
        } catch (CustomRuntimeException e) {
            System.out.println("Error while evaluating file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unknown error: " + e.getMessage());
        }
    }

}
