/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

/**
 *
 * @author Douglas
 */
import static com.sun.org.apache.xerces.internal.util.XMLChar.trim;
import java.util.*;

public class ShuntingYard {

    private enum Operator {

        ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4);
        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    private static Map<String, Operator> ops = new HashMap<String, Operator>() {
        {
            put("+", Operator.ADD);
            put("-", Operator.SUBTRACT);
            put("*", Operator.MULTIPLY);
            put("/", Operator.DIVIDE);
        }
    };

    private static boolean isHigerPrec(String op, String sub) {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }

    public static List<String> postfix(String infix) {
        List<String> output = new LinkedList<>();
        Deque<String> stack = new LinkedList<>();
        String[] temp = infix.split("");
        String token = "";
        int i;
        for (i = 0; i < temp.length; i++) {
            // operator
            token = trim(temp[i]);
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && isHigerPrec(token, stack.peek())) {
                    output.add(stack.pop());
                }
                stack.push(token);

                // left parenthesis
            } else if (token.equals("(")) {
                stack.push(token);

                // right parenthesis
            } else if (token.equals(")")) {
                while (!stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();

                // digit or word
            } else {
                String tempString = "";
                if (!token.isEmpty()) {
                    while (!ops.containsKey(token) && !token.equals("(") && !token.equals(")")) {
                        tempString = tempString.concat(token);
                        if (i < temp.length - 1) {
                            token = trim(temp[++i]);
                        } else {
                            break;
                        }
                    }
                    output.add(tempString);
                    if (i < temp.length - 1) i--;
                }

            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

}