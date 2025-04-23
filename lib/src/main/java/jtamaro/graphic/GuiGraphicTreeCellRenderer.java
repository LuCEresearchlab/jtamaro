package jtamaro.graphic;

import java.awt.Component;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Swing tree cell component that renders the cell of an inspect tree of a graphic.
 *
 * @apiNote Not for public use.
 * @hidden
 */
public class GuiGraphicTreeCellRenderer extends DefaultTreeCellRenderer {

  /**
   * Default constructor.
   */
  public GuiGraphicTreeCellRenderer() {
    super();
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
      final Map<String, String> props = graphic.getProps(false);
      final String toolTipText = "<html><b>" + graphic.getInspectLabel() + "</b>"
          + "<table>"
          + props.entrySet().stream()
          .map(e -> "<tr><td><b>"
              + e.getKey()
              + "</b></td><td>"
              + e.getValue()
              + "</td></tr>")
          .collect(Collectors.joining())
          + "</table></html>";
      setToolTipText(toolTipText);
    }
    return this;
  }
}
