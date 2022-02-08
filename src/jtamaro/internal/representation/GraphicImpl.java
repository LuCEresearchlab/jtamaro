package jtamaro.internal.representation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.HashMap;

import jtamaro.internal.gui.GraphicTreeNode;
import jtamaro.internal.gui.RenderOptions;


public abstract class GraphicImpl {

  private static final Color BASELINE_COLOR = new Color(0, 200, 100);
  private static final Color BOUNDING_BOX_COLOR = new Color(0, 100, 200);
  private static final Color HOLE_COLOR = new Color(0, 0, 0);
  private static final int POINT_INNER_RADIUS = 5;
  private static final int POINT_OUTER_RADIUS = 7;
  
  private Path2D.Double path;
  private TightBoundingBox bbox;
  private final HashMap<Place,Point> points;
  private double baseY; // Y-coordinate of the graphic's baseline (especially for text)


  protected GraphicImpl() {
    points = new HashMap<>();
    addPoint(Place.PIN, 0, 0);
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

  protected final void setBaseY(final double baseY) {
    this.baseY = baseY;
  }

  protected final void addPoint(final Place place, final double x, final double y) {
    points.put(place, new Point(this, place, x, y));
  }

  protected final void addBoundingBoxPoints() {
    this.addPoint(Place.TL, bbox.getMinX(), bbox.getMinY());
    this.addPoint(Place.ML, bbox.getMinX(), (bbox.getMinY()+bbox.getMaxY())/2);
    this.addPoint(Place.BL, bbox.getMinX(), bbox.getMaxY());
    this.addPoint(Place.TM, (bbox.getMinX()+bbox.getMaxX())/2, bbox.getMinY());
    this.addPoint(Place.MM, (bbox.getMinX()+bbox.getMaxX())/2, (bbox.getMinY()+bbox.getMaxY())/2);
    this.addPoint(Place.BM, (bbox.getMinX()+bbox.getMaxX())/2, bbox.getMaxY());
    this.addPoint(Place.TR, bbox.getMaxX(), bbox.getMinY());
    this.addPoint(Place.MR, bbox.getMaxX(), (bbox.getMinY()+bbox.getMaxY())/2);
    this.addPoint(Place.BR, bbox.getMaxX(), bbox.getMaxY());
  }

  public TightBoundingBox getBBox() {
    return bbox;
  }

  public Point getPoint(final Place place) {
    final Point point = points.get(place);
    if (point != null) {
      return point;
    } else {
      throw new IllegalArgumentException("getPoint: Place "+place+" does not exist in graphic "+this);
    }
  }

  protected double xForPoint(final Point point) {
    return point.getGraphic() == this ? point.getX() : Double.NaN;
  }

  protected double yForPoint(final Point point) {
    return point.getGraphic() == this ? point.getY() : Double.NaN;
  }


  public void renderBaseline(Graphics2D g2) {
    g2.setColor(BASELINE_COLOR);
    g2.drawLine(0, (int)getBaseY(), (int)getWidth(), (int)getBaseY());
  }

  public void renderBounds(Graphics2D g2) {
    g2.setColor(BOUNDING_BOX_COLOR);
    g2.drawRect((int)bbox.getMinX(), (int)bbox.getMinY(), (int)getWidth(), (int)getHeight());
  }

  public void renderPoint(Graphics2D g2, double x, double y, Color color) {
    g2.setColor(Color.WHITE);
    g2.drawOval((int)x - POINT_OUTER_RADIUS, (int)y - POINT_OUTER_RADIUS, 2 * POINT_OUTER_RADIUS, 2 * POINT_OUTER_RADIUS);
    g2.setColor(color);
    g2.drawOval((int)x - POINT_INNER_RADIUS, (int)y - POINT_INNER_RADIUS, 2 * POINT_INNER_RADIUS, 2 * POINT_INNER_RADIUS);
  }

  public void renderHole(Graphics2D g2) {
    // hole is always at 0, 0
    this.renderPoint(g2, 0, 0, HOLE_COLOR);
  }

  public void renderBoundingBoxPoints(Graphics2D g2) {
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
      renderBaseline(g2);
      renderBounds(g2);
      renderHole(g2);
    }
  }


  /**
   * Produce textual representation of this Graphic.
   * This uses indentation to show the tree structure of compound graphics.
   * @param sb The StringBuilder onto which to append the textual representation
   * @param indent The indent (e.g., one or two spaces) to use for each level of the tree
   */
  public void dump(StringBuilder sb, String indent) {
    sb.append(indent + getClass().getSimpleName() + "\n");
    appendField(sb, indent, "width", ""+getWidth());
    appendField(sb, indent, "height", ""+getHeight());
    appendField(sb, indent, "baseY", ""+getBaseY());
  }

  protected final void appendField(StringBuilder sb, String indent, String name, String value) {
    sb.append(indent + "- " + name + ": " + value + "\n");
  }

  protected final void appendChild(StringBuilder sb, String indent, String name, GraphicImpl child) {
    sb.append(indent + "- " + name + ":\n");
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
  public double getBaseY() {
    return baseY;
  }

  public Path2D.Double getPath() {
    return path;
  }

  /**
   * Render this Graphic into the given Graphics2D graphics context,
   * using the given RenderingOptions.
   * 
   * The context can represent a GUI component (used when visualizing the graphic),
   * or a bitmap (used when writing the graphic into a bitmap file).
   */
  public abstract void render(Graphics2D g2, RenderOptions o);

}
