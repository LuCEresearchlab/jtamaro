package jtamaro.internal.gui;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import jtamaro.internal.io.ClipboardUtil;
import jtamaro.internal.io.PngWriter;
import jtamaro.internal.representation.GraphicImpl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;


public class GraphicFrame extends JFrame {
  
  private RenderOptions renderOptions;
  private GraphicImpl graphic;
  private final GraphicCanvas canvas;
  private final GraphicTreePanel treePanel;


  public GraphicFrame() {
    setTitle("JTamaro");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Save Graphic as PNG");
      fileChooser.setSelectedFile(new File("jtamaro.png"));
      fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);
      final int userSelection = fileChooser.showSaveDialog(GraphicFrame.this);
      if (userSelection == JFileChooser.APPROVE_OPTION) {
        final File file = fileChooser.getSelectedFile();
        try {
          PngWriter.save(graphic, file.getAbsolutePath(), renderOptions);
        } catch (final IOException ex) {
          JOptionPane.showMessageDialog(GraphicFrame.this, ex.getMessage());
        }
      };
    });
    copyButton.addActionListener(e -> {
      ClipboardUtil.copyToClipboard(graphic, renderOptions);
    });
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

  public void setGraphic(GraphicImpl graphic) {
    this.graphic = graphic;
    canvas.setGraphic(graphic);
    treePanel.setGraphic(graphic);
    pack();
  }
  
}
