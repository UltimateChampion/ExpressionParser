/**
 * Created by AmierNaji on 1/7/15.
 */

import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;


public class ExpressionParser {

    public static void main(String[] args) throws Exception {

        //parseNumbers("56.87 + 89 - 45 / 3");
        System.out.println(isBalanced("(()())"));

        String test = "(43 ^ .86  + ((10 + 89) - 45) / 3";
        System.out.println(Arrays.toString(tokenize(test).toArray()));
        System.out.println(parseRPN(convertToRPN(test)));
    }

    public static String parseString(String s) throws Exception {

        double x = Double.parseDouble(parseRPN(convertToRPN(s)));
        String out = ((double)Math.round(x * 100000) / 100000) + "";
        return out;
    }

    public static String parseRPN(String s) {

        Stack<String> stack = new Stack<String>();
        String[] tokens = fixRPNString(s).split(" ");

        System.out.println(Arrays.toString(tokens));
        for (int i = 0; i < tokens.length; i++) {

            if (tokens[i].length() == 1 && isOperator(tokens[i].charAt(0))) {

                String b = stack.pop();
                String a = stack.pop();

                stack.push(calc(a, b, tokens[i].charAt(0)));

            } else {

                stack.push(tokens[i]);
            }

        }
        return stack.peek();
    }

    public static String fixRPNString(String s) {

        String out = s.replaceAll(" {2,}", " ");
        return out;
    }

    public static String fixRegularString(String s) {

        String out = s.replaceAll(" {2,}", " ");
        out = out.replaceAll("\\) {1,}\\(", ")*(");
        return out;
    }

    public static String calc(String a, String b, char op) {

        double x = Double.parseDouble(a);
        double y = Double.parseDouble(b);

        switch(op) {

            case '+':
                return (x + y) + "";
            case '-':
                return (x - y) + "";
            case '*':
                return (x * y) + "";
            case '/':
                return (x / y) + "";
            case '^':
                return (Math.pow(x, y)) + "";
        }

        return 0 + "";
    }

    public static String convertToRPN(String s) throws Exception {

        StringBuffer outQueue = new StringBuffer();
        Stack<Character> stack = new Stack<Character>();

        ArrayList<String> tokens = tokenize(s);

        for (int i = 0; i < tokens.size(); i++) {

            if (!isOperator(tokens.get(i).charAt(0))) {

                outQueue.append(tokens.get(i) + " ");
            }

            else {

                if (tokens.get(i).charAt(0) == '(') {

                    stack.push(tokens.get(i).charAt(0));
                    continue;
                }

                if (tokens.get(i).charAt(0) == ')') {

                    while (stack.peek() != '(') {

                        outQueue.append(stack.pop() + " ");
                    }

                    stack.pop();
                    continue;
                }

                while (stack.size() != 0) {

                    if (opPrecedence(stack.peek()) <= opPrecedence(tokens.get(i).charAt(0))) { break; }

                    outQueue.append(stack.pop() + " ");
                }

                stack.push(tokens.get(i).charAt(0));
            }
        }

        while (stack.size() > 0) {

            outQueue.append(stack.pop() + " ");
        }

        return outQueue.toString();
    }

    public static ArrayList<String> tokenize(String s) throws Exception {

        ArrayList<String> out = new ArrayList<String>();
        StringBuffer curr = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == ' ') { continue; }

            if (isOperator(s.charAt(i))) {

                if (curr.toString().length() > 0) {

                    double temp = Double.parseDouble(curr.toString());
                    out.add(temp + "");
                }

                if (!curr.toString().equals(s.charAt(i) + "")) {

                    out.add(s.charAt(i) + "");
                }

                curr.delete(0, curr.length());
                continue;
            }

            curr.append(s.charAt(i));
        }

        if (curr.length() > 0) {

            double temp = Double.parseDouble(curr.toString());
            out.add(temp + "");
        }

        return out;
     }

    public static boolean isOperator(char x) {

        return ((x == '+') || (x == '-') || (x == '*') || (x == '/') || (x == '^') || (x == '(') || (x == ')'));
    }


    public static int opPrecedence(char c) {

        switch(c) {

            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            case '^':
                return 3;
        }

        return -1;
    }

    public static boolean isBalanced(String s) {

        Stack<Character> x = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '(') {

                x.push('(');
                continue;
            }

            if (s.charAt(i) == ')') {

                if (x.size() > 0 && x.peek() == '(') {

                    x.pop();
                    continue;
                }

                x.push(')');
            }
        }

        return (x.size() == 0);
    }

}
