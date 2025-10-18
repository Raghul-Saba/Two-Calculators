package com.calculator.ui;

import javax.swing.JFrame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Unit tests for CalculatorFrame
public class CalculatorFrameTest {
    
    @Test
    @DisplayName("CalculatorFrame should be created successfully")
    void testCalculatorFrameCreation() {
        CalculatorFrame frame = new CalculatorFrame();
        assertNotNull(frame);
        assertEquals("CalculatoR", frame.getTitle());
        assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
    }
    
    @Test
    @DisplayName("CalculatorFrame should have correct size")
    void testCalculatorFrameSize() {
        CalculatorFrame frame = new CalculatorFrame();
        assertEquals(900, frame.getWidth());
        assertEquals(650, frame.getHeight());
    }
    
    @Test
    @DisplayName("CalculatorFrame should be visible")
    void testCalculatorFrameVisibility() {
        CalculatorFrame frame = new CalculatorFrame();
        frame.setVisible(true);
        assertTrue(frame.isVisible());
    }
}
