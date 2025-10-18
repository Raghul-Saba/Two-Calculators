package com.calculator.ui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.calculator.core.ExpressionEvaluator;


public class ScientificPanel extends JPanel {
private final JTextField display = new JTextField();
private final ExpressionEvaluator evaluator = new ExpressionEvaluator();


public ScientificPanel() {
    setLayout(new BorderLayout(6, 6));
    
    // Increase display panel size and font
    display.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
    display.setHorizontalAlignment(SwingConstants.RIGHT);
    display.setPreferredSize(new Dimension(0, 120)); 
    add(display, BorderLayout.NORTH);

    JPanel buttons = new JPanel(new GridLayout(6, 5, 3, 3));
    String[] labels = {
        "C", "<-", "(", ")", "pi",
        "sin", "cos", "tan", "^", "sqrt",
        "7", "8", "9", "/", "log",
        "4", "5", "6", "*", "ln",
        "1", "2", "3", "-", "exp",
        "0", ".", "=", "+", "abs"
    };
    
    for (String lab : labels) {
        JButton b = new JButton(lab);
        
        // Make operators larger and more prominent
        if (lab.matches("[+\\-*/=]")) {
            b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            b.setPreferredSize(new Dimension(70, 45));
            b.setBackground(new Color(70, 130, 180)); // Steel blue
            b.setForeground(Color.WHITE);
        } else if (lab.matches("[0-9]")) {
            // Smaller number buttons
            b.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            b.setPreferredSize(new Dimension(50, 35));
        } else if (lab.matches("(sin|cos|tan|log|ln|exp|sqrt|abs)")) {
            // Function buttons
            b.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            b.setPreferredSize(new Dimension(50, 35));
            b.setBackground(new Color(144, 238, 144)); // Light green
        } else if (lab.matches("(\\^|pi)")) {
            // Power and constant buttons
            b.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            b.setPreferredSize(new Dimension(50, 35));
            b.setBackground(new Color(255, 218, 185)); // Peach
        } else {
            // Other buttons (C, <-, (, ), .)
            b.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            b.setPreferredSize(new Dimension(50, 35));
        }
        
        b.addActionListener(this::onButton);
        buttons.add(b);
    }
    add(buttons, BorderLayout.CENTER);
}


private void onButton(ActionEvent ev) {
    String cmd = ((JButton) ev.getSource()).getText();
    switch (cmd) {
        case "C" -> display.setText("");
        case "<-" -> {
            if (!display.getText().isEmpty()) {
                display.setText(display.getText().substring(0, display.getText().length() - 1));
            }
        }
        case "=" -> evaluate();
        case "pi" -> display.setText(display.getText() + "pi");
        default -> display.setText(display.getText() + cmd);
    }
}

private void evaluate() {
    try {
        double result = evaluator.eval(display.getText(), Double.NaN);
        display.setText(Double.toString(result));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}