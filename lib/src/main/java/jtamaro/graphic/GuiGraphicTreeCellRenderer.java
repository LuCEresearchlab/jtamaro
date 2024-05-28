package jtamaro.graphic;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Swing tree cell component that renders the cell of an inspect tree of a graphic.
 *
 * @apiNote Not for public use.
 * @hidden
 */
public class GuiGraphicTreeCellRenderer extends DefaultTreeCellRenderer {

  public GuiGraphicTreeCellRenderer() {
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
    if (value instanceof Graphic.InspectTreeNode node) {
      final Graphic graphic = node.getGraphic();
      setText(graphic.getInspectLabel());
      final String toolTipText = "<html><b>" + graphic.getInspectLabel() + "</b>"
          + "<br>width: " + graphic.getWidth()
          + "<br>height: " + graphic.getHeight()
          + "</html>";
      setToolTipText(toolTipText);
    }
    return this;
  }
}
