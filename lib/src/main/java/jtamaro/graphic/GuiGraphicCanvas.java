package jtamaro.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

/**
 * Swing canvas component that renders a graphic.
 *
 * @apiNote Not for public use.
 * @hidden
 */
public final class GuiGraphicCanvas extends JComponent {

  private static final Color BRIGHT = new Color(250, 250, 250);

  private static final Color DARK = new Color(230, 230, 230);

  private final RenderOptions renderOptions;

  private Graphic graphic;

  public GuiGraphicCanvas(int width, int height) {
    this(new RenderOptions(0, width, height));
  }

  public GuiGraphicCanvas(RenderOptions renderOptions) {
    this.renderOptions = renderOptions;
    this.graphic = new EmptyGraphic();

    renderOptions.addRenderOptionsListener(this::repaint);
  }

  public void setGraphic(Graphic graphic) {
    this.graphic = graphic;
    revalidate();
    repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    final int padding = renderOptions.getPadding();
    if (renderOptions.hasFixedSize()) {
      return new Dimension(
          renderOptions.getFixedWidth() + 2 * padding,
          renderOptions.getFixedHeight() + 2 * padding
      );
    } else {
      return new Dimension(
          (int) graphic.getWidth() + 2 * padding,
          (int) graphic.getHeight() + 2 * padding
      );
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    final Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHints(new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON));
    final int padding = renderOptions.getPadding();
    paintBackground(g);
    g2.translate(padding, padding);
    g2.translate(-graphic.getBBox().getMinX(), -graphic.getBBox().getMinY());
    graphic.render(g2, renderOptions);
    graphic.drawDebugInfo(g2, renderOptions);
  }

  private void paintBackground(Graphics g) {
    final int width = getWidth();
    final int height = getHeight();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
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
