package jtamaro.en.io;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jtamaro.internal.representation.ColorImpl;

public class ColorFrame extends JFrame {
  
  public ColorFrame(ColorImpl color) {
    setTitle("Color");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    final JLabel rgbLabel = new JLabel(
      "RGBA: " + 
      color.toAWT().getRed() + ", " + 
      color.toAWT().getGreen() + ", " + 
      color.toAWT().getBlue() + ", " + 
      color.toAWT().getAlpha()
    );
    add(rgbLabel, BorderLayout.NORTH);
    JComponent colorComponent = new JComponent() {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(200, 200);
      }
      @Override
      protected void paintComponent(Graphics g) {
        g.setColor(color.toAWT());
        g.fillRect(0, 0, getWidth(), getHeight());
      }
    };
    add(colorComponent, BorderLayout.CENTER);
    pack();
    setVisible(true);
  }
}
