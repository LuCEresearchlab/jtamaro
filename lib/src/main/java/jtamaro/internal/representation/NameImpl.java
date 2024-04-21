package jtamaro.internal.representation;

import jtamaro.internal.gui.GraphicTreeNode;
import jtamaro.internal.gui.RenderOptions;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;


public final class NameImpl extends GraphicImpl implements CompositeImpl {

  private final String name;
  private final GraphicImpl graphic;


  /**
   * Name the graphic.
   *
   * @param name
   * @param graphic
   */
  public NameImpl(String name, GraphicImpl graphic) {
    this.name = name;
    this.graphic = graphic;
    setPath(graphic.getPath());
    //setBaseY(getBBox().getMaxY());
    //addBoundingBoxLocations();
  }

  public String getName() {
    return name;
  }

  @Override
  protected double xForLocation(final Location location) {
    if (location.getGraphic() == this) {
      return location.getX();
    } else {
      return graphic.xForLocation(location);
    }
  }

  @Override
  protected double yForLocation(final Location location) {
    if (location.getGraphic() == this) {
      return location.getY();
    } else {
      return graphic.yForLocation(location);
    }
  }

  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    graphic.render(g2, o);
  }

  @Override
  public void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    appendField(sb, indent, "name", "" + name);
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
