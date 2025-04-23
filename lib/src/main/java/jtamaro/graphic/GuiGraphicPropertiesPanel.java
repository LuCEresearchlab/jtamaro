package jtamaro.graphic;

import java.awt.BorderLayout;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Swing panel component that renders the properties of a graphic.
 *
 * @apiNote Not for public use.
 * @hidden
 */
public final class GuiGraphicPropertiesPanel extends JPanel {

  private final JLabel label;

  private final GuiGraphicCanvas canvas;

  private final RenderOptions canvasRenderOptions;

  public GuiGraphicPropertiesPanel(RenderOptions renderOptions) {
    super();
    setLayout(new BorderLayout());
    label = new JLabel();
    add(label, BorderLayout.NORTH);

    canvasRenderOptions = new RenderOptions(10);
    canvas = new GuiGraphicCanvas(canvasRenderOptions);
    final JScrollPane canvasScrollPanel = new JScrollPane(canvas);
    canvasScrollPanel.setBorder(BorderFactory.createEmptyBorder());
    add(canvasScrollPanel, BorderLayout.CENTER);

    setGraphic(null);

    renderOptions.addRenderOptionsListener(() -> setGraphic(renderOptions.getSelection()));
  }

  public void setGraphic(Graphic graphic) {
    if (graphic == null) {
      // use content to have a reasonable initial size of this component
      label.setText("<html>Select a node above to show it here<br><br></html>");
      canvas.setGraphic(new Rectangle(300, 200, new Color(0, 0, 0, 0)));
      canvasRenderOptions.clearSelection();
    } else {
      label.setText("<html><b>" + graphic.getInspectLabel() + "</b>"
          + "<table>"
          + graphic.getProps(false).entrySet().stream()
          .map(e -> "<tr><td><b>"
              + e.getKey()
              + "</b></td><td>"
              + e.getValue()
              + "</td></tr>")
          .collect(Collectors.joining())
          + "</table></html>");
      canvas.setGraphic(graphic);
      canvasRenderOptions.clearSelection();
      canvasRenderOptions.select(graphic);
    }
  }
}
