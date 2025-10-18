package com.calculator;


import javax.swing.SwingUtilities;

import com.calculator.ui.CalculatorFrame;


public class CalculatorApp {
public static void main(String[] args) {
SwingUtilities.invokeLater(() -> {
CalculatorFrame frame = new CalculatorFrame();
frame.setVisible(true);
});
}
}