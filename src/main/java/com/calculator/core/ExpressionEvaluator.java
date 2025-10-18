package com.calculator.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressionEvaluator {
    private static final Set<String> FUNCTIONS = new HashSet<>(Arrays.asList(
        "sin","cos","tan","asin","acos","atan","ln","log","sqrt","exp","abs"
    ));

    public double eval(String expr, double xValue) throws Exception {
        List<Token> rpn = toRPN(expr);
        Deque<Double> st = new ArrayDeque<>();
        for (Token t : rpn) {
            switch (t.type) {
                case NUMBER: st.push(Double.parseDouble(t.text)); break;
                case VARIABLE: st.push(xValue); break;
                case CONSTANT: st.push(t.text.equals("pi")? Math.PI: Math.E); break;
                case OP: {
                    if (t.text.equals("u-")) {
                        double a = st.pop(); st.push(-a); break;
                    }
                    double b = st.pop(); double a = st.pop();
                    switch (t.text) {
                        case "+": st.push(a+b); break;
                        case "-": st.push(a-b); break;
                        case "*": st.push(a*b); break;
                        case "/": st.push(a/b); break;
                        case "^": st.push(Math.pow(a,b)); break;
                        default: throw new Exception("Unknown operator: " + t.text);
                    }
                } break;
                case FUNC: {
                    double a = st.pop();
                    st.push(applyFunc(t.text, a));
                } break;
                default: throw new Exception("Unexpected token in RPN: " + t.text);
            }
        }
        if (st.size()!=1) throw new Exception("Invalid expression (stack size!=1)");
        return st.pop();
    }

    private double applyFunc(String name, double a) throws Exception {
        switch (name) {
            case "sin": return Math.sin(a);
            case "cos": return Math.cos(a);
            case "tan": return Math.tan(a);
            case "asin": return Math.asin(a);
            case "acos": return Math.acos(a);
            case "atan": return Math.atan(a);
            case "ln": return Math.log(a);
            case "log": return Math.log10(a);
            case "sqrt": return Math.sqrt(a);
            case "exp": return Math.exp(a);
            case "abs": return Math.abs(a);
            default: throw new Exception("Unknown function: " + name);
        }
    }

    private enum Type {NUMBER, OP, FUNC, LPAREN, RPAREN, VARIABLE, CONSTANT}
    private static class Token { Type type; String text; Token(Type t, String s){type=t;text=s;} }

    private List<Token> toRPN(String expr) throws Exception {
        List<String> toks = tokenize(expr);
        List<Token> out = new ArrayList<>();
        Deque<String> ops = new ArrayDeque<>();
        String prev = null;
        for (int i=0;i<toks.size();i++) {
            String tk = toks.get(i);
            if (isNumber(tk)) { out.add(new Token(Type.NUMBER, tk)); }
            else if (tk.equals("x")) { out.add(new Token(Type.VARIABLE, tk)); }
            else if (tk.equals("pi") || tk.equals("e")) { out.add(new Token(Type.CONSTANT, tk)); }
            else if (isIdentifier(tk)) { ops.push(tk); }
            else if (isOp(tk)) {
                if (tk.equals("-") && (prev==null || (isOp(prev) || prev.equals("(") ))) {
                    tk = "u-"; // unary
                }
                while (!ops.isEmpty() && isOp(ops.peek())) {
                    String top = ops.peek();
                    if ( (isLeftAssoc(tk) && precedence(tk) <= precedence(top)) || (!isLeftAssoc(tk) && precedence(tk) < precedence(top)) ) {
                        out.add(new Token(Type.OP, ops.pop()));
                    } else break;
                }
                ops.push(tk);
            } else if (tk.equals("(")) { ops.push(tk); }
            else if (tk.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    String op = ops.pop();
                    if (FUNCTIONS.contains(op)) out.add(new Token(Type.FUNC, op));
                    else out.add(new Token(Type.OP, op));
                }
                if (ops.isEmpty() || !ops.peek().equals("(")) throw new Exception("Mismatched parentheses");
                ops.pop();
                if (!ops.isEmpty() && FUNCTIONS.contains(ops.peek())) {
                    out.add(new Token(Type.FUNC, ops.pop()));
                }
            } else {
                throw new Exception("Unknown token: '"+tk+"'");
            }
            prev = tk;
        }
        while (!ops.isEmpty()) {
            String op = ops.pop();
            if (op.equals("(") || op.equals(")")) throw new Exception("Mismatched parentheses");
            if (FUNCTIONS.contains(op)) out.add(new Token(Type.FUNC, op)); else out.add(new Token(Type.OP, op));
        }
        return out;
    }

    private List<String> tokenize(String s) {
        List<String> out = new ArrayList<>();
        int i=0; s = s.replaceAll("\\s+","");
        while (i<s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c) || c=='.') {
                int j=i+1; while (j<s.length() && (Character.isDigit(s.charAt(j))||s.charAt(j)=='.')) j++;
                out.add(s.substring(i,j)); i=j; continue;
            }
            if (Character.isLetter(c)) {
                int j=i+1; while (j<s.length() && Character.isLetter(s.charAt(j))) j++;
                out.add(s.substring(i,j)); i=j; continue;
            }
            if (c=='+'||c=='-'||c=='*'||c=='/'||c=='^') { out.add(String.valueOf(c)); i++; continue; }
            if (c=='('||c==')' || c==',') { out.add(String.valueOf(c)); i++; continue; }
            i++;
        }
        
        // Add implicit multiplication (e.g., "3x" -> "3*x", "x(" -> "x*(")
        return addImplicitMultiplication(out);
    }
    
    private List<String> addImplicitMultiplication(List<String> tokens) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            String current = tokens.get(i);
            result.add(current);
            
            // Add implicit multiplication between number and variable/function
            if (i < tokens.size() - 1) {
                String next = tokens.get(i + 1);
                if (isNumber(current) && (next.equals("x") || isIdentifier(next) || next.equals("("))) {
                    result.add("*");
                }
                // Add implicit multiplication between variable and number/function
                else if (current.equals("x") && (isNumber(next) || isIdentifier(next) || next.equals("("))) {
                    result.add("*");
                }
                // Add implicit multiplication between ) and number/variable/function
                else if (current.equals(")") && (isNumber(next) || next.equals("x") || isIdentifier(next) || next.equals("("))) {
                    result.add("*");
                }
            }
        }
        return result;
    }

    private boolean isNumber(String s) { try { Double.parseDouble(s); return true;} catch(Exception e){return false;} }
    private boolean isIdentifier(String s) { return FUNCTIONS.contains(s); }
    private boolean isOp(String s) { return s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")||s.equals("^")||s.equals("u-"); }
    private int precedence(String op) {
        switch (op) {
            case "u-": return 5;
            case "^": return 4;
            case "*": case "/": return 3;
            case "+": case "-": return 2;
        }
        return 0;
    }
    private boolean isLeftAssoc(String op) { return !op.equals("^") && !op.equals("u-"); }
}
