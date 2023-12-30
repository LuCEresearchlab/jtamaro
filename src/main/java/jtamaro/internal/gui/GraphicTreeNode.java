package jtamaro.internal.gui;

import jtamaro.internal.representation.GraphicImpl;

import javax.swing.tree.DefaultMutableTreeNode;


public class GraphicTreeNode extends DefaultMutableTreeNode {

  public GraphicTreeNode(GraphicImpl g) {
    super(g);
  }

  public GraphicImpl getGraphic() {
    return (GraphicImpl) getUserObject();
  }

}
