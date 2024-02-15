package jtamaro.internal.gui;

import jtamaro.internal.io.ClipboardUtil;
import jtamaro.internal.io.PngWriter;
import jtamaro.internal.representation.GraphicImpl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class GraphicFrame extends JFrame {

  private static JFileChooser FILE_CHOOSER;

  private final RenderOptions renderOptions;
  private GraphicImpl graphic;
  private final JLabel widthLabel;
  private final JLabel heightLabel;
  private final GraphicCanvas canvas;
  private final GraphicTreePanel treePanel;


  public GraphicFrame() {
    setTitle("JTamaro");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    renderOptions = new RenderOptions(10);

    // toolbar
    JPanel toolbar = new JPanel(new FlowLayout());
    toolbar.add(new JLabel("Padding:"));
    JTextField paddingField = new JTextField(3);
    paddingField.setText("" + renderOptions.getPadding());
    toolbar.add(paddingField);
    JButton saveButton = new JButton("Save");
    toolbar.add(saveButton);
    JButton copyButton = new JButton("Copy");
    toolbar.add(copyButton);
    add(toolbar, BorderLayout.NORTH);

    // canvas
    canvas = new GraphicCanvas(renderOptions);
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(canvas, BorderLayout.CENTER);

    JPanel statusBar = new JPanel(new FlowLayout());
    statusBar.add(new JLabel("Width:"));
    widthLabel = new JLabel();
    statusBar.add(widthLabel);
    statusBar.add(new JLabel("Height:"));
    heightLabel = new JLabel();
    statusBar.add(heightLabel);
    add(statusBar, BorderLayout.SOUTH);

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

    //--- listeners
    saveButton.addActionListener(e -> {
      final JFileChooser fileChooser = getOrCreateFileChooser();
      final int userSelection = fileChooser.showSaveDialog(GraphicFrame.this);
      if (userSelection == JFileChooser.APPROVE_OPTION) {
        final File file = fileChooser.getSelectedFile();
        try {
          PngWriter.save(graphic, file.getAbsolutePath(), renderOptions);
        } catch (final IOException ex) {
          JOptionPane.showMessageDialog(GraphicFrame.this, ex.getMessage());
        }
      }
    });
    copyButton.addActionListener(e -> ClipboardUtil.copyToClipboard(graphic, renderOptions));
    paddingField.addActionListener(e -> {
      try {
        int padding = Integer.parseInt(paddingField.getText());
        renderOptions.setPadding(padding);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(GraphicFrame.this, "Padding must be an integer");
      }
    });
    pack();
  }

  /**
   * We want one JFileChooser per running application.
   * This way we don't have to navigate to the desired directory
   * each time we save.
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

  public void setGraphic(GraphicImpl graphic) {
    this.graphic = graphic;
    canvas.setGraphic(graphic);
    treePanel.setGraphic(graphic);
    widthLabel.setText("" + graphic.getWidth());
    heightLabel.setText("" + graphic.getHeight());
    pack();
  }

}
