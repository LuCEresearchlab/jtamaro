package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import jtamaro.internal.gui.GraphicTreeNode;
import jtamaro.internal.gui.RenderOptions;


public final class RotateImpl extends GraphicImpl implements CompositeImpl {

  private final double angle;
  private final GraphicImpl graphic;


  /**
   * Rotate the graphic clockwise by the given angle
   * (this is different from PyTamaro, where Rotate works counterclockwise).
   * 
   * @param angle
   * @param graphic
   */
  public RotateImpl(double angle, GraphicImpl graphic) {
    this.angle = angle;
    this.graphic = graphic;
    final Path2D.Double path = new Path2D.Double(graphic.getPath());
    path.transform(AffineTransform.getRotateInstance(-Math.toRadians(angle)));
    setPath(path);
    setBaseY(getBBox().getMaxY());
    addBoundingBoxLocations();
  }

  public double getAngle() {
    return angle;
  }

  protected double xForLocation(final Location location) {
    if (location.getGraphic() == this) {
      return location.getX();
    } else {
      // rotate xInGraphic by angle
      final double xInGraphic = graphic.xForLocation(location);
      final double yInGraphic = graphic.yForLocation(location);
      if (!Double.isNaN(xInGraphic) && !Double.isNaN(yInGraphic)) {
        return Math.cos(Math.toRadians(angle)) * xInGraphic - Math.sin(Math.toRadians(angle)) * yInGraphic;
      } else {
        return Double.NaN;
      }
    }
  }

  protected double yForLocation(final Location location) {
    if (location.getGraphic() == this) {
      return location.getY();
    } else {
      // rotate yInGraphic by angle
      final double xInGraphic = graphic.xForLocation(location);
      final double yInGraphic = graphic.yForLocation(location);
      if (!Double.isNaN(xInGraphic) && !Double.isNaN(yInGraphic)) {
        return Math.sin(Math.toRadians(angle)) * xInGraphic + Math.cos(Math.toRadians(angle)) * yInGraphic;
      } else {
        return Double.NaN;
      }
    }
  }

  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    AffineTransform baseTransform = g2.getTransform();

    g2.rotate(-Math.toRadians(angle));
    graphic.render(g2, o);

    g2.setTransform(baseTransform);
  }

  @Override
  public void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    appendField(sb, indent, "angle", ""+angle);
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
