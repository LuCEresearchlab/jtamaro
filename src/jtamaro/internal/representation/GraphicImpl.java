package jtamaro.internal.representation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.HashMap;

import jtamaro.internal.gui.GraphicTreeNode;
import jtamaro.internal.gui.RenderOptions;


public abstract class GraphicImpl {

  private static final Color DEBUG_COLOR = new Color(100, 120, 255);
  private static final Color BOUNDING_BOX_COLOR = new Color(0, 100, 200);
  private static final Color HOLE_COLOR = new Color(0, 0, 0);
  private static final double POINT_INNER_RADIUS = 5;
  private static final double POINT_OUTER_RADIUS = 7;
  
  private Path2D.Double path;
  private TightBoundingBox bbox;
  private final HashMap<Place,Point> points;
  

  protected GraphicImpl() {
    points = new HashMap<>();
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


  public void renderBoundingBox(Graphics2D ctx) {
    const bbox = this.path.bbox;
    ctx.beginPath();
    ctx.moveTo(bbox.minX, bbox.minY);
    ctx.lineTo(bbox.maxX, bbox.minY);
    ctx.lineTo(bbox.maxX, bbox.maxY);
    ctx.lineTo(bbox.minX, bbox.maxY);
    ctx.lineTo(bbox.minX, bbox.minY);
    ctx.strokeStyle = BOUNDING_BOX_COLOR;
    ctx.stroke();
  }

  public void renderPoint(Graphics2D ctx, double x, double y, Color color) {
    ctx.beginPath();
    ctx.ellipse(x, y, POINT_OUTER_RADIUS, POINT_OUTER_RADIUS, 0, 0, 2 * Math.PI);
    ctx.fillStyle = "white";
    ctx.fill();
    ctx.beginPath();
    ctx.ellipse(x, y, POINT_INNER_RADIUS, POINT_INNER_RADIUS, 0, 0, 2 * Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
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
      drawBaseline(g2);
      drawBounds(g2);
      drawPinhole(g2);
    }
  }

  public void drawBaseline(Graphics2D g2) {
    g2.setColor(DEBUG_COLOR);
    g2.drawLine(0, (int)getBaseline(), (int)getWidth(), (int)getBaseline());
  }

  public void drawBounds(Graphics2D g2) {
    g2.setColor(DEBUG_COLOR);
    g2.drawRect(0, 0, (int)getWidth(), (int)getHeight());
  }

  public void drawPinhole(Graphics2D g2) {
    g2.setColor(DEBUG_COLOR);
    final int size = 20;
    g2.drawLine(0, -size, 0, size);
    g2.drawLine(-size, 0, size, 0);
    final int circleRadius = 10;
    g2.drawOval(-circleRadius, -circleRadius, 2 * circleRadius, 2 * circleRadius);
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
    appendField(sb, indent, "baseline", ""+getBaseline());
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
  public final double getWidth() {
    return bbox.getWidth();
  }

  /**
   * Get the height of the bounding box.
   */
  public final double getHeight() {
    return bbox.getHeight();
  }

  /**
   * Get the location of the baseline (from pinhole, i.e., from y = 0).
   */
  public abstract double getBaseline();

  public final Path2D.Double getPath() {
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
