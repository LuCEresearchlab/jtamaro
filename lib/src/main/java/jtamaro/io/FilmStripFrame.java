package jtamaro.io;

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

final class FilmStripFrame extends JFrame {

  private final DefaultBoundedRangeModel sliderModel;

  private final int frameCount;

  private final int completeFrameWidth;

  private final FilmStripCanvas canvas;

  public FilmStripFrame(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    super();
    setTitle("Film Strip (" + length(graphics) + " frames)");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    completeFrameWidth = FilmStripCanvas.computeCompleteFrameWidth(frameWidth);
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
    prevButton.addActionListener(_ ->
        sliderModel.setValue(sliderModel.getValue() - completeFrameWidth));
    nextButton.addActionListener(_ ->
        sliderModel.setValue(sliderModel.getValue() + completeFrameWidth));
    sliderModel.addChangeListener(_ ->
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
    return s.reduce(0, (_, acc) -> acc + 1);
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
