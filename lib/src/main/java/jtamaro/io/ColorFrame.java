package jtamaro.io;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

final class ColorFrame extends JFrame {

  public ColorFrame(Color color) {
    setTitle("Color");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    final JLabel rgbLabel = new JLabel("RGBA: "
        + color.red() + ", "
        + color.green() + ", "
        + color.blue() + ", "
        + color.alpha()
    );
    add(rgbLabel, BorderLayout.NORTH);
    final JComponent colorComponent = new JComponent() {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(200, 200);
      }

      @Override
      protected void paintComponent(Graphics g) {
        g.setColor(Graphic.renderableColor(color));
        g.fillRect(0, 0, getWidth(), getHeight());
      }
    };
    add(colorComponent, BorderLayout.CENTER);
    pack();
  }
}
