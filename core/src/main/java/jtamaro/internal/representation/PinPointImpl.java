package jtamaro.internal.representation;

import jtamaro.internal.gui.GraphicTreeNode;
import jtamaro.internal.gui.RenderOptions;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;


public class PinPointImpl extends GraphicImpl implements CompositeImpl {

  private final Location location;
  private final GraphicImpl graphic;
  private final double dx;
  private final double dy;

  // pin
  public PinPointImpl(PointImpl point, GraphicImpl graphic) {
    this(graphic.getLocation(point), graphic);
  }

  // pinPoint
  public PinPointImpl(Location location, GraphicImpl graphic) {
    this.graphic = graphic;
    this.location = location;
    this.dx = graphic.xForLocation(location);
    if (Double.isNaN(this.dx)) {
      throw new IllegalArgumentException("pinPoint: " + location + " does not exist in " + graphic + ".");
    }
    this.dy = graphic.yForLocation(location);
    if (Double.isNaN(this.dy)) {
      throw new IllegalArgumentException("pinPoint: " + location + " does not exist in " + graphic + ".");
    }
    final Path2D.Double path = new Path2D.Double(graphic.getPath());
    path.transform(AffineTransform.getTranslateInstance(-this.dx, -this.dy));
    setPath(path);
    //setBaseY(graphic.getBaseY() - dy);
    //this.addBoundingBoxLocations();
  }

  @Override
  protected double xForLocation(final Location location) {
    if (location.getGraphic() == this) {
      return location.getX();
    } else {
      final double xInGraphic = graphic.xForLocation(location);
      return Double.isNaN(xInGraphic) ? Double.NaN : xInGraphic - this.dx;
    }
  }

  @Override
  protected double yForLocation(final Location location) {
    if (location.getGraphic() == this) {
      return location.getY();
    } else {
      final double yInGraphic = graphic.yForLocation(location);
      return Double.isNaN(yInGraphic) ? Double.NaN : yInGraphic - this.dy;
    }
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
    appendField(sb, indent, "location", "" + location);
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
