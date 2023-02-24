package jtamaro.internal.representation;

import jtamaro.internal.gui.GraphicTreeNode;


public final class OverlayImpl extends DelegatingGraphicImpl implements CompositeImpl {

  private final GraphicImpl foregroundGraphic;
  private final GraphicImpl backgroundGraphic;


  public OverlayImpl(GraphicImpl foregroundGraphic, GraphicImpl backgroundGraphic) {
    super(new ComposeImpl(
      new PinPointImpl(PointImpl.MIDDLE, foregroundGraphic), 
      new PinPointImpl(PointImpl.MIDDLE, backgroundGraphic)));
    this.foregroundGraphic = foregroundGraphic;
    this.backgroundGraphic = backgroundGraphic;
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
