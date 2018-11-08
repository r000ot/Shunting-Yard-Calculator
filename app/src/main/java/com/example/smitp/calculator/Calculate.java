package com.example.smitp.calculator;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculate {
    private static String[] code;
    private static final String[] operation = new String[]{"+", "-", "×", "÷", "^"};
    // build string with all operations to match
    private static final String allFunction[] = new String[]{"+", "-", "×", "÷", "^", "abs", "cos", "tan", "sin", "π", "e", "√", "ln", "log", "exp"};
    // build string will constant
    private static final String constant[] = new String[]{"π"};
    private Stack<String> mathStack = new Stack<String>();
    private Stack<String> stack = new Stack<String>();
    private int index;
    private ArrayList<String> queue = new ArrayList<String>();
    // define the Calculate class and obtain the expression
    public Calculate(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str);
        ArrayList<String> code = new ArrayList<String>();

        for (int i = 0; tokenizer.hasMoreElements(); i++) {
            code.add(tokenizer.nextToken(" \n\t"));
        }
        str = "";
        for (int i = 0; i < code.size(); i++) {
            str += code.get(i) + " ";
        }

        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            char nextChar = ' ';
            if (str.length() > i + 1) nextChar = str.charAt(i + 1);
            if ((str.charAt(i) == '-' && ("" + nextChar).matches("\\d"))) {
                newStr += " " + str.charAt(i) + "" + nextChar;
                i++;
            } else if (isAny("" + str.charAt(i), operation)) {
                newStr += " " + str.charAt(i) + " ";
            } else if (str.charAt(i) == '(' || str.charAt(i) == ')') {
                newStr += " " + str.charAt(i) + " ";
            } else newStr += str.charAt(i);
        }

        tokenizer = new StringTokenizer(newStr);
        code = new ArrayList<String>();

        for (int i = 0; tokenizer.hasMoreElements(); i++) {
            code.add(tokenizer.nextToken(" \n\t"));
        }
        this.code = new String[code.size()];
        for (int i = 0; i < this.code.length; i++) {
            this.code[i] = code.get(i);
        }
    }
    // get priority of the operations
    public int getPriority(String str)
    {
        int a;
        switch(str)
        {
            case "+":
            case "-":
                a = 1;
                break;
            case "×":
            case "÷":
                a = 2;
                break;
            case "^":
                a = 3;
                break;
            case "√":
            case "tan":
            case "sin":
            case "cos":
            case "ln":
            case "log":
            case "abs":
            case "exp":
                a = 4;
                break;
            case "π":
                a = 5;
                break;
            default:
                a = 0;
                break;
        }
        return a;
    }
    // compare the priority of the operations to determine which one to operate first
    public boolean ComparePriority(String str1,String str2)
    {
        return getPriority(str1) <= getPriority(str2);
    }
    // evaluate an expression
    public String eval() {
        if (code.length == 1) {
            if (isAny(code[0], constant)) {
                return evalConstant(code[0]);
            }
        }
        return evalNum();
    }
    // evaluate the value of constant in the expression
    public String evalConstant(String s) {
        if (s.equals("π")) {
            return "" + Math.PI;
        }
        if (s.equals("e")) {
            return "" + Math.E;
        }
        return s;
    }
    // trans from infix notation to postfix notation (Reverse Polish Notation)
    public String evalNum() {
        int end = code.length;
        for (int j = index; j < end; j++) {
            if (code[j].matches("(\\d+)|(\\-\\d+)") || code[j].matches("(\\d+\\.\\d+)|(\\-\\d+\\.\\d+)") || code[j].matches("\\w") || code[j].matches(".+ .+")) {
                queue.add(code[j]);
            } else if (code[j].equals("(")) {
                mathStack.push(code[j]);
            } else if (code[j].equals(")")) {
                while (!mathStack.empty() && !mathStack.peek().equals("(")) {
                    queue.add(mathStack.pop());
                }
                mathStack.pop();

            } else if (isAny(code[j], allFunction)) {
                while (!mathStack.empty() && isAny(mathStack.peek(), allFunction) && ComparePriority(code[j],mathStack.peek())) {
                    String top = mathStack.pop();
                    queue.add(top);
                }
                mathStack.push(code[j]);
            } else {
                System.out.println("false");
                break;
            }
        }
        while (!mathStack.empty()) {
            queue.add(mathStack.pop());
        }
        return evalRPN();
    }
    // evaluates the finished reverse polish notation string
    public String evalRPN() {
        // System.out.println(queue);
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).matches("(\\d+)|(\\-\\d+)") || queue.get(i).matches("(\\d+\\.\\d+)|(\\-\\d+\\.\\d+)")) {
                mathStack.push(queue.get(i));
            } else if (isAny(queue.get(i), allFunction)) {
                if (isAny(queue.get(i), constant)) {
                    if (queue.get(i).equals("π")) {
                        mathStack.push("" + Math.PI);
                    } else if (queue.get(i).equals("e")) {
                        mathStack.push("" + Math.E);
                    }
                } else {
                    // evaluate all right associative operations
                    double num0 = 0;
                    double num1 = 0;
                    if (!mathStack.isEmpty()) num1 = Double.parseDouble(mathStack.pop());
                    if (!mathStack.isEmpty()) num0 = Double.parseDouble(mathStack.pop());
                    if (queue.get(i).equals("+")) {
                        mathStack.push("" + (num0 + num1));
                    } else if (queue.get(i).equals("-")) {
                        mathStack.push("" + (num0 - num1));
                    } else if (queue.get(i).equals("×")) {
                        mathStack.push("" + (num0 * num1));
                    } else if (queue.get(i).equals("÷")) {
                        mathStack.push("" + (num0 / num1));
                    } else if (queue.get(i).equals("^")) {
                        mathStack.push("" + (Math.pow(num0, num1)));
                    } else if (queue.get(i).equals("abs")) {
                        mathStack.push("" + num0);
                        mathStack.push("" + Math.abs(num1));
                    } else if (queue.get(i).equals("exp")) {
                        mathStack.push("" + num0);
                        mathStack.push("" + Math.exp(num1));
                    } else if (queue.get(i).equals("ln")) {
                        mathStack.push("" + num0);
                        mathStack.push("" + Math.log(num1));
                    } else if (queue.get(i).equals("log")) {
                        mathStack.push("" + num0);
                        mathStack.push("" + Math.log10(num1));
                    } else if (queue.get(i).equals("√")) {
                        mathStack.push("" + num0);
                        mathStack.push("" + Math.sqrt(num1));
                    } else if (queue.get(i).equals("cos")) {
                        mathStack.push("" + num0);
                        mathStack.push("" + Math.cos(Math.toRadians(num1)));
                    } else if (queue.get(i).equals("tan")) {
                        mathStack.push("" + num0);
                        mathStack.push("" + Math.tan(Math.toRadians(num1)));
                    } else if (queue.get(i).equals("sin")) {
                        mathStack.push("" + num0);
                        mathStack.push("" + Math.sin(Math.toRadians(num1)));
                    }
                }
            }
        }

        if (!mathStack.isEmpty()) {
            String top = mathStack.pop();
            return top;
        }
        return "";
    }

    public static boolean isAny(String str, String strings[]) {
        for (int i = 0; i < strings.length; i++) {
            if (str.equals(strings[i])) return true;
        }
        return false;
    }
}