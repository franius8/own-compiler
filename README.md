# own-compiler

A compiler (to JS and C) and evaluator for a simple programming language (work in progress)

## Getting started

This compiler is written in Java and therefore the JVM must be installed on the user's machine.

The CLI menu displayed after launching the program guides the user through possible choices, including
compilating and evaulating a program.

The language is dynamically typed and currently supports integer numbers and strings.

## Example code snippets

* Adding 5 to a number

    ```
    function add (x) x + 5;
    ```

* Recursive fibonacci

    ```
    function fib (n)  if n < 2 then n else fib(n - 1) + fib(n - 2);
    ```

* Recursive factorial

    ```
    function factorial (n) if n == 1 then n else n * factorial(n - 1);
    ```

## Implemented keywords

* `if-then-else`

    It is a standard conditional. The else branch is optional and is evaluated only if the condition is false.
    ```
    if (cond) then (expr) else (expr);
    ```

* `function`

    It is used to define a function. It is necessary to provide a function name and arguments in parentheses. The function
    body is enclosed in curly brackets or contained on the same line as the definition without curly brackets.

    No return statement is necessary as the last evaluated value is the return value of the function.

    Example of function definition without curly brackets:

    ```
    function test (a, b) a + b;
    ```

    Example of function definition with curly brackets:

    ```
    functions test (a, b) {
        a - b;
    }
    ```

    Any function may be called in the same way as in most other languages:
    
    ```
    test(5, 6);
    ```
    
