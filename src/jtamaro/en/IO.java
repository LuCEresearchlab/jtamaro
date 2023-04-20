package jtamaro.en;

import jtamaro.en.graphic.AbstractGraphic;
import jtamaro.en.io.ColorFrame;
import jtamaro.en.io.FilmStripFrame;
import jtamaro.en.io.Interaction;
import jtamaro.internal.gui.GraphicFrame;
import jtamaro.internal.io.GifWriter;
import jtamaro.internal.io.PngWriter;

import javax.swing.*;
import java.io.IOException;

import static jtamaro.en.Sequences.map;


public class IO {

  public static <T> void print(Sequence<T> sequence) {
    for (T element : sequence) {
      System.out.print(element);
    }
  }

  public static <T> void println(Sequence<T> sequence) {
    for (T element : sequence) {
      System.out.println(element);
    }
  }

  public static <F, S> void print(Pair<F, S> pair) {
    System.out.print(pair.first());
    System.out.print(pair.second());
  }

  public static <F, S> void println(Pair<F, S> pair) {
    System.out.println(pair.first());
    System.out.println(pair.second());
  }

  // The show methods open an entire world of visualization of program state.
  // There are a lot of open questions.
  // Right now there are just a couple of special-purpose methods,
  // to show a color or a graphic.
  // But we could create a general show method, that could show anything,
  // including a Pair (containing two values that themselves can be shown),
  // or a Sequence (containing many values that themselves can be shown).
  // How to show such data structures is an open question.
  // We could show it as a stack-and-heap diagram,
  // with arrows between cells.
  // This reminds me of Erich Gamma's Eclipse Spider plugin,
  // that allowed incrementally unfolding a data structure shown as a graph
  // (I think the Eclipse plugins or something).
  // We could show it using nesting (given the immutable sublanguage we use,
  // there are no cycles and thus nesting works fine; just some duplication in case of dags).

  /**
   * Open a window showing the given color.
   *
   * @param color
   */
  public static void show(Color color) {
    SwingUtilities.invokeLater(() -> {
      final ColorFrame frame = new ColorFrame(color.getImplementation());
      frame.setVisible(true);
    });
  }

  /**
   * Open a window showing the given graphic.
   *
   * @param graphic
   */
  public static void show(Graphic graphic) {
    SwingUtilities.invokeLater(() -> {
      final GraphicFrame frame = new GraphicFrame();
      frame.setGraphic(((AbstractGraphic) graphic).getImplementation());
      frame.setVisible(true);
    });
  }

  // TODO: show(Point)
  // showing a Point, in a -1 ... +1 coordinate system, with a crosshair,
  // and special highlighting if it's one of the nine standard points

  // TODO: show(Pair)
  // showing a Pair, as two cells, where each cell renders whatever it contains (incl. a Graphic, Color, Point, Sequence, ...)

  // TODO: show(Sequence)
  // show a Sequence, as a list, where each cell renders whatever it contains (incl. a Graphic, Color, Point, Sequence, ...)

  /**
   * Open a window with a looped animation of the given graphics,
   * at 25 frames per second.
   *
   * @param graphics sequence of graphics (frames) to animate
   */
  public static void animate(Sequence<Graphic> graphics) {
    animate(graphics, true, 25);
  }

  /**
   * Open a window with an animation of the given graphics, with the given delay between frames.
   *
   * @param graphics             sequence of graphics (frames) to animate
   * @param loop                 whether to loop the animation
   * @param millisecondsPerFrame delay between frames
   */
  public static void animate(Sequence<Graphic> graphics, boolean loop, int millisecondsPerFrame) {
    assert !graphics.isEmpty() : "Animation must have at least one frame";
    new Interaction<>(graphics)
        .withName("Animation")
        .withMsBetweenTicks(millisecondsPerFrame)
        .withTickHandler(model -> {
          Sequence<Graphic> rest = model.rest();
          return rest.isEmpty() ? (loop ? graphics : rest) : rest;
        })
        .withRenderer(Sequence::first)
        .withStoppingPredicate(Sequence::isEmpty)
        .run();
  }

  /**
   * Open a window with a scrollable film strip,
   * with each from for each of the given graphics.
   *
   * @param graphics sequence of graphics (frames) to show in the film strip
   */
  public static void showFilmStrip(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    SwingUtilities.invokeLater(() -> {
      final FilmStripFrame frame = new FilmStripFrame(graphics, frameWidth, frameHeight);
      frame.setVisible(true);
    });
  }

  /**
   * Open a window with an interactive application.
   *
   * @param <M>          Model type
   * @param initialModel The initial model state of the application
   * @return
   */
  public static <M> Interaction<M> interact(M initialModel) {
    return new Interaction<>(initialModel);
  }

  public static void save(Graphic graphic, String filename) {
    try {
      PngWriter.save(((AbstractGraphic) graphic).getImplementation(), filename);
    } catch (IOException ex) {
      System.err.println("Error saving PNG image to " + filename + ": " + ex.getMessage());
    }
  }

  public static void saveAnimatedGif(Sequence<Graphic> graphics, boolean loop, int millisecondsPerFrame, String filename) {
    try {
      GifWriter.saveAnimation(map(g -> ((AbstractGraphic) g).getImplementation(), graphics), millisecondsPerFrame, loop, filename);
    } catch (IOException ex) {
      System.err.println("Error saving animated GIF to " + filename + ": " + ex.getMessage());
    }
  }

}
