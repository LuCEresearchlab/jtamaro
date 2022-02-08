package jtamaro.internal.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import jtamaro.internal.representation.GraphicImpl;


public class GraphicTreePanel extends JPanel {

  private final RenderOptions renderOptions;
  private final JTree tree;

  
  public GraphicTreePanel(RenderOptions renderOptions) {
    this.renderOptions = renderOptions;
    setLayout(new BorderLayout());
    tree = new JTree();
    tree.setCellRenderer(new GraphicTreeCellRenderer());
    ToolTipManager.sharedInstance().registerComponent(tree);
    add(new JScrollPane(tree), BorderLayout.CENTER);

    tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
      @Override
      public void valueChanged(TreeSelectionEvent ev) {
        renderOptions.clearSelection();
        final TreePath[] paths = tree.getSelectionPaths();
        for (final TreePath path : paths) {
          final GraphicTreeNode node = (GraphicTreeNode)path.getLastPathComponent();
          final GraphicImpl graphic = node.getGraphic();
          renderOptions.select(graphic);
        }
      }
    });
  }

  public void setGraphic(GraphicImpl g) {
    tree.setModel(new DefaultTreeModel(g.createTree()));
  }

}
