package jtamaro.en.io;

import jtamaro.en.Graphic;
import jtamaro.en.Pair;
import jtamaro.en.Sequence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static jtamaro.en.Sequences.*;


public final class FilmStripFrame extends JFrame {

  private final Sequence<Pair<Graphic, Integer>> indexedGraphics;
  private final DefaultBoundedRangeModel sliderModel;
  private final int frameCount;
  private final int completeFrameWidth;


  public FilmStripFrame(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    indexedGraphics = zipWithIndex(graphics);
    completeFrameWidth = FilmStripCanvas.computeCompleteFrameWidth(frameWidth);
    final String titleSuffix = graphics.hasDefiniteSize() ? " (" + length(graphics) + " frames)" : "";
    setTitle("Film Strip" + titleSuffix);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    final FilmStripCanvas canvas = new FilmStripCanvas(graphics, frameWidth, frameHeight);
    add(canvas, BorderLayout.CENTER);
    final JPanel bar = new JPanel(new BorderLayout());
    final JButton prevButton = new JButton("<");
    bar.add(prevButton, BorderLayout.WEST);
    final JButton nextButton = new JButton(">");
    bar.add(nextButton, BorderLayout.EAST);
    sliderModel = new DefaultBoundedRangeModel();
    final JScrollBar slider = new JScrollBar(JScrollBar.HORIZONTAL);
    slider.setModel(sliderModel);
    if (indexedGraphics.hasDefiniteSize()) {
      frameCount = length(graphics);
      bar.add(slider, BorderLayout.CENTER);
    } else {
      // sequence may be infinite
      frameCount = -1;
    }
    updateSliderModelMax();
    add(bar, BorderLayout.SOUTH);

    // register listeners
    prevButton.addActionListener((ev) -> {
      sliderModel.setValue(sliderModel.getValue() - completeFrameWidth);
    });
    nextButton.addActionListener((ev) -> {
      sliderModel.setValue(sliderModel.getValue() + completeFrameWidth);
    });
    sliderModel.addChangeListener(ev -> {
      printSliderModel("change: ");
      canvas.setPosition(sliderModel.getValue());
    });

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent ev) {
        updateSliderModelMax();
      }
    });

    pack();
  }

  private static <T> int length(Sequence<T> s) {
    return isEmpty(s) ? 0 : 1 + length(rest(s));
  }

  private void updateSliderModelMax() {
    final int min = 0;
    final int max = frameCount >= 0 ? completeFrameWidth * frameCount : Integer.MAX_VALUE;
    int value = sliderModel.getValue();
    final int extent = getWidth();
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
    printSliderModel("updateSliderModelMax:");
    sliderModel.setRangeProperties(value, extent, min, max, false);
  }

  private void printSliderModel(String message) {
      System.out.println(
        message +
        sliderModel.getMinimum() + " <= " +
        sliderModel.getValue() + " <= " +
        (sliderModel.getValue() + sliderModel.getExtent() + " <= " +
        sliderModel.getMaximum() + " -- extent: " +
        sliderModel.getExtent() + ", completeFrameWidth: " + completeFrameWidth)
      );
  }

}
