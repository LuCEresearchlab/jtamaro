package jtamaro.internal.representation;

import jtamaro.internal.gui.GraphicTreeNode;
import jtamaro.internal.gui.RenderOptions;

import java.awt.*;
import java.awt.geom.Path2D;


public abstract class GraphicImpl {

  private static final Color OUTLINE_COLOR = new Color(200, 0, 100);
  //private static final Color BASELINE_COLOR = new Color(0, 200, 100);
  private static final Color BOUNDING_BOX_COLOR = new Color(0, 100, 200);
  private static final Color HOLE_COLOR = new Color(0, 0, 0);
  private static final int POINT_INNER_RADIUS = 5;
  private static final int POINT_OUTER_RADIUS = 7;
  private static final int POINT_SHADOW_RADIUS = 9;
  private static final int CROSS_INNER_LINE_WIDTH = 2;
  private static final int CROSS_OUTER_LINE_WIDTH = 6;
  private static final int CROSS_SHADOW_LINE_WIDTH = 10;
  private static final int CROSS_INNER_SIZE = 20;
  private static final int CROSS_OUTER_SIZE = 24;
  private static final int CROSS_SHADOW_SIZE = 28;


  private Path2D.Double path;
  private TightBoundingBox bbox;
  private final Location pin;
  //private final HashMap<Place,Location> locations;
  //private double baseY; // Y-coordinate of the graphic's baseline (especially for text)


  protected GraphicImpl() {
    //locations = new HashMap<>();
    //addLocation(Place.PIN, 0, 0);
    pin = new Location(this, 0, 0);
  }

  // new Point API
  public Location getLocation(PointImpl point) {
    // point.x == -1 corresponds to bbox.getMinX()
    // point.x == 0 corresponds to (bbox.getMinX() + bbox.getMaxX()) / 2
    // point.x == 1 corresponds to bbox.getMaxX()
    final double xForPointX0 = (bbox.getMinX() + bbox.getMaxX()) / 2; // corresponding to point.x == 0
    final double yForPointY0 = (bbox.getMinY() + bbox.getMaxY()) / 2; // corresponding to point.y == 0
    final double dxForPointX1 = bbox.getWidth() / 2;
    final double dyForPointY1 = bbox.getHeight() / 2;
    final double x = xForPointX0 + point.getX() * dxForPointX1;
    final double y = yForPointY0 + point.getY() * dyForPointY1;
    return new Location(this, x, y);
  }

  // noteable locations (points on bounding box) for all graphics
  public Location getPin() {
    return pin;
  }

  public Location getTopLeft() {
    return getLocation(PointImpl.TOP_LEFT);
  }

  public Location getTopMiddle() {
    return getLocation(PointImpl.TOP_MIDDLE);
  }

  public Location getTopRight() {
    return getLocation(PointImpl.TOP_RIGHT);
  }

  public Location getMiddleLeft() {
    return getLocation(PointImpl.MIDDLE_LEFT);
  }

  public Location getMiddle() {
    return getLocation(PointImpl.MIDDLE);
  }

  public Location getMiddleRight() {
    return getLocation(PointImpl.MIDDLE_RIGHT);
  }

  public Location getBottomLeft() {
    return getLocation(PointImpl.BOTTOM_LEFT);
  }

  public Location getBottomMiddle() {
    return getLocation(PointImpl.BOTTOM_MIDDLE);
  }

  public Location getBottomRight() {
    return getLocation(PointImpl.BOTTOM_RIGHT);
  }

  /**
   * Set the path used for bounding box computation.
   * This has to correspond to the complete outline of the graphic.
   * It is NOT used for rendering, though.
   * This method has to be called in subclass constructors
   * before calling addPoint / addBoundingBoxPoint.
   */
  protected final void setPath(Path2D.Double path) {
    this.path = path;
    bbox = new TightBoundingBox(path);
  }

  //protected final void setBaseY(final double baseY) {
  //  this.baseY = baseY;
  //}

  /**
   * protected final void addLocation(final PointImpl point, final double x, final double y) {
   * locations.put(point, new Location(this, point, x, y));
   * }
   * <p>
   * protected final void addBoundingBoxLocations() {
   * this.addLocation(Place.TL, bbox.getMinX(), bbox.getMinY());
   * this.addLocation(Place.ML, bbox.getMinX(), (bbox.getMinY()+bbox.getMaxY())/2);
   * this.addLocation(Place.BL, bbox.getMinX(), bbox.getMaxY());
   * this.addLocation(Place.TM, (bbox.getMinX()+bbox.getMaxX())/2, bbox.getMinY());
   * this.addLocation(Place.MM, (bbox.getMinX()+bbox.getMaxX())/2, (bbox.getMinY()+bbox.getMaxY())/2);
   * this.addLocation(Place.BM, (bbox.getMinX()+bbox.getMaxX())/2, bbox.getMaxY());
   * this.addLocation(Place.TR, bbox.getMaxX(), bbox.getMinY());
   * this.addLocation(Place.MR, bbox.getMaxX(), (bbox.getMinY()+bbox.getMaxY())/2);
   * this.addLocation(Place.BR, bbox.getMaxX(), bbox.getMaxY());
   * }
   */

