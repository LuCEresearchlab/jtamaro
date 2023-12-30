package jtamaro.internal.gui;

import jtamaro.internal.representation.GraphicImpl;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;


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

    tree.getSelectionModel().addTreeSelectionListener(ev -> {
      renderOptions.clearSelection();
      final TreePath[] paths = tree.getSelectionPaths();
      for (final TreePath path : paths) {
        final GraphicTreeNode node = (GraphicTreeNode) path.getLastPathComponent();
        final GraphicImpl graphic = node.getGraphic();
        renderOptions.select(graphic);
      }
    });
  }

  public void setGraphic(GraphicImpl g) {
    tree.setModel(new DefaultTreeModel(g.createTree()));
  }

}
