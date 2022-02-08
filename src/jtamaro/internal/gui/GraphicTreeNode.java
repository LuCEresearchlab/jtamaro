package jtamaro.internal.gui;

import javax.swing.tree.DefaultMutableTreeNode;

import jtamaro.internal.representation.GraphicImpl;


public class GraphicTreeNode extends DefaultMutableTreeNode {

  public GraphicTreeNode(GraphicImpl g) {
    super(g);
  }
  
  public GraphicImpl getGraphic() {
    return (GraphicImpl)getUserObject();
  }

}
