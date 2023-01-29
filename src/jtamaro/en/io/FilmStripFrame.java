package jtamaro.en.io;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jtamaro.en.Graphic;
import jtamaro.en.Sequence;


public final class FilmStripFrame extends JFrame {
  
  private final Sequence<Graphic> graphics;


  public FilmStripFrame(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    this.graphics = graphics;
    setTitle("Film Strip");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    final FilmStripCanvas canvas = new FilmStripCanvas(graphics, frameWidth, frameHeight);
    add(canvas, BorderLayout.CENTER);
    final JSlider slider = new JSlider(0, 3 * 100);
    add(slider, BorderLayout.SOUTH);

    // register listeners
    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent ev) {
        int value = slider.getValue();
        canvas.setPosition(value / 100.0);
      }
    });
    
    pack();
  }

}
