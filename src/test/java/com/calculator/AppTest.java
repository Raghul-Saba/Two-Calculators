package com.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.calculator.core.ExpressionEvaluator;

//Unit tests for Calculator Application
public class AppTest {
    
    private ExpressionEvaluator evaluator;
    
    @BeforeEach
    void setUp() {
        evaluator = new ExpressionEvaluator();
    }
    
    @Test
    @DisplayName("Basic arithmetic operations")
    void testBasicArithmetic() throws Exception {
        assertEquals(5.0, evaluator.eval("2+3", Double.NaN), 0.001);
        assertEquals(1.0, evaluator.eval("3-2", Double.NaN), 0.001);
        assertEquals(6.0, evaluator.eval("2*3", Double.NaN), 0.001);
        assertEquals(2.0, evaluator.eval("6/3", Double.NaN), 0.001);
        assertEquals(8.0, evaluator.eval("2^3", Double.NaN), 0.001);
    }
    
    @Test
    @DisplayName("Complex expressions with parentheses")
    void testComplexExpressions() throws Exception {
        assertEquals(14.0, evaluator.eval("(2+3)*2+4", Double.NaN), 0.001);
        assertEquals(10.0, evaluator.eval("2*(3+2)", Double.NaN), 0.001);
        assertEquals(3.0, evaluator.eval("(1+2)*(2+1)/3", Double.NaN), 0.001);
    }
    
    @Test
    @DisplayName("Mathematical functions")
    void testMathematicalFunctions() throws Exception {
        assertEquals(0.0, evaluator.eval("sin(0)", Double.NaN), 0.001);
        assertEquals(1.0, evaluator.eval("cos(0)", Double.NaN), 0.001);
        assertEquals(0.0, evaluator.eval("tan(0)", Double.NaN), 0.001);
        assertEquals(2.0, evaluator.eval("sqrt(4)", Double.NaN), 0.001);
        assertEquals(1.0, evaluator.eval("abs(-1)", Double.NaN), 0.001);
        assertEquals(1.0, evaluator.eval("abs(1)", Double.NaN), 0.001);
    }
    
    @Test
    @DisplayName("Logarithmic functions")
    void testLogarithmicFunctions() throws Exception {
        assertEquals(1.0, evaluator.eval("ln(e)", Double.NaN), 0.001);
        assertEquals(2.0, evaluator.eval("log(100)", Double.NaN), 0.001);
    }
    
    @Test
    @DisplayName("Constants")
    void testConstants() throws Exception {
        assertEquals(Math.PI, evaluator.eval("pi", Double.NaN), 0.001);
        assertEquals(Math.E, evaluator.eval("e", Double.NaN), 0.001);
    }
    
    @Test
    @DisplayName("Variable substitution")
    void testVariableSubstitution() throws Exception {
        assertEquals(5.0, evaluator.eval("x+2", 3.0), 0.001);
        assertEquals(9.0, evaluator.eval("x^2", 3.0), 0.001);
        assertEquals(1.0, evaluator.eval("sin(x)", Math.PI/2), 0.001);
    }
    
    @Test
    @DisplayName("Unary operations")
    void testUnaryOperations() throws Exception {
        assertEquals(-5.0, evaluator.eval("-5", Double.NaN), 0.001);
        assertEquals(-3.0, evaluator.eval("-(2+1)", Double.NaN), 0.001);
        assertEquals(3.0, evaluator.eval("--3", Double.NaN), 0.001);
    }
    
    @Test
    @DisplayName("Error handling for invalid expressions")
    void testErrorHandling() throws Exception {
        assertThrows(Exception.class, () -> evaluator.eval("2+", Double.NaN));
        assertThrows(Exception.class, () -> evaluator.eval("(2+3", Double.NaN));
        assertThrows(Exception.class, () -> evaluator.eval("2+)", Double.NaN));
        assertThrows(Exception.class, () -> evaluator.eval("unknown(2)", Double.NaN));
        // Note: 2/0 returns Infinity in Java, not an exception
        assertTrue(Double.isInfinite(evaluator.eval("2/0", Double.NaN)));
    }
    
    @Test
    @DisplayName("Edge cases")
    void testEdgeCases() throws Exception {
        assertEquals(0.0, evaluator.eval("0", Double.NaN), 0.001);
        assertEquals(1.0, evaluator.eval("1", Double.NaN), 0.001);
        assertEquals(0.0, evaluator.eval("0*5", Double.NaN), 0.001);
        assertEquals(0.0, evaluator.eval("5*0", Double.NaN), 0.001);
    }
    
    @Test
    @DisplayName("Implicit multiplication")
    void testImplicitMultiplication() throws Exception {
        // Test implicit multiplication with variables
        assertEquals(5.0, evaluator.eval("3x-1", 2.0), 0.001); // 3*2-1 = 5
        assertEquals(8.0, evaluator.eval("2x+4", 2.0), 0.001);  // 2*2+4 = 8
        assertEquals(7.0, evaluator.eval("x+5", 2.0), 0.001);  // 2+5 = 7
        assertEquals(6.0, evaluator.eval("2x", 3.0), 0.001);   // 2*3 = 6
        
        // Test implicit multiplication with functions
        assertEquals(2.0, evaluator.eval("2sin(x)", Math.PI/2), 0.001); // 2*sin(π/2) = 2*1 = 2
        assertEquals(0.0, evaluator.eval("3cos(x)", Math.PI/2), 0.001);  // 3*cos(π/2) = 3*0 = 0
    }
}
