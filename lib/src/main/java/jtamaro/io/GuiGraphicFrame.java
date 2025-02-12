package jtamaro.io;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.io.IOException;
import java.nio.file.Path;
import javax.swing.JButton;
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
    final JButton saveButton = new JButton("Save");
    toolbar.add(saveButton);
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

    saveButton.addActionListener(e -> saveGraphic());

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

  private void saveGraphic() {
    final JFileChooser fileChooser = new JFileChooser();
    // PNG files
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setFileFilter(new FileNameExtensionFilter("PNG", "png"));
    // Set default file ($CWD/graphic.png)
    final Path cwd = Path.of(System.getProperty("user.dir"));
    final Path defaultFile = cwd.resolve("graphic.png");
    fileChooser.setCurrentDirectory(cwd.toFile());
    fileChooser.setSelectedFile(defaultFile.toFile());

    final int fileChooserResult = fileChooser.showSaveDialog(this);
    if (fileChooserResult != JFileChooser.APPROVE_OPTION) {
      return;
    }

    // Write the file
    final Path selectedFile = fileChooser.getSelectedFile().toPath();
    final boolean writeResult = canvas.saveGraphic(selectedFile);

    if (writeResult) {
      final int result = JOptionPane.showOptionDialog(this,
          "Saved graphic to " + selectedFile.toAbsolutePath(),
          "Save graphic",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.PLAIN_MESSAGE,
          null,
          new Object[]{"Ok", "Show in files"},
          null);
      if (result == 1) {
        try {
          Desktop.getDesktop().open(selectedFile.getParent().toFile());
        } catch (IOException ignored) {
        }
      }

    } else {
      JOptionPane.showMessageDialog(this,
          "Failed to save graphic to " + selectedFile.toAbsolutePath(),
          "Save graphic",
          JOptionPane.ERROR_MESSAGE);
    }
  }
}
