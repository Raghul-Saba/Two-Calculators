package com.calculator.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Unit tests for ScientificPanel
public class ScientificPanelTest {
    
    @Test
    @DisplayName("ScientificPanel should be created successfully")
    void testScientificPanelCreation() {
        ScientificPanel panel = new ScientificPanel();
        assertNotNull(panel);
    }
    
    @Test
    @DisplayName("ScientificPanel should have correct layout")
    void testScientificPanelLayout() {
        ScientificPanel panel = new ScientificPanel();
        assertTrue(panel.getLayout() instanceof java.awt.BorderLayout);
    }
    
    @Test
    @DisplayName("ScientificPanel should contain scientific buttons")
    void testScientificPanelButtons() {
        ScientificPanel panel = new ScientificPanel();
        
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
        
        // Should have 30 buttons (6x5 grid)
        assertEquals(30, buttonCount);
    }
}
