package com.calculator.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Unit tests for GraphPanel
public class GraphPanelTest {
    
    @Test
    @DisplayName("GraphPanel should be created successfully")
    void testGraphPanelCreation() {
        GraphPanel panel = new GraphPanel();
        assertNotNull(panel);
    }
    
    @Test
    @DisplayName("GraphPanel should have correct layout")
    void testGraphPanelLayout() {
        GraphPanel panel = new GraphPanel();
        assertTrue(panel.getLayout() instanceof java.awt.BorderLayout);
    }
    
    @Test
    @DisplayName("GraphPanel should have proper components")
    void testGraphPanelComponents() {
        GraphPanel panel = new GraphPanel();
        
        // Should have at least 2 components (top panel and plot panel)
        assertTrue(panel.getComponentCount() >= 2);
        
        // Should have BorderLayout
        assertTrue(panel.getLayout() instanceof java.awt.BorderLayout);
    }
}
