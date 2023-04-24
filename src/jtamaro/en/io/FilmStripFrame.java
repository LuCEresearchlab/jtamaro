package jtamaro.en.io;

import jtamaro.en.Graphic;
import jtamaro.en.Pair;
import jtamaro.en.Sequence;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static jtamaro.en.Sequences.reduce;
import static jtamaro.en.Sequences.zipWithIndex;


public final class FilmStripFrame extends JFrame {

  private final Sequence<Pair<Graphic, Integer>> indexedGraphics;
  private final DefaultBoundedRangeModel sliderModel;
  private final int frameCount;
  private final int completeFrameWith;


  public FilmStripFrame(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    indexedGraphics = zipWithIndex(graphics);
    completeFrameWith = FilmStripCanvas.computeCompleteFrameWidth(frameWidth);
    setTitle("Film Strip");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    final FilmStripCanvas canvas = new FilmStripCanvas(graphics, frameWidth, frameHeight);
    add(canvas, BorderLayout.CENTER);
    final JPanel bar = new JPanel(new BorderLayout());
    final JButton prevButton = new JButton("<");
    bar.add(prevButton, BorderLayout.WEST);
    final JButton nextButton = new JButton(">");
    bar.add(nextButton, BorderLayout.EAST);
    sliderModel = new DefaultBoundedRangeModel(0, 0, 0, 1);
    final JSlider slider = new JSlider(sliderModel);
    if (indexedGraphics.hasDefiniteSize()) {
      frameCount = reduce((count, pair) -> count + 1, 0, indexedGraphics);
      updateSliderModelMax();
      bar.add(slider, BorderLayout.CENTER);
    } else {
      // sequence may be infinite
      frameCount = -1;
    }
    add(bar, BorderLayout.SOUTH);

    // register listeners
    prevButton.addActionListener((ev) -> {
      double position = canvas.getPosition();
      if (position > 1) {
        position = position - 1;
      } else {
        position = 0;
      }
      canvas.setPosition(position);
    });
    nextButton.addActionListener((ev) -> {
      double position = canvas.getPosition();
      if (frameCount < 0) {
        // sequence may be infinite
        position = position + 1;
      } else {
        if (position < frameCount - 1) {
          position = position + 1;
        } else {
          position = frameCount;
        }
      }
      canvas.setPosition(position);
    });
    sliderModel.addChangeListener(ev -> {
      final int value = sliderModel.getValue();
      final int max = sliderModel.getMaximum();
      canvas.setPosition(value / (double) max);
    });

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent ev) {
        updateSliderModelMax();
      }
    });

    pack();
  }

  private void updateSliderModelMax() {
    if (frameCount >= 0) {
      final int width = getWidth();
      final int filmStripWidth = completeFrameWith * frameCount;
      final int max = Math.max(0, filmStripWidth - width);
      sliderModel.setMaximum(max);
      sliderModel.setValue(Math.min(sliderModel.getValue(), max));
      // if (max < sliderModel.getValue()) {
      //   sliderModel.setValue(max);
      // }
    }
  }

}
