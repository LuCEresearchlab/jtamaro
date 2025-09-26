package jtamaro.graphic;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
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

  public GuiGraphicTreePanel(RenderOptions renderOptions, Graphic graphic) {
    super();
    setLayout(new BorderLayout());
    tree = new JTree();
    tree.setCellRenderer(new GuiGraphicTreeCellRenderer());
    tree.setModel(new DefaultTreeModel(graphic.createInspectTree()));
    ToolTipManager.sharedInstance().registerComponent(tree);
    final JScrollPane treeScrollPanel = new JScrollPane(tree);
    treeScrollPanel.setBorder(BorderFactory.createEmptyBorder());
    add(treeScrollPanel, BorderLayout.CENTER);

    tree.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          tree.clearSelection();
        }
      }
    });
    tree.getSelectionModel().addTreeSelectionListener(_ -> {
      renderOptions.clearSelection();
      final TreePath[] paths = tree.getSelectionPaths();
      if (paths == null) {
        return;
      }
      for (final TreePath path : paths) {
        final Graphic.InspectTreeNode node = (Graphic.InspectTreeNode) path.getLastPathComponent();
        renderOptions.select(node.getGraphic());
      }
    });
  }

  public void dispose() {
    ToolTipManager.sharedInstance().unregisterComponent(tree);
  }
}
