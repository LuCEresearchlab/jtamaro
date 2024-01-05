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

/**
 * This class includes methods to perform input and output for JTamaro classes.
 * It allows outputting JTamaro graphics, colors, points, sequences, and pairs.
 * 
 * It provides methods for textual output,
 * specifically, to easily print sequences and pairs to the standard output.
 * 
 * More importantly, it provides methods for graphical IO,
 * specifically to: show graphics, to animate sequences of graphics, and
 * to produce interactions.
 * 
 * It also provides methods to save graphics as PNG files and
 * animations as animated GIF files.
 */

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

public final class IO {


  // prevent instantiation
  private IO() {
  }

  /**
   * Print the given sequence, one element right after the next, to standard output.
   * Each element is converted into a String to be printed.
   * This does not add any newlines.
   * 
   * @param <T> the type of elements
   * @param sequence the sequence to print
   */
  public static <T> void print(Sequence<T> sequence) {
    for (T element : sequence) {
      System.out.print(element);
    }
  }

  /**
   * Print the given sequence, one element per line, to standard output.
   * Each element is converted into a String to be printed.
   * 
   * @param <T> the type of elements
   * @param sequence the sequence to print
   */
  public static <T> void println(Sequence<T> sequence) {
    for (T element : sequence) {
      System.out.println(element);
    }
  }

  /**
   * Print the given pair, one element right after the next, to the standard output.
   * Each element is converted into a String to be printed.
   * This does not add any newlines.
   * 
   * @param <F> the type of the first element
   * @param <S> the type of the second element
   * @param pair the pair to print
   */
  public static <F, S> void print(Pair<F, S> pair) {
    System.out.print(pair.first());
    System.out.print(pair.second());
  }

  /**
   * Print the given pair, one element per line, to the standard output.
   * Each element is converted into a String to be printed.
   * 
   * @param <F> the type of the first element
   * @param <S> the type of the second element
   * @param pair the pair to print
   */
  public static <F, S> void println(Pair<F, S> pair) {
    System.out.println(pair.first());
    System.out.println(pair.second());
  }

  /**
   * Open a window showing the given color.
   *
   * @param color the color to show
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
   * @param graphic the graphic to show
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
    assert millisecondsPerFrame > 0 : "Must wait a positive number of milliseconds between frames";
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
   * @param frameWidth the width of frames (should be at least the maximum of all the graphics' widths)
   * @param frameHeight the height of frames (should be at least the maximum of all the graphics' heights)
   */
  public static void showFilmStrip(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    assert frameWidth >= 0 : "Can't have a negative width";
    assert frameHeight >= 0 : "Can't have a negative height";
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
   * @return an Interaction that can be further configured and run
   */
  public static <M> Interaction<M> interact(M initialModel) {
    return new Interaction<>(initialModel);
  }

  /**
   * Save the given graphic as a PNG file.
   * 
   * @param graphic the Graphic to render
   * @param filename the name of the file (including the .png extension)
   */
  public static void save(Graphic graphic, String filename) {
    assert filename.endsWith(".png") : "Filename must end with .png";
    try {
      PngWriter.save(((AbstractGraphic) graphic).getImplementation(), filename);
    } catch (IOException ex) {
      System.err.println("Error saving PNG image to " + filename + ": " + ex.getMessage());
    }
  }

  /**
   * Save the given sequence of graphics into an animated GIF file.
   * 
   * @param graphics the finite sequence of graphics (frames) to save in the GIF
   * @param loop whether or not to loop the animation when playing
   * @param millisecondsPerFrame how many milliseconds to wait between each frame
   * @param filename the name of the file (including the .gif extension)
   */
  public static void saveAnimatedGif(Sequence<Graphic> graphics, boolean loop, int millisecondsPerFrame, String filename) {
    assert !graphics.isEmpty() : "Animation must have at least one frame";
    assert graphics.hasDefiniteSize() : "The sequence of graphics must contain a finite number of frames";
    assert millisecondsPerFrame > 0 : "Must wait a positive number of milliseconds between frames";
    assert filename.endsWith(".gif") : "Filename must end with .gif";
    try {
      GifWriter.saveAnimation(map(g -> ((AbstractGraphic) g).getImplementation(), graphics), millisecondsPerFrame, loop, filename);
    } catch (IOException ex) {
      System.err.println("Error saving animated GIF to " + filename + ": " + ex.getMessage());
    }
  }

}
