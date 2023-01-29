package jtamaro.en;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import jtamaro.en.graphic.AbstractGraphic;
import jtamaro.en.io.BigBang;
import jtamaro.en.io.FilmStripFrame;
import jtamaro.internal.gui.GraphicFrame;
import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.representation.GraphicImpl;


public class IO {

  public static <T> void print(Sequence<T> sequence) {
    for (T element : sequence) {
      System.out.print(element);
    }
    System.out.println();
  }

  public static <T> void println(Sequence<T> sequence) {
    for (T element : sequence) {
      System.out.println(element);
    }
  }


  /**
   * Open a window showing the given graphic.
   * @param graphic
   */
  public static void show(Graphic graphic) {
    final GraphicFrame frame = new GraphicFrame();
    frame.setGraphic(((AbstractGraphic)graphic).getImplementation());
    frame.setVisible(true);
  }

  /**
   * Open a window with a looped animation of the given graphics,
   * at 25 frames per second.
   * @param graphics sequence of graphics (frames) to animate
   */
  public static void animate(Sequence<Graphic> graphics) {
    animate(graphics, true, 25);
  }

  /**
   * Open a window with an animation of the given graphics, with the given delay between frames.
   * @param graphics sequence of graphics (frames) to animate
   * @param loop whether to loop the animation
   * @param millisecondsPerFrame delay between frames
   */
  public static void animate(Sequence<Graphic> graphics, boolean loop, int millisecondsPerFrame) {
    assert !graphics.isEmpty() : "Animation must have at least one frame";
    new BigBang<>(graphics)
      .withName("Animation")
      .withMsBetweenTicks(millisecondsPerFrame)
      .withTickHandler(model -> {
        Sequence<Graphic> rest = model.rest();
        return rest.isEmpty() ? (loop ? graphics : rest) : rest;
      })
      .withRenderer(model -> model.first())
      .withStoppingPredicate(model -> model.isEmpty())
      .run();
  }

  /**
   * Open a window with a scrollable film strip,
   * with each from for each of the given graphics.
   * @param graphics sequence of graphics (frames) to show in the film strip
   */
  public static void showFilmStrip(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    final FilmStripFrame frame = new FilmStripFrame(graphics, frameWidth, frameHeight);
    frame.setVisible(true);
  }

  /**
   * Open a window with an interactive application.
   * @param <M> Model type
   * @param initialModel The initial model state of the application
   * @return
   */
  public static <M> BigBang<M> interact(M initialModel) {
    return new BigBang<>(initialModel);
  }

  public static void save(Graphic graphic, String filename) {
    GraphicImpl graphicImpl = ((AbstractGraphic)graphic).getImplementation();
    int width = (int)graphicImpl.getWidth();
    int height = (int)graphicImpl.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    RenderOptions renderOptions = new RenderOptions(0);
    g2.translate(-graphicImpl.getBBox().getMinX(), -graphicImpl.getBBox().getMinY());
    graphicImpl.render(g2, renderOptions);
    //graphicImpl.drawDebugInfo(g2, renderOptions);
    try {
      ImageIO.write(bufferedImage, "png", new File(filename));
    } catch (IOException ex) {
      System.err.println("Error saving image to " + filename + ": " + ex.getMessage());
    }
  }

}
