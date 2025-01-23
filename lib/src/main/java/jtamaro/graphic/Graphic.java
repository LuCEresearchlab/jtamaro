package jtamaro.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * A graphic (image) with a position for pinning.
 */
public abstract sealed class Graphic
    permits CircularSector,
    Compose,
    DelegatingGraphic,
    Ellipse,
    EmptyGraphic,
    Pin,
    Rectangle,
    Rotate,
    Text,
    Triangle {

  private final Point pinPoint;

  private final Path2D.Double path;

  private final TightBoundingBox bbox;

  /**
   * Produces a graphic with an empty path.
   *
   * <p>To be used only by {@link DelegatingGraphic} and
   * {@link EmptyGraphic}.
   *
   * @hidden
   */
  /* package */ Graphic() {
    this.pinPoint = Points.CENTER;
    this.path = new Path2D.Double();
    this.path.moveTo(0, 0);
    this.bbox = new TightBoundingBox(this.path);
  }

  /**
   * Graphic of the given path pinned in the center.
   */
  protected Graphic(Path2D.Double path) {
    this(path, Points.CENTER);
  }

  /**
   * Default public constructor.
   */
  protected Graphic(Path2D.Double path, Point pinPoint) {
    this.path = path;
    this.pinPoint = pinPoint;
    this.bbox = new TightBoundingBox(path);
  }

  /* **** Location **** */

  public Point getPin() {
    return pinPoint;
  }

  TightBoundingBox getBBox() {
    return bbox;
  }

  /**
   * Get the min X of the bounding box.
   *
   * @apiNote Only used for rendering purposes.
   * @hidden
   */
  public final double getBoundsMinX() {
    return getBBox().getMinX();
  }

  /**
   * Get the min Y of the bounding box.
   *
   * @apiNote Only used for rendering purposes.
   * @hidden
   */
  public final double getBoundsMinY() {
    return getBBox().getMinY();
  }

  Location getLocation(Point point) {
    /*
     * switch (point.x) {
     *   case -1 -> bbox.getMinX();
     *   case  0 -> (bbox.getMinX() + bbox.getMaxX()) / 2;
     *   case  1 -> bbox.getMaxX();
     * }
     */
    final double xForPointX0 = (bbox.getMinX() + bbox.getMaxX()) / 2.0;
    final double yForPointY0 = (bbox.getMinY() + bbox.getMaxY()) / 2.0;
    final double dxForPointX1 = bbox.getWidth() / 2.0;
    final double dyForPointY1 = bbox.getHeight() / 2.0;
    return new Location(
        xForPointX0 + point.getX() * dxForPointX1,
        yForPointY0 + point.getY() * dyForPointY1
    );
  }

  /**
   * Get the coordinate x of the given location, if it belongs to this graphic, or NaN.
   */
  double xForLocation(Location location) {
    return location.isOfGraphic(this)
        ? location.x
        : Double.NaN;
  }

  /**
   * Get the coordinate y of the given location, if it belongs to this graphic, or NaN.
   */
  double yForLocation(Location location) {
    return location.isOfGraphic(this)
        ? location.y
        : Double.NaN;
  }

  /* **** Rendering **** */

  /**
   * Render this Graphic into the given Graphics2D graphics context, using the given RenderOptions.
   *
   * <p>The context can represent a GUI component (used when visualizing the graphic), or a bitmap
   * (used when writing the graphic into a bitmap file).
   */
  protected abstract void render(Graphics2D g2d, RenderOptions options);

  Path2D.Double getPath() {
    return path;
  }

  public double getWidth() {
    return bbox.getWidth();
  }

  public double getHeight() {
    return bbox.getHeight();
  }

  public static Color renderableColor(jtamaro.graphic.Color color) {
    return new Color(color.red(), color.green(), color.blue(), color.alpha());
  }

  /* **** Info **** */

  /**
   * Creates a string visualization of this graphic and its components.
   */
  public String dump() {
    final StringBuilder sb = new StringBuilder();
    dump(sb, "");
    return sb.toString();
  }

  /**
   * Dump method used by {@link #toString()} to provide a string representation of the graphic.
   *
   * <p>The output resembles a tree where the leaves are graphics, their attributes and children
   * graphics.
   *
   * @implSpec Remember to invoke <code>super.dump(sb, indent)</code> first, then add all children
   * using {@link #dumpChild(StringBuilder, String, String, Graphic)}.
   * @implNote All the props from {@link #getProps(boolean)} are automatically added by invoking the
   * super method.
   */
  protected void dump(StringBuilder sb, String indent) {
    sb.append(indent)
        .append(getClass().getSimpleName())
        .append("\n");
    getProps(true).forEach((k, v) -> dumpField(sb, indent, k, v));
  }

  protected final void dumpField(StringBuilder sb, String indent, String name, Object value) {
    sb.append(indent)
        .append("├─ ")
        .append(name)
        .append(": ")
        .append(value)
        .append("\n");
  }

  protected final void dumpChild(StringBuilder sb, String indent, String name, Graphic child) {
    sb.append(indent)
        .append("├─ ")
        .append(name)
        .append(":\n");
    child.dump(sb, indent + "│  ");
  }

  /**
   * Create a node that can be used to inspect this graphic in a GUI by representing it with a
   * JTree.
   */
  public MutableTreeNode createInspectTree() {
    return new InspectTreeNode();
  }

  /**
   * Label used to represent this component in the inspector / show UI.
   */
  protected String getInspectLabel() {
    return "graphic";
  }

  /**
   * Properties of the graphic.
   *
   * <p>These are used to produce various representations of the graphic.
   *
   * @param plainText Whether the value strings be in plain text (<code>true</code>) or formatted in
   *                  HTML markup (<code>false</code>).
   * @implSpec The returned map is supposed to be mutable in non final-classes that override this
   * method so that it's easier for subclasses to add more entries to it.
   * @see #dump(StringBuilder, String)
   * @see #toString()
   * @see GuiGraphicPropertiesPanel
   * @see GuiGraphicTreeCellRenderer
   */
  protected Map<String, String> getProps(boolean plainText) {
    // We use LinkedHashMap as the implementation class because:
    //   1. We care about insertion order
    //   2. We never perform random reads on the map, just forEach iterations
    final Map<String, String> props = new LinkedHashMap<>();
    props.put("width", String.format("%.2f", getWidth()));
    props.put("height", String.format("%.2f", getHeight()));
    props.put("pinPoint", Points.format(pinPoint));
    return props;
  }

  void drawDebugInfo(Graphics2D g2d, RenderOptions options) {
    if (options.isSelected(this)) {
      GraphicsDebugInfo.render(g2d, getPath(), getBBox());
    }
  }

  /* **** Object **** */

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Graphic that
        && Objects.equals(pinPoint, that.pinPoint)
        && Objects.equals(getPath(), that.getPath()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(pinPoint, getPath());
  }

  @Override
  public final String toString() {
    return getClass().getSimpleName()
        + "["
        + getProps(true).entrySet().stream()
        .map(e -> e.getKey() + "=" + e.getValue())
        .collect(Collectors.joining(", "))
        + "]";
  }

  /* **** Internal util classes **** */

  final class InspectTreeNode extends DefaultMutableTreeNode {

    public InspectTreeNode() {
    }

    public InspectTreeNode(Graphic... children) {
      for (Graphic child : children) {
        add(child.createInspectTree());
      }
    }

    public Graphic getGraphic() {
      return Graphic.this;
    }
  }

  final class Location {

    public final double x;

    public final double y;

    public Location(double x, double y) {
      this.x = x;
      this.y = y;
    }

    public boolean isOfGraphic(Graphic graphic) {
      return graphic == Graphic.this;
    }

    @Override
    public boolean equals(Object other) {
      return this == other
          || (other instanceof Location that
          && Double.compare(x, that.x) == 0
          && Double.compare(y, that.y) == 0);
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    @Override
    public String toString() {
      return String.format("Graphic.Location[x=%1$.2f, y=%2$.2f]", x, y);
    }
  }
}