  public TightBoundingBox getBBox() {
    return bbox;
  }

  /*
  public Location getLocation(final Place place) {
    final Location point = locations.get(place);
    if (point != null) {
      return point;
    } else {
      throw new IllegalArgumentException("getLocation: Place "+place+" does not exist in graphic "+this);
    }
  }
  */

  protected double xForLocation(final Location location) {
    return location.getGraphic() == this ? location.getX() : Double.NaN;
  }

  protected double yForLocation(final Location location) {
    return location.getGraphic() == this ? location.getY() : Double.NaN;
  }

  public void renderOutline(Graphics2D g2) {
    g2.setColor(OUTLINE_COLOR);
    g2.draw(getPath());
  }

  /*
  public void renderBaseline(Graphics2D g2) {
    final TightBoundingBox bbox = getBBox();
    g2.setColor(BASELINE_COLOR);
    g2.drawLine((int)bbox.getMinX(), (int)getBaseY(), (int)bbox.getMaxX(), (int)getBaseY());
  }
  */

  public void renderBounds(Graphics2D g2) {
    final TightBoundingBox bbox = getBBox();
    g2.setColor(BOUNDING_BOX_COLOR);
    g2.drawRect((int) bbox.getMinX(), (int) bbox.getMinY(), (int) getWidth(), (int) getHeight());
  }

  public void renderPoint(Graphics2D g2, double x, double y, Color color) {
    g2.setColor(new Color(0, 0, 0, 16));
    g2.fillOval((int) x - POINT_SHADOW_RADIUS, (int) y - POINT_SHADOW_RADIUS, 2 * POINT_SHADOW_RADIUS, 2 * POINT_SHADOW_RADIUS);
    g2.setColor(Color.WHITE);
    g2.fillOval((int) x - POINT_OUTER_RADIUS, (int) y - POINT_OUTER_RADIUS, 2 * POINT_OUTER_RADIUS, 2 * POINT_OUTER_RADIUS);
    g2.setColor(color);
    g2.fillOval((int) x - POINT_INNER_RADIUS, (int) y - POINT_INNER_RADIUS, 2 * POINT_INNER_RADIUS, 2 * POINT_INNER_RADIUS);
  }

  public void renderPin(Graphics2D g2, double x, double y, Color color) {
    g2.setColor(new Color(0, 0, 0, 16));
    g2.fillRoundRect((int) x - CROSS_SHADOW_LINE_WIDTH / 2, (int) y - CROSS_SHADOW_SIZE / 2, CROSS_SHADOW_LINE_WIDTH, CROSS_SHADOW_SIZE, CROSS_SHADOW_LINE_WIDTH, CROSS_SHADOW_LINE_WIDTH);
    g2.fillRoundRect((int) x - CROSS_SHADOW_SIZE / 2, (int) y - CROSS_SHADOW_LINE_WIDTH / 2, CROSS_SHADOW_SIZE, CROSS_SHADOW_LINE_WIDTH, CROSS_SHADOW_LINE_WIDTH, CROSS_SHADOW_LINE_WIDTH);
    g2.fillOval((int) x - POINT_SHADOW_RADIUS, (int) y - POINT_SHADOW_RADIUS, 2 * POINT_SHADOW_RADIUS, 2 * POINT_SHADOW_RADIUS);
    g2.setColor(Color.WHITE);
    g2.fillRoundRect((int) x - CROSS_OUTER_LINE_WIDTH / 2, (int) y - CROSS_OUTER_SIZE / 2, CROSS_OUTER_LINE_WIDTH, CROSS_OUTER_SIZE, CROSS_OUTER_LINE_WIDTH, CROSS_OUTER_LINE_WIDTH);
    g2.fillRoundRect((int) x - CROSS_OUTER_SIZE / 2, (int) y - CROSS_OUTER_LINE_WIDTH / 2, CROSS_OUTER_SIZE, CROSS_OUTER_LINE_WIDTH, CROSS_OUTER_LINE_WIDTH, CROSS_OUTER_LINE_WIDTH);
    g2.fillOval((int) x - POINT_OUTER_RADIUS, (int) y - POINT_OUTER_RADIUS, 2 * POINT_OUTER_RADIUS, 2 * POINT_OUTER_RADIUS);
    g2.setColor(color);
    g2.fillRoundRect((int) x - CROSS_INNER_LINE_WIDTH / 2, (int) y - CROSS_INNER_SIZE / 2, CROSS_INNER_LINE_WIDTH, CROSS_INNER_SIZE, CROSS_INNER_LINE_WIDTH, CROSS_INNER_LINE_WIDTH);
    g2.fillRoundRect((int) x - CROSS_INNER_SIZE / 2, (int) y - CROSS_INNER_LINE_WIDTH / 2, CROSS_INNER_SIZE, CROSS_INNER_LINE_WIDTH, CROSS_INNER_LINE_WIDTH, CROSS_INNER_LINE_WIDTH);
    g2.fillOval((int) x - POINT_INNER_RADIUS, (int) y - POINT_INNER_RADIUS, 2 * POINT_INNER_RADIUS, 2 * POINT_INNER_RADIUS);
  }

