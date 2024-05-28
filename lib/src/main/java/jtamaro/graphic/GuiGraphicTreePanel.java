package jtamaro.graphic;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Swing panel component that renders the inspect tree of a graphic.
 *
 * @apiNote Not for public use.
 * @hidden
 */
public final class GuiGraphicTreePanel extends JPanel {

  private final JTree tree;

  public GuiGraphicTreePanel(RenderOptions renderOptions) {
    setLayout(new BorderLayout());
    tree = new JTree();
    tree.setCellRenderer(new GuiGraphicTreeCellRenderer());
    ToolTipManager.sharedInstance().registerComponent(tree);
    add(new JScrollPane(tree), BorderLayout.CENTER);

    tree.getSelectionModel().addTreeSelectionListener(ev -> {
      renderOptions.clearSelection();
      final TreePath[] paths = tree.getSelectionPaths();
      if (paths == null) {
        return;
      }
      for (final TreePath path : paths) {
        final Graphic.InspectTreeNode node = (Graphic.InspectTreeNode) path.getLastPathComponent();
        final Graphic graphic = node.getGraphic();
        renderOptions.select(graphic);
      }
    });
  }

  public void setGraphic(Graphic g) {
    tree.setModel(new DefaultTreeModel(g.createInspectTree()));
  }
}
