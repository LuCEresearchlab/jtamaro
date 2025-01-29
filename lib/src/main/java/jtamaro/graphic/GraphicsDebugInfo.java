package jtamaro.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Utility class that contains utilities to draw debug information about a given {@link Graphic}.
 *
 * @apiNote Not for public use.
 * @hidden
 */
final class GraphicsDebugInfo {

  private static final Color OUTLINE_COLOR = new Color(200, 0, 100);

  private static final Color BOUNDING_BOX_COLOR = new Color(0, 100, 200);

  private static final int POINT_INNER_RADIUS = 5;

  private static final int POINT_OUTER_RADIUS = 7;

  private static final int POINT_SHADOW_RADIUS = 9;

  private static final int CROSS_INNER_LINE_WIDTH = 2;

  private static final int CROSS_INNER_LINE_HEIGHT = 2;

  private static final int CROSS_OUTER_LINE_WIDTH = 6;

  private static final int CROSS_OUTER_LINE_HEIGHT = 6;

  private static final int CROSS_SHADOW_LINE_WIDTH = 10;

  private static final int CROSS_SHADOW_LINE_HEIGHT = 10;

  private static final int CROSS_INNER_SIZE = 20;

  private static final int CROSS_OUTER_SIZE = 24;

  private static final int CROSS_SHADOW_SIZE = 28;

  private GraphicsDebugInfo() {
  }

  /**
   * Render debug information such as pinning point position and bounding box for a {@link Graphic}
   * onto an awt {@link Graphics2D}.
   *
   * @param g2d  The awt graphic component on which the debug info will be drawn
   * @param path The path of the graphic of which we're rendering the debug information
   * @param bbox The bounding box of the graphic of which we're rendering the debug information
   */
  static void render(Graphics2D g2d, Path2D.Double path, Rectangle2D bbox) {
    renderOutline(g2d, path);
    renderBounds(g2d, bbox);
    renderBoundingBoxPoints(g2d, bbox);
    renderHole(g2d);
  }

  private static void renderOutline(Graphics2D g2d, Path2D.Double path) {
    g2d.setColor(OUTLINE_COLOR);
    g2d.draw(path);
  }

  /**
   * Render a rectangle of the bounding box of this graphic.
   */
  private static void renderBounds(Graphics2D g2d, Rectangle2D bbox) {
    g2d.setColor(BOUNDING_BOX_COLOR);
    g2d.drawRect(
        (int) bbox.getMinX(),
        (int) bbox.getMinY(),
        (int) bbox.getWidth(),
        (int) bbox.getHeight());
  }

  /**
   * Render the points of the bounding box of this graphic.
   */
  private static void renderBoundingBoxPoints(Graphics2D g2d, Rectangle2D bbox) {
    final double tY = bbox.getMinY();
    final double bY = bbox.getMaxY();
    final double mY = (bY + tY) / 2;
    final double lX = bbox.getMinX();
    final double rX = bbox.getMaxX();
    final double mX = (rX + lX) / 2;
    drawPoint(g2d, lX, tY);
    drawPoint(g2d, mX, tY);
    drawPoint(g2d, rX, tY);
    drawPoint(g2d, lX, mY);
    drawPoint(g2d, mX, mY);
    drawPoint(g2d, rX, mY);
    drawPoint(g2d, lX, bY);
    drawPoint(g2d, mX, bY);
    drawPoint(g2d, rX, bY);
  }

  private static void renderHole(Graphics2D g2d) {
    // The hole is always at 0, 0
    g2d.setColor(new Color(0, 0, 0, 16));
    drawCrossLine(g2d,
        CROSS_SHADOW_LINE_WIDTH,
        CROSS_SHADOW_SIZE,
        CROSS_SHADOW_LINE_HEIGHT,
        POINT_SHADOW_RADIUS);
    g2d.setColor(Color.WHITE);
    drawCrossLine(g2d,
        CROSS_OUTER_LINE_WIDTH,
        CROSS_OUTER_SIZE,
        CROSS_OUTER_LINE_HEIGHT,
        POINT_OUTER_RADIUS);
    g2d.setColor(Color.BLACK);
    drawCrossLine(g2d,
        CROSS_INNER_LINE_WIDTH,
        CROSS_INNER_SIZE,
        CROSS_INNER_LINE_HEIGHT,
        POINT_INNER_RADIUS);
  }

  private static void drawCrossLine(Graphics2D g2d,
      int crossLineWidth,
      int crossSize,
      int crossLineHeight,
      int dotRadius) {
    g2d.fillRoundRect(
        -crossLineWidth / 2,
        -crossSize / 2,
        crossLineWidth,
        crossSize,
        crossLineWidth,
        crossLineHeight
    );
    g2d.fillRoundRect(
        -crossSize / 2,
        -crossLineWidth / 2,
        crossSize,
        crossLineHeight,
        crossLineWidth,
        crossLineHeight
    );
    g2d.fillOval(
        -dotRadius,
        -dotRadius,
        2 * dotRadius,
        2 * dotRadius
    );
  }

  private static void drawPoint(Graphics2D g2d, double x, double y) {
    g2d.setColor(new Color(0, 0, 0, 16));
    g2d.fillOval(
        (int) x - POINT_SHADOW_RADIUS,
        (int) y - POINT_SHADOW_RADIUS,
        2 * POINT_SHADOW_RADIUS,
        2 * POINT_SHADOW_RADIUS
    );
    g2d.setColor(Color.WHITE);
    g2d.fillOval(
        (int) x - POINT_OUTER_RADIUS,
        (int) y - POINT_OUTER_RADIUS,
        2 * POINT_OUTER_RADIUS,
        2 * POINT_OUTER_RADIUS
    );
    g2d.setColor(BOUNDING_BOX_COLOR);
    g2d.fillOval(
        (int) x - POINT_INNER_RADIUS,
        (int) y - POINT_INNER_RADIUS,
        2 * POINT_INNER_RADIUS,
        2 * POINT_INNER_RADIUS
    );
  }
}
