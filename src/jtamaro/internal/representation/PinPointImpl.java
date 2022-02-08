package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;

import jtamaro.internal.gui.GraphicTreeNode;
import jtamaro.internal.gui.RenderOptions;


public class PinPointImpl extends GraphicImpl implements CompositeImpl {

  private final Point point;
  private final GraphicImpl graphic;
  private final double dx;
  private final double dy;

  // pin
  public PinPointImpl(Place place, GraphicImpl graphic) {
    this(graphic.getPoint(place), graphic);
  }

  // pinPoint
  public PinPointImpl(Point point, GraphicImpl graphic) {
    this.graphic = graphic;
    this.point = point;
    this.dx = graphic.xForPoint(point);
    if (Double.isNaN(this.dx)) {
      throw new IllegalArgumentException("pinPoint: " + point + " does not exist in " + graphic + ".");
    }
    this.dy = graphic.yForPoint(point);
    if (Double.isNaN(this.dy)) {
      throw new IllegalArgumentException("pinPoint: " + point + " does not exist in " + graphic + ".");
    }
    final Path2D.Double path = new Path2D.Double(graphic.getPath());
    path.transform(AffineTransform.getTranslateInstance(-this.dx, -this.dy));
    setPath(path);
    this.addBoundingBoxPoints();
  }

  protected double xForPoint(final Point point) {
    if (point.getGraphic() == this) {
      return point.getX();
    } else {
      final double xInGraphic = graphic.xForPoint(point);
      return Double.isNaN(xInGraphic) ? Double.NaN : xInGraphic - this.dx;
    }
  }

  protected double yForPoint(final Point point) {
    if (point.getGraphic() == this) {
      return point.getY();
    } else {
      final double yInGraphic = graphic.yForPoint(point);
      return Double.isNaN(yInGraphic) ? Double.NaN : yInGraphic - this.dy;
    }
  }

  @Override
  public double getBaseline() {
    // set baseline of translated shape to
    // translated baseline
    return graphic.getBaseline() - dy;
  }

  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    AffineTransform baseTransform = g2.getTransform();

    g2.translate(-dx, -dy);
    graphic.render(g2, o);

    g2.setTransform(baseTransform);
  }

  @Override
  public void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    appendField(sb, indent, "point", ""+point);
    appendChild(sb, indent, "graphic", graphic);
  }

  @Override
  public GraphicTreeNode createTree() {
    GraphicTreeNode node = new GraphicTreeNode(this);
    GraphicTreeNode graphicNode = graphic.createTree();
    node.add(graphicNode);
    return node;
  }

}
