package jtamaro.internal.gui;

import javax.swing.JComponent;

import jtamaro.internal.representation.EmptyGraphicImpl;
import jtamaro.internal.representation.GraphicImpl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;


public class GraphicCanvas extends JComponent {

  private final RenderOptions renderOptions;
  private GraphicImpl graphic;


  public GraphicCanvas(final RenderOptions renderOptions) {
    this.renderOptions = renderOptions;
    graphic = new EmptyGraphicImpl();

    renderOptions.addRenderOptionsListener(new RenderOptionsListener() {
      @Override
      public void renderOptionsChanged() {
        repaint();        
      }
    });
  }
  
  public void setGraphic(GraphicImpl graphic) {
    this.graphic = graphic;
    revalidate();
    repaint();
  }

  public Dimension getPreferredSize() {
    final int padding = renderOptions.getPadding();
    return new Dimension((int)graphic.getWidth() + 2 * padding, (int)graphic.getHeight() + 2 * padding);
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    final int padding = renderOptions.getPadding();
    g2.translate(padding, padding);
    g2.translate(-graphic.getBBox().getMinX(), -graphic.getBBox().getMinY());
    paintBackground(g);
    graphic.render(g2, renderOptions);

    // debugging
    final BasicStroke dashed = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f);
    g2.setStroke(dashed);
    g2.setColor(new Color(200, 150, 0));
    g2.draw(graphic.getPath());
  }

  private void paintBackground(Graphics g) {
    final int width = (int)graphic.getWidth();
    final int height = (int)graphic.getHeight();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    Color BRIGHT = new Color(250, 250, 250);
    Color DARK = new Color(210, 210, 210);
    final int tileSize = 10;
    final int rows = height / tileSize;
    final int cols = width / tileSize;
    for (int col = 0; col <= cols; col++) {
      for (int row = 0; row <= rows; row++) {
        g.setColor((col + row) % 2 == 0 ? BRIGHT : DARK);
        int tileWidth = Math.min(width - col * tileSize, tileSize);
        int tileHeight = Math.min(height - row * tileSize, tileSize);
        g.fillRect(col * tileSize, row * tileSize, tileWidth, tileHeight);
      }
    }
  }

}
