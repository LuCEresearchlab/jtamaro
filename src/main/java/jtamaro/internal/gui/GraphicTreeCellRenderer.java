package jtamaro.internal.gui;

import jtamaro.internal.representation.CompositeImpl;
import jtamaro.internal.representation.EllipseImpl;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.RotateImpl;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;


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
    if (value instanceof GraphicTreeNode) {
      GraphicTreeNode node = (GraphicTreeNode) value;
      GraphicImpl graphic = (GraphicImpl) node.getUserObject();
      setText(graphic.getClass().getSimpleName());
      String toolTipText = "<html><b>" + graphic.getClass().getSimpleName() + "</b>" +
          "<br>width: " + graphic.getWidth() +
          "<br>height: " + graphic.getHeight();
      if (graphic instanceof CompositeImpl) {
        final CompositeImpl composite = (CompositeImpl) graphic;
        //setIcon(compositeIcon);
      } else if (graphic instanceof EllipseImpl) {
        final EllipseImpl ellipse = (EllipseImpl) graphic;
        toolTipText += "<br>color: " + ellipse.getColor();
      } else if (graphic instanceof RotateImpl) {
        final RotateImpl rotate = (RotateImpl) graphic;
        toolTipText += "<br>angle: " + rotate.getAngle();
      }
      setToolTipText(toolTipText);
    }
    return this;
  }

}