  public void renderHole(Graphics2D g2) {
    // hole is always at 0, 0
    //this.renderPoint(g2, 0, 0, HOLE_COLOR);
    this.renderPin(g2, 0, 0, HOLE_COLOR);
  }

  public void renderBoundingBoxPoints(Graphics2D g2) {
    final TightBoundingBox bbox = getBBox();
    final double tY = bbox.getMinY();
    final double bY = bbox.getMaxY();
    final double mY = (bY + tY) / 2;
    final double lX = bbox.getMinX();
    final double rX = bbox.getMaxX();
    final double mX = (rX + lX) / 2;
    this.renderPoint(g2, lX, tY, BOUNDING_BOX_COLOR);
    this.renderPoint(g2, mX, tY, BOUNDING_BOX_COLOR);
    this.renderPoint(g2, rX, tY, BOUNDING_BOX_COLOR);
    this.renderPoint(g2, lX, mY, BOUNDING_BOX_COLOR);
    this.renderPoint(g2, mX, mY, BOUNDING_BOX_COLOR);
    this.renderPoint(g2, rX, mY, BOUNDING_BOX_COLOR);
    this.renderPoint(g2, lX, bY, BOUNDING_BOX_COLOR);
    this.renderPoint(g2, mX, bY, BOUNDING_BOX_COLOR);
    this.renderPoint(g2, rX, bY, BOUNDING_BOX_COLOR);
  }

  public void drawDebugInfo(Graphics2D g2, RenderOptions o) {
    if (o.isSelected(this)) {
      renderOutline(g2);
      //renderBaseline(g2);
      renderBounds(g2);
      renderBoundingBoxPoints(g2);
      renderHole(g2);
    }
  }


  /**
   * Produce textual representation of this Graphic.
   * This uses indentation to show the tree structure of compound graphics.
   *
   * @param sb     The StringBuilder onto which to append the textual representation
   * @param indent The indent (e.g., one or two spaces) to use for each level of the tree
   */
  public void dump(StringBuilder sb, String indent) {
    sb.append(indent).append(getClass().getSimpleName()).append("\n");
    appendField(sb, indent, "width", "" + getWidth());
    appendField(sb, indent, "height", "" + getHeight());
    //appendField(sb, indent, "baseY", ""+getBaseY());
  }

  protected final void appendField(StringBuilder sb, String indent, String name, String value) {
    sb.append(indent).append("- ").append(name).append(": ").append(value).append("\n");
  }

  protected final void appendChild(StringBuilder sb, String indent, String name, GraphicImpl child) {
    sb.append(indent).append("- ").append(name).append(":\n");
    child.dump(sb, indent + "  ");
  }

  /**
   * Create a node that can be used to represent this graphic
   * in a GUI with a JTree.
   */
  public GraphicTreeNode createTree() {
    return new GraphicTreeNode(this);
  }

  /**
   * Get the width of the bounding box.
   */
  public double getWidth() {
    return bbox.getWidth();
  }

  /**
   * Get the height of the bounding box.
   */
  public double getHeight() {
    return bbox.getHeight();
  }

  /**
   * Get the location of the baseline (from pinhole, i.e., from y = 0).
   */
  //public double getBaseY() {
  //  return baseY;
  //}
  public Path2D.Double getPath() {
    return path;
  }

  /**
   * Render this Graphic into the given Graphics2D graphics context,
   * using the given RenderingOptions.
   * <p>
   * The context can represent a GUI component (used when visualizing the graphic),
   * or a bitmap (used when writing the graphic into a bitmap file).
   */
  public abstract void render(Graphics2D g2, RenderOptions o);

}
