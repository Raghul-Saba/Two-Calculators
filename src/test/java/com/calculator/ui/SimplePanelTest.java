package com.calculator.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Unit tests for SimplePanel
public class SimplePanelTest {
    
    @Test
    @DisplayName("SimplePanel should be created successfully")
    void testSimplePanelCreation() {
        SimplePanel panel = new SimplePanel();
        assertNotNull(panel);
    }
    
    @Test
    @DisplayName("SimplePanel should have correct layout")
    void testSimplePanelLayout() {
        SimplePanel panel = new SimplePanel();
        assertTrue(panel.getLayout() instanceof java.awt.BorderLayout);
    }
    
    @Test
    @DisplayName("SimplePanel should contain buttons")
    void testSimplePanelButtons() {
        SimplePanel panel = new SimplePanel();
        
        // Count buttons in the panel
        int buttonCount = 0;
        for (int i = 0; i < panel.getComponentCount(); i++) {
            if (panel.getComponent(i) instanceof JPanel) {
                JPanel buttonPanel = (JPanel) panel.getComponent(i);
                for (int j = 0; j < buttonPanel.getComponentCount(); j++) {
                    if (buttonPanel.getComponent(j) instanceof JButton) {
                        buttonCount++;
                    }
                }
            }
        }
        
        // Should have 20 buttons (5x4 grid)
        assertEquals(20, buttonCount);
    }
}
