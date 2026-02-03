package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.SequencedMap;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import jtamaro.data.Option;
import jtamaro.data.Options;

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

  private final Rectangle2D bbox;

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
    this.bbox = new Rectangle2D.Double();
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
    this.bbox = path.getBounds2D();
  }

  /* **** Location **** */

  /**
   * Get pinning point of the graphic.
   *
   * @see Point
   */
  public Point getPin() {
    return pinPoint;
  }

  /**
   * Get the (tight) bounding box of this graphic.
   */
  Rectangle2D getBBox() {
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

  /**
   * Get the relative location for the given point with respect to this graphic.
   *
   * @implNote The location is computed with respect to the origin point of this graphic.
   */
  RelativeLocation getLocation(Point point) {
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
    return new RelativeLocation(
        this,
        xForPointX0 + point.getX() * dxForPointX1,
        yForPointY0 + point.getY() * dyForPointY1
    );
  }

  /**
   * Get the coordinate x of the given location, if it belongs to this graphic, or NaN.
   */
  double xForLocation(RelativeLocation location) {
    return location.isOfGraphic(this)
        ? location.x()
        : Double.NaN;
  }

  /**
   * Get the coordinate y of the given location, if it belongs to this graphic, or NaN.
   */
  double yForLocation(RelativeLocation location) {
    return location.isOfGraphic(this)
        ? location.y()
        : Double.NaN;
  }

  /**
   * Return the location coordinates relative to the (child) graphic corresponding to the given
   * absolute coordinates.
   *
   * @apiNote The two coordinates must be given with respect to the origin point of the graphic.
   */
  Option<RelativeLocation> relativeLocationOf(double x, double y) {
    return path.contains(x, y)
        ? Options.some(new RelativeLocation(this, x, y))
        : Options.none();
  }

  /* **** Rendering **** */

  /**
   * Render this Graphic into the given {@link Graphics2D} graphics context, using the given
   * {@link RenderOptions}.
   *
   * <p>The context can represent a GUI component (used when visualizing the graphic), or a bitmap
   * (used when writing the graphic into a bitmap file).
   */
  protected abstract void render(Graphics2D g2d, RenderOptions options);

  /**
   * Draw debug information for this graphic on the given {@link Graphics2D} graphics context.
   */
  final void drawDebugInfo(Graphics2D g2d) {
    GraphicsDebugInfo.render(g2d, getPath(), getBBox());
  }

  /**
   * Get the path of this graphic.
   */
  Path2D.Double getPath() {
    return path;
  }

  /**
   * Get the width of this graphic.
   *
   * @implNote Computed using the tight bounding box.
   */
  public double getWidth() {
    return bbox.getWidth();
  }

  /**
   * Get the height of this graphic.
   *
   * @implNote Computed using the tight bounding box.
   */
  public double getHeight() {
    return bbox.getHeight();
  }

  /**
   * Return an <b>ordered</b> map that contains children (and their "names" as key) of this
   * graphic.
   */
  SequencedMap<String, Graphic> getChildren() {
    return new LinkedHashMap<>();
  }

  /* **** Info **** */

  /**
   * Creates a string visualization of this graphic and its components.
   */
  public final String dump() {
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
   * @implSpec Remember to invoke {@code super.dump(sb, indent)} first, then add all children using
   * {@link #dumpChild(StringBuilder, String, String, Graphic)}.
   * @implNote All the props from {@link #getProps(PropStyle)} are automatically added by invoking
   * the super method.
   */
  protected final void dump(StringBuilder sb, String indent) {
    sb.append("\033[1m")
        .append(getInspectLabel())
        .append("\033[0m")
        .append("\n");
    getProps(PropStyle.ANSI_ESCAPE_CODES).forEach((k, v) -> dumpField(sb, indent, k, v));
    getChildren().forEach((k, v) -> dumpChild(sb, indent, k, v));
  }

  private void dumpField(StringBuilder sb, String indent, String name, Object value) {
    sb.append(indent)
        .append("├─ \033[2m")
        .append(name)
        .append("\033[0m: ")
        .append(value)
        .append("\n");
  }

  private void dumpChild(StringBuilder sb, String indent, String name, Graphic child) {
    sb.append(indent)
        .append("├─ \033[2m")
        .append(name)
        .append("\033[0m: ");
    child.dump(sb, indent + "│  ");
  }

  /**
   * Create a node that can be used to inspect this graphic in a GUI by representing it with a
   * JTree.
   *
   * @apiNote Internal usage only.
   * @hidden
   */
  public final MutableTreeNode createInspectTree() {
    return new InspectTreeNode(getChildren().values());
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
   * <p>Same as {@link #getProps(PropStyle)}, with {@link PropStyle#PLAIN} as the style.
   *
   * @implSpec The returned map is supposed to be mutable in non final-classes that override this
   * method so that it's easier for subclasses to add more entries to it.
   * @see #getProps(PropStyle)
   */
  protected SequencedMap<String, String> getProps() {
    return getProps(PropStyle.PLAIN);
  }

  /**
   * Properties of the graphic.
   *
   * <p>These are used to produce various representations of the graphic.
   *
   * @param propStyle Whether the value strings be in plain text, formatted in HTML or ANSI escape
   *                  codes markup.
   * @implSpec The returned map is supposed to be mutable in non final-classes that override this
   * method so that it's easier for subclasses to add more entries to it.
   * @see #dump(StringBuilder, String)
   * @see #toString()
   * @see GuiGraphicPropertiesPanel
   * @see GuiGraphicTreeCellRenderer
   */
  protected SequencedMap<String, String> getProps(PropStyle propStyle) {
    // We use LinkedHashMap as the implementation class because:
    //   1. We care about insertion order
    //   2. We never perform random reads on the map, just forEach iterations
    final SequencedMap<String, String> props = new LinkedHashMap<>();
    props.put("width", String.format("%.2f", getWidth()));
    props.put("height", String.format("%.2f", getHeight()));
    return props;
  }

  /**
   * Check whether this graphic is "structurally equal" to another one.
   *
   * <p>This is a less-strict version of {@link Graphic#equals(Object)} that uses additional
   * heuristics to allow some graphics that <i>look</i> identical be reported as <i>equal</i> even
   * if they do not have the exact same structure.
   *
   * @implNote Right now, the implementation is not formally sound, nor complete, but it is good
   * enough for the current use-case. In the future we may want to devise a fully-specified algebra
   * of graphics with a complete set of equality laws.
   * @hidden
   */
  public abstract boolean structurallyEqualTo(Graphic other);

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
        + getProps(PropStyle.PLAIN).entrySet().stream()
        .map(e -> e.getKey() + "=" + e.getValue())
        .collect(Collectors.joining(", "))
        + "]";
  }

  /* **** Internal util classes **** */

  /**
   * @hidden
   */
  final class InspectTreeNode extends DefaultMutableTreeNode {

    public InspectTreeNode(Iterable<Graphic> children) {
      super();
      for (Graphic child : children) {
        add(child.createInspectTree());
      }
    }

    public Graphic getGraphic() {
      return Graphic.this;
    }
  }

  /**
   * @hidden
   */
  protected enum PropStyle {
    PLAIN,
    ANSI_ESCAPE_CODES,
    HTML,
  }
}
