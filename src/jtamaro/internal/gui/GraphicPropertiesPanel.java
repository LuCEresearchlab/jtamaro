package jtamaro.internal.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jtamaro.internal.representation.ColorImpl;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.RectangleImpl;

import javax.swing.JLabel;


public final class GraphicPropertiesPanel extends JPanel {

  private final JLabel label;
  private final GraphicCanvas canvas;
  private final RenderOptions canvasRenderOptions;


  public GraphicPropertiesPanel(RenderOptions renderOptions) {
    setLayout(new BorderLayout());
    label = new JLabel();
    add(label, BorderLayout.NORTH);
    canvasRenderOptions = new RenderOptions(10);
    canvas = new GraphicCanvas(canvasRenderOptions);
    add(new JScrollPane(canvas), BorderLayout.CENTER);

    setGraphic(null);

    renderOptions.addRenderOptionsListener(new RenderOptionsListener() {
      @Override
      public void renderOptionsChanged() {
        setGraphic(renderOptions.getLeadSelection());
      }
    });
  }

  public void setGraphic(GraphicImpl graphic) {
    if (graphic == null) {
      // use content to have a reasonable initial size of this component
      label.setText("<html>Select a node above to show it here</i><br><br><br><br><br><br><br>");
      canvas.setGraphic(new RectangleImpl(300, 200, new ColorImpl(0, 0, 0, 0)));
      canvasRenderOptions.clearSelection();
    } else {
      label.setText("<html><b>" + graphic.getClass().getSimpleName()+"</b>" +
        "<br>width: " + graphic.getWidth() +
        "<br>height: " + graphic.getHeight() +
        "<br>baseY: " + graphic.getBaseY());
      canvas.setGraphic(graphic);
      canvasRenderOptions.clearSelection();
      canvasRenderOptions.select(graphic);
    }
  }
  
}
