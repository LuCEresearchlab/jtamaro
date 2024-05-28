package jtamaro.io.graphic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.GuiGraphicCanvas;
import jtamaro.graphic.GuiGraphicPropertiesPanel;
import jtamaro.graphic.GuiGraphicTreePanel;
import jtamaro.graphic.RenderOptions;

public class GuiGraphicFrame extends JFrame {

  private static JFileChooser FILE_CHOOSER;

  private final RenderOptions renderOptions;

  private final JLabel widthLabel;

  private final JLabel heightLabel;

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

    final JPanel statusBar = new JPanel(new FlowLayout());
    statusBar.add(new JLabel("Width:"));
    widthLabel = new JLabel();
    statusBar.add(widthLabel);
    statusBar.add(new JLabel("Height:"));
    heightLabel = new JLabel();
    statusBar.add(heightLabel);
    add(statusBar, BorderLayout.SOUTH);

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
        int padding = Integer.parseInt(paddingField.getText());
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

  /**
   * We want one JFileChooser per running application. This way we don't have to navigate to the
   * desired directory each time we save.
   */
  private static JFileChooser getOrCreateFileChooser() {
    if (FILE_CHOOSER == null) {
      FILE_CHOOSER = new JFileChooser();
      FILE_CHOOSER.setDialogTitle("Save Graphic as PNG");
      FILE_CHOOSER.setSelectedFile(new File("jtamaro.png"));
      FILE_CHOOSER.addChoosableFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
      FILE_CHOOSER.setFileSelectionMode(JFileChooser.FILES_ONLY);
      FILE_CHOOSER.setAcceptAllFileFilterUsed(false);
    }
    return FILE_CHOOSER;
  }

  public void setGraphic(Graphic graphic) {
    canvas.setGraphic(graphic);
    treePanel.setGraphic(graphic);
    widthLabel.setText(String.valueOf(graphic.getWidth()));
    heightLabel.setText(String.valueOf(graphic.getHeight()));
    pack();
  }
}
