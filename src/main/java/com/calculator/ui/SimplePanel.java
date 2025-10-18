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


public class SimplePanel extends JPanel {
private final JTextField display = new JTextField();
private final ExpressionEvaluator evaluator = new ExpressionEvaluator();


public SimplePanel() {
    setLayout(new BorderLayout(6, 6));
    
    // Increase display panel size and font
    display.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
    display.setHorizontalAlignment(SwingConstants.RIGHT);
    display.setPreferredSize(new Dimension(0, 120));
    add(display, BorderLayout.NORTH);

    JPanel buttons = new JPanel(new GridLayout(5, 4, 3, 3));
    String[] labels = {
        "C", "<-", "(", ")",
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", ".", "=", "+"
    };
    
    for (String lab : labels) {
        JButton b = new JButton(lab);
        
        // Make operators larger and more prominent
        if (lab.matches("[+\\-*/=]")) {
            b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
            b.setPreferredSize(new Dimension(80, 50));
            b.setBackground(new Color(70, 130, 180)); // Steel blue
            b.setForeground(Color.WHITE);
        } else if (lab.matches("[0-9]")) {
            // Smaller number buttons
            b.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
            b.setPreferredSize(new Dimension(60, 40));
        } else {
            // Other buttons (C, <-, (, ), .)
            b.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
            b.setPreferredSize(new Dimension(60, 40));
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