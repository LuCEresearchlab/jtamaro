package jtamaro.internal.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import jtamaro.internal.representation.GraphicImpl;

import java.awt.BorderLayout;


public class GraphicFrame extends JFrame {
  
  private RenderOptions renderOptions;
  private final GraphicCanvas canvas;
  private final GraphicTreePanel treePanel;


  public GraphicFrame() {
    setTitle("JTamaro");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    renderOptions = new RenderOptions(50);

    // canvas
    canvas = new GraphicCanvas(renderOptions);
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(canvas, BorderLayout.CENTER);

    // tree
    treePanel = new GraphicTreePanel(renderOptions);
    GraphicPropertiesPanel propertiesPanel = new GraphicPropertiesPanel(renderOptions);
    JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    rightSplitPane.setTopComponent(treePanel);
    rightSplitPane.setBottomComponent(propertiesPanel);
    //rightSplitPane.setDividerLocation(300);

    JSplitPane topSplitPane = new JSplitPane();
    topSplitPane.setLeftComponent(panel);
    topSplitPane.setRightComponent(rightSplitPane);
    add(topSplitPane, BorderLayout.CENTER);

    pack();
  }

  public void setGraphic(GraphicImpl graphic) {
    canvas.setGraphic(graphic);
    treePanel.setGraphic(graphic);
    pack();
  }
  
}
