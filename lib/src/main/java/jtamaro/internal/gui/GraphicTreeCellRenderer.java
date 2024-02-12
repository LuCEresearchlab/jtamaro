package jtamaro.internal.gui;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import jtamaro.internal.representation.EllipseImpl;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.RotateImpl;


public class GraphicTreeCellRenderer extends DefaultTreeCellRenderer {

  public GraphicTreeCellRenderer() {
  }

  @Override
  public Component getTreeCellRendererComponent(
      JTree tree,
      Object value,
      boolean sel,
      boolean expanded,
      boolean leaf,
      int row,
      boolean hasFocus) {

    super.getTreeCellRendererComponent(
        tree, value, sel,
        expanded, leaf, row,
        hasFocus);
    if (value instanceof GraphicTreeNode node) {
        GraphicImpl graphic = (GraphicImpl) node.getUserObject();
      setText(graphic.getClass().getSimpleName());
      String toolTipText = "<html><b>" + graphic.getClass().getSimpleName() + "</b>" +
          "<br>width: " + graphic.getWidth() +
          "<br>height: " + graphic.getHeight();
      if (graphic instanceof EllipseImpl ellipse) {
          toolTipText += "<br>color: " + ellipse.getColor();
      } else if (graphic instanceof RotateImpl rotate) {
          toolTipText += "<br>angle: " + rotate.getAngle();
      }
      setToolTipText(toolTipText);
    }
    return this;
  }

}
