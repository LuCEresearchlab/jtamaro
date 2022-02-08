package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import jtamaro.internal.gui.GraphicTreeNode;
import jtamaro.internal.gui.RenderOptions;


public final class ComposeImpl extends GraphicImpl implements CompositeImpl {

  private final GraphicImpl foregroundGraphic;
  private final GraphicImpl backgroundGraphic;


  public ComposeImpl(final GraphicImpl foregroundGraphic, final GraphicImpl backgroundGraphic) {
    this.foregroundGraphic = foregroundGraphic;
    this.backgroundGraphic = backgroundGraphic;
    final Path2D.Double path = new Path2D.Double();
    path.append(backgroundGraphic.getPath(), false);
    path.append(foregroundGraphic.getPath(), false);
    setPath(path);
  }

  protected double xForPoint(final Point point) {
    if (point.getGraphic() == this) {
      return point.getX();
    } else {
      final double xInBottom = backgroundGraphic.xForPoint(point);
      final double xInTop = foregroundGraphic.xForPoint(point);
      if (!Double.isNaN(xInBottom) && !Double.isNaN(xInTop)) {
        throw new IllegalArgumentException(point + " exists multiple times in composition " + this + ". Did you add the same " + point.getGraphic() + " graphic twice?");
      } else if (!Double.isNaN(xInBottom)) {
        return xInBottom;
      } else if (!Double.isNaN(xInTop)) {
        return xInTop;
      } else {
        return Double.NaN;
      }
    }
  }

  protected double yForPoint(final Point point) {
    if (point.getGraphic() == this) {
      return point.getY();
    } else {
      final double yInBottom = backgroundGraphic.yForPoint(point);
      final double yInTop = foregroundGraphic.yForPoint(point);
      if (!Double.isNaN(yInBottom) && !Double.isNaN(yInTop)) {
        throw new IllegalArgumentException(point + " exists multiple times in composition " + this + ". Did you add the same " + point.getGraphic() + " graphic twice?");
      } else if (!Double.isNaN(yInBottom)) {
        return yInBottom;
      } else if (!Double.isNaN(yInTop)) {
        return yInTop;
      } else {
        return Double.NaN;
      }
    }
  }

  @Override
  public double getBaseline() {
    return 0;
  }

  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    backgroundGraphic.render(g2, o);
    foregroundGraphic.render(g2, o);
  }

  @Override
  public void dump(final StringBuilder sb, final String indent) {
    super.dump(sb, indent);
    appendChild(sb, indent, "foregroundGraphic", foregroundGraphic);
    appendChild(sb, indent, "backgroundGraphic", backgroundGraphic);
  }

  @Override
  public GraphicTreeNode createTree() {
    GraphicTreeNode node = new GraphicTreeNode(this);
    GraphicTreeNode foregroundGraphicNode = foregroundGraphic.createTree();
    node.add(foregroundGraphicNode);
    GraphicTreeNode backgroundGraphicNode = backgroundGraphic.createTree();
    node.add(backgroundGraphicNode);
    return node;
  }

}
