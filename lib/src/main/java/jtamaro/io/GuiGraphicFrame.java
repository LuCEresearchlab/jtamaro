package jtamaro.io;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.GuiGraphicCanvas;
import jtamaro.graphic.GuiGraphicPropertiesPanel;
import jtamaro.graphic.GuiGraphicTreePanel;
import jtamaro.graphic.RenderOptions;

final class GuiGraphicFrame extends JFrame {

  static {
    // Use system menu bar on macOS
    System.setProperty("apple.laf.useScreenMenuBar", "true");
  }

  private final RenderOptions renderOptions;

  private final GuiGraphicCanvas canvas;

  private final GuiGraphicTreePanel treePanel;

  public GuiGraphicFrame() {
    super();
    setTitle("JTamaro");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    renderOptions = new RenderOptions(10);

    // menu bar
    final JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    final int menuShortcutKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

    final JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    menuBar.add(fileMenu);

    final JMenuItem copyItem = new JMenuItem("Copy graphic", KeyEvent.VK_C);
    copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
        KeyEvent.SHIFT_DOWN_MASK | menuShortcutKey));
    copyItem.addActionListener(e -> copyGraphicToClipboard());
    fileMenu.add(copyItem);

    final JMenuItem saveItem = new JMenuItem("Save graphic", KeyEvent.VK_S);
    saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, menuShortcutKey));
    saveItem.addActionListener(e -> saveGraphic());
    fileMenu.add(saveItem);

    final JMenu editMenu = new JMenu("Edit");
    fileMenu.setMnemonic(KeyEvent.VK_E);
    menuBar.add(editMenu);

    final JMenuItem paddingItem = new JMenuItem("Padding", KeyEvent.VK_P);
    paddingItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, menuShortcutKey));
    paddingItem.addActionListener(e -> setPadding());
    editMenu.add(paddingItem);

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

    pack();
  }

  public GuiGraphicFrame(Graphic graphic) {
    this();
    setGraphic(graphic);
  }

  @Override
  public void dispose() {
    treePanel.dispose();
    super.dispose();
  }

  public void setGraphic(Graphic graphic) {
    canvas.setGraphic(graphic);
    treePanel.setGraphic(graphic);
    pack();
  }

  private void setPadding() {
    final String valueStr = (String) JOptionPane.showInputDialog(this,
        "Set the padding for the graphic",
        "Padding",
        JOptionPane.PLAIN_MESSAGE,
        null,
        null,
        String.valueOf(renderOptions.getPadding()));

    if (valueStr == null) {
      // User clicked on cancel
      return;
    }

    try {
      final int padding = Integer.parseInt(valueStr);
      renderOptions.setPadding(padding);
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this,
          "Padding must be an integer",
          "Padding",
          JOptionPane.ERROR_MESSAGE);
    }
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
        // Show in files option
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

  private void copyGraphicToClipboard() {
    final boolean copyResult = canvas.copyGraphicToClipboard();
    if (!copyResult) {
      JOptionPane.showMessageDialog(this,
          "Failed to copy graphic to clipboard",
          "Copy graphic",
          JOptionPane.ERROR_MESSAGE);
    }
  }
}
