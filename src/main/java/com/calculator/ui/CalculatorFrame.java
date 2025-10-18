package com.calculator.ui;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class CalculatorFrame extends JFrame {
public CalculatorFrame() {
super("CalculatoR");
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setSize(900, 650);
setLocationRelativeTo(null);


JTabbedPane tabs = new JTabbedPane();
tabs.addTab("Simple", new SimplePanel());
tabs.addTab("Scientific", new ScientificPanel());
tabs.addTab("Graph", new GraphPanel());


getContentPane().add(tabs, BorderLayout.CENTER);
}
}