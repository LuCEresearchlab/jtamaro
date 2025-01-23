package jtamaro.io;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.GuiGraphicCanvas;
import jtamaro.graphic.GuiGraphicPropertiesPanel;
import jtamaro.graphic.GuiGraphicTreePanel;
import jtamaro.graphic.RenderOptions;

final class GuiGraphicFrame extends JFrame {

  private final RenderOptions renderOptions;

  private final GuiGraphicCanvas canvas;

  private final GuiGraphicTreePanel treePanel;

  public GuiGraphicFrame() {
    setTitle("JTamaro");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    renderOptions = new RenderOptions(10);

    // toolbar
    final JPanel toolbar = new JPanel(new FlowLayout());
    toolbar.add(new JLabel("Padding:"));
    final JTextField paddingField = new JTextField(3);
    paddingField.setText("" + renderOptions.getPadding());
    toolbar.add(paddingField);
    add(toolbar, BorderLayout.NORTH);

    // canvas
    canvas = new GuiGraphicCanvas(renderOptions);
    final JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(canvas, BorderLayout.CENTER);

    // tree
    treePanel = new GuiGraphicTreePanel(renderOptions);
    final GuiGraphicPropertiesPanel propertiesPanel = new GuiGraphicPropertiesPanel(renderOptions);
    final JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    rightSplitPane.setTopComponent(treePanel);
    rightSplitPane.setBottomComponent(propertiesPanel);

    final JSplitPane topSplitPane = new JSplitPane();
    topSplitPane.setLeftComponent(panel);
    topSplitPane.setRightComponent(rightSplitPane);
    add(topSplitPane, BorderLayout.CENTER);

    //--- listeners
    paddingField.addActionListener(e -> {
      try {
        final int padding = Integer.parseInt(paddingField.getText());
        renderOptions.setPadding(padding);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(GuiGraphicFrame.this, "Padding must be an integer");
      }
    });
    pack();
  }

  public GuiGraphicFrame(Graphic graphic) {
    this();
    setGraphic(graphic);
  }

  public void setGraphic(Graphic graphic) {
    canvas.setGraphic(graphic);
    treePanel.setGraphic(graphic);
    pack();
  }
}
