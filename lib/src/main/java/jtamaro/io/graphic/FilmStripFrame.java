package jtamaro.io.graphic;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import jtamaro.data.Sequence;
import jtamaro.graphic.FilmStripCanvas;
import jtamaro.graphic.Graphic;

public final class FilmStripFrame extends JFrame {

  private final DefaultBoundedRangeModel sliderModel;

  private final int frameCount;

  private final int completeFrameWidth;

  private final FilmStripCanvas canvas;

  public FilmStripFrame(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    completeFrameWidth = FilmStripCanvas.computeCompleteFrameWidth(frameWidth);
    final String titleSuffix = " (" + length(graphics) + " frames)";
    setTitle("Film Strip" + titleSuffix);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    canvas = new FilmStripCanvas(graphics, frameWidth, frameHeight);
    add(canvas, BorderLayout.CENTER);
    final JPanel bar = new JPanel(new BorderLayout());
    final JButton prevButton = new JButton("<");
    bar.add(prevButton, BorderLayout.WEST);
    final JButton nextButton = new JButton(">");
    bar.add(nextButton, BorderLayout.EAST);
    sliderModel = new DefaultBoundedRangeModel();
    final JScrollBar slider = new JScrollBar(JScrollBar.HORIZONTAL);
    slider.setModel(sliderModel);
    frameCount = length(graphics);
    bar.add(slider, BorderLayout.CENTER);
    updateSliderModelMax();
    add(bar, BorderLayout.SOUTH);

    // register listeners
    prevButton.addActionListener((ev) ->
        sliderModel.setValue(sliderModel.getValue() - completeFrameWidth));
    nextButton.addActionListener((ev) ->
        sliderModel.setValue(sliderModel.getValue() + completeFrameWidth));
    sliderModel.addChangeListener(ev ->
        canvas.setPosition(sliderModel.getValue()));

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent ev) {
        updateSliderModelMax();
      }
    });

    pack();
  }

  private static <T> int length(Sequence<T> s) {
    return s.isEmpty() ? 0 : 1 + length(s.rest());
  }

  private void updateSliderModelMax() {
    final int min = 0;
    final int max = frameCount >= 0 ? completeFrameWidth * frameCount : Integer.MAX_VALUE;
    int value = sliderModel.getValue();
    final int extent = canvas.getNetWidth();
    // if the window became wider (extent grew)
    if (value + extent > max) {
      // keep scrolled to the right edge
      value = max - extent;
      // if window is wider than filmstrip
      if (value < 0) {
        // keep scrolled to the left
        value = 0;
      }
    }
    sliderModel.setRangeProperties(value, extent, min, max, false);
  }
}
