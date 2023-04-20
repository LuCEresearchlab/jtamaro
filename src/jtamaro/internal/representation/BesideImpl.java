package jtamaro.internal.representation;

import jtamaro.internal.gui.GraphicTreeNode;


public final class BesideImpl extends DelegatingGraphicImpl implements CompositeImpl {

  private final GraphicImpl leftGraphic;
  private final GraphicImpl rightGraphic;


  public BesideImpl(GraphicImpl leftGraphic, GraphicImpl rightGraphic) {
    super(new PinPointImpl(PointImpl.MIDDLE,
        new ComposeImpl(
            new PinPointImpl(PointImpl.MIDDLE_RIGHT, leftGraphic),
            new PinPointImpl(PointImpl.MIDDLE_LEFT, rightGraphic)
        )
    ));
    this.leftGraphic = leftGraphic;
    this.rightGraphic = rightGraphic;
  }

  @Override
  public void dump(final StringBuilder sb, final String indent) {
    super.dump(sb, indent);
    appendChild(sb, indent, "leftGraphic", leftGraphic);
    appendChild(sb, indent, "rightGraphic", rightGraphic);
  }

  @Override
  public GraphicTreeNode createTree() {
    GraphicTreeNode node = new GraphicTreeNode(this);
    GraphicTreeNode leftGraphicNode = leftGraphic.createTree();
    node.add(leftGraphicNode);
    GraphicTreeNode rightGraphicNode = rightGraphic.createTree();
    node.add(rightGraphicNode);
    return node;
  }

}
