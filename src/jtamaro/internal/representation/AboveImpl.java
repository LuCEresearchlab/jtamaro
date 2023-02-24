package jtamaro.internal.representation;

import jtamaro.internal.gui.GraphicTreeNode;


public final class AboveImpl extends DelegatingGraphicImpl implements CompositeImpl {

  private final GraphicImpl topGraphic;
  private final GraphicImpl bottomGraphic;


  public AboveImpl(GraphicImpl topGraphic, GraphicImpl bottomGraphic) {
    super(new PinPointImpl(PointImpl.MIDDLE,
      new ComposeImpl(
        new PinPointImpl(PointImpl.BOTTOM_MIDDLE, topGraphic), 
        new PinPointImpl(PointImpl.TOP_MIDDLE, bottomGraphic)
      )
    ));
    this.topGraphic = topGraphic;
    this.bottomGraphic = bottomGraphic;
  }

  @Override
  public void dump(final StringBuilder sb, final String indent) {
    super.dump(sb, indent);
    appendChild(sb, indent, "topGraphic", topGraphic);
    appendChild(sb, indent, "bottomGraphic", bottomGraphic);
  }

  @Override
  public GraphicTreeNode createTree() {
    GraphicTreeNode node = new GraphicTreeNode(this);
    GraphicTreeNode topGraphicNode = topGraphic.createTree();
    node.add(topGraphicNode);
    GraphicTreeNode bottomGraphicNode = bottomGraphic.createTree();
    node.add(bottomGraphicNode);
    return node;
  }

}
