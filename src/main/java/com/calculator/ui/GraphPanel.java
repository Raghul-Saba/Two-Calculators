package com.calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Path2D;
import com.calculator.core.ExpressionEvaluator;

public class GraphPanel extends JPanel {
    private final ExpressionEvaluator evaluator = new ExpressionEvaluator();
    private final JTextField exprField = new JTextField("sin(x)");
    private final PlotPanel plotPanel = new PlotPanel();

public GraphPanel() {
    setLayout(new BorderLayout(6, 6));

    // Simple function input panel
    JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    functionPanel.add(new JLabel("f(x) = "));
    exprField.setColumns(25);
    exprField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    functionPanel.add(exprField);
    
    JButton plotBtn = new JButton("Plot Graph");
    plotBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
    plotBtn.setBackground(new Color(70, 130, 180));
    plotBtn.setForeground(Color.WHITE);
    plotBtn.addActionListener(this::onPlot);
    functionPanel.add(plotBtn);
    
    add(functionPanel, BorderLayout.NORTH);
    add(plotPanel, BorderLayout.CENTER);
}

private void onPlot(ActionEvent ev) {
    try {
        // Use a good default range for most functions
        double xmin = -10;
        double xmax = 10;
        int samples = 1000;
        plotPanel.updatePlot(exprField.getText(), xmin, xmax, samples);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private class PlotPanel extends JPanel {
        private String expr = "sin(x)";
        private double xmin = -10, xmax = 10;
        private int samples = 500;
        private double[] xs, ys;

        public PlotPanel() {
            setPreferredSize(new Dimension(600, 400));
            setBackground(Color.WHITE);
        }

        public void updatePlot(String expr, double xmin, double xmax, int samples) {
            this.expr = expr;
            this.xmin = xmin;
            this.xmax = xmax;
            this.samples = Math.max(10, samples);
            computePoints();
            repaint();
        }

        private void computePoints() {
            xs = new double[samples];
            ys = new double[samples];
            double dx = (xmax - xmin) / (samples - 1);
            
            for (int i = 0; i < samples; i++) {
                xs[i] = xmin + i * dx;
                try {
                    ys[i] = evaluator.eval(expr, xs[i]);
                } catch (Exception e) {
                    ys[i] = Double.NaN;
                    // Debug: print first few errors
                    if (i < 5) {
                        System.out.println("Error at x=" + xs[i] + ": " + e.getMessage());
                    }
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            
            // Clear background
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            
            // Draw axes
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(0, height / 2, width, height / 2); // x-axis
            g2d.drawLine(width / 2, 0, width / 2, height); // y-axis

            // Draw measurement markings
            drawMarkings(g2d, width, height);

            if (xs == null || ys == null) return;

            // Find min/max y values for scaling
            double ymin = Double.MAX_VALUE, ymax = Double.MIN_VALUE;
            for (double y : ys) {
                if (!Double.isNaN(y)) {
                    ymin = Math.min(ymin, y);
                    ymax = Math.max(ymax, y);
                }
            }
            if (ymin == Double.MAX_VALUE) return;

            // Add some padding to y range for better display
            double yrange = ymax - ymin;
            if (yrange == 0) {
                ymin -= 1;
                ymax += 1;
                yrange = 2;
            } else {
                double padding = yrange * 0.1;
                ymin -= padding;
                ymax += padding;
                yrange = ymax - ymin;
            }
            
            double xscale = (double) width / (xmax - xmin);
            double yscale = (double) height / yrange;

            // Draw the function
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(3));
            
            Path2D path = new Path2D.Double();
            boolean first = true;
            
            for (int i = 0; i < xs.length; i++) {
                if (!Double.isNaN(ys[i])) {
                    int x = (int) ((xs[i] - xmin) * xscale);
                    int y = (int) (height - (ys[i] - ymin) * yscale);
                    
                    if (first) {
                        path.moveTo(x, y);
                        first = false;
                    } else {
                        path.lineTo(x, y);
                    }
                }
            }
            g2d.draw(path);
        }
        
        private void drawMarkings(Graphics2D g2d, int width, int height) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            
            // X-axis markings
            double xscale = (double) width / (xmax - xmin);
            int centerY = height / 2;
            
            // Draw π markings
            double[] piValues = {-2*Math.PI, -Math.PI, -Math.PI/2, 0, Math.PI/2, Math.PI, 2*Math.PI};
            String[] piLabels = {"-2π", "-π", "-π/2", "0", "π/2", "π", "2π"};
            
            for (int i = 0; i < piValues.length; i++) {
                if (piValues[i] >= xmin && piValues[i] <= xmax) {
                    int x = (int) ((piValues[i] - xmin) * xscale);
                    g2d.drawLine(x, centerY - 5, x, centerY + 5);
                    g2d.drawString(piLabels[i], x - 10, centerY + 20);
                }
            }
            
            // Y-axis markings
            double yscale = (double) height / (xmax - xmin); // Use same scale for simplicity
            int centerX = width / 2;
            
            // Draw y markings
            double[] yValues = {-2*Math.PI, -Math.PI, -Math.PI/2, 0, Math.PI/2, Math.PI, 2*Math.PI};
            String[] yLabels = {"-2π", "-π", "-π/2", "0", "π/2", "π", "2π"};
            
            for (int i = 0; i < yValues.length; i++) {
                int y = (int) (centerY - (yValues[i] - 0) * yscale);
                if (y >= 0 && y <= height) {
                    g2d.drawLine(centerX - 5, y, centerX + 5, y);
                    g2d.drawString(yLabels[i], centerX + 10, y + 5);
                }
            }
        }
    }
}