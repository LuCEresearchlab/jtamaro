package jtamaro.io;

import javax.swing.SwingUtilities;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.Interaction;

/**
 * Procedures to perform I/O operations with graphics and interactions.
 *
 * <p>Note: all methods in this class are <b>impure</b>!
 */
public final class GraphicIO {
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
  // We could show it using nesting (given the immutable sub-language we use,
  // there are no cycles and thus nesting works fine; just some duplication in case of dags).

  private GraphicIO() {
  }

  /**
   * Open a window showing the given graphic.
   *
   * @param graphic the graphic to show
   */
  public static void show(Graphic graphic) {
    if (graphic == null) {
      System.err.println("Nothing to show");
    } else {
      SwingUtilities.invokeLater(() -> new GuiGraphicFrame(graphic).setVisible(true));
    }
  }

  /**
   * Open a window showing the given color.
   *
   * @param color the color to show
   */
  public static void show(Color color) {
    if (color == null) {
      System.err.println("Nothing to show");
    } else {
      SwingUtilities.invokeLater(() -> new ColorFrame(color).setVisible(true));
    }
  }

  /**
   * Open a window with a looped animation of the given graphics, at 25 frames per second.
   *
   * @param graphics sequence of graphics (frames) to animate
   */
  public static void animate(Sequence<Graphic> graphics) {
    animate(graphics, true, 25);
  }

  /**
   * Open a window with a looped animation of the given graphics.
   *
   * @param graphics             sequence of graphics (frames) to animate
   * @param millisecondsPerFrame delay between frames
   */
  public static void animate(Sequence<Graphic> graphics, int millisecondsPerFrame) {
    animate(graphics, true, millisecondsPerFrame);
  }

  /**
   * Open a window with an animation of the given graphics, at 25 frames per second.
   *
   * @param graphics sequence of graphics (frames) to animate
   * @param loop     whether to loop the animation
   */
  public static void animate(Sequence<Graphic> graphics, boolean loop) {
    animate(graphics, loop, 25);
  }

  /**
   * Open a window with an animation.
   *
   * @param graphics             sequence of graphics (frames) to animate
   * @param loop                 whether to loop the animation
   * @param millisecondsPerFrame delay between frames
   */
  public static void animate(Sequence<Graphic> graphics, boolean loop, int millisecondsPerFrame) {
    if (graphics == null) {
      System.err.println("Nothing to animate");
      return;
    }

    assert !graphics.isEmpty() : "Animation must have at least one frame";
    assert millisecondsPerFrame > 0 : "Must wait a positive number of milliseconds between frames";
    final Pair<Double, Double> wh = determineMaxWidthHeight(graphics);
    final String titleFramesSuffix = " ("
        + length(graphics)
        + " frames)";
    final String titleLoopedSuffix = loop ? ", looped" : "";
    new Interaction<>(graphics)
        .withName("Animation" + titleFramesSuffix + titleLoopedSuffix)
        .withCanvasSize(wh.first().intValue(), wh.second().intValue())
        .withMsBetweenTicks(millisecondsPerFrame)
        .withTickHandler(model -> {
          final Sequence<Graphic> rest = model.rest();
          return rest.isEmpty() ? (loop ? graphics : rest) : rest;
        })
        .withRenderer(Sequence::first)
        .withStoppingPredicate(Sequence::isEmpty)
        .run();
  }

  /**
   * Open a window with a scrollable filmstrip, with a frame for each of the given graphics. This
   * method automatically detects the width and height of the frames. For empty sequences, it uses a
   * default frame size. For sequences with a definite size, it determines the maximum width and
   * height.
   *
   * @param graphics sequence of graphics (frames) to show in the filmstrip
   */
  public static void showFilmStrip(Sequence<Graphic> graphics) {
    final Pair<Double, Double> wh = determineMaxWidthHeight(graphics);
    showFilmStrip(graphics, wh.first().intValue(), wh.second().intValue());
  }

  /**
   * Open a window with a scrollable filmstrip, with a frame for each of the given graphics.
   *
   * @param graphics    sequence of graphics (frames) to show in the filmstrip
   * @param frameWidth  the width of frames (should be at least the maximum of all the graphics'
   *                    widths)
   * @param frameHeight the height of frames (should be at least the maximum of all the graphics'
   *                    heights)
   */
  public static void showFilmStrip(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    if (graphics == null) {
      System.err.println("Nothing to show");
    } else {
      assert frameWidth >= 0 : "Can't have a negative width";
      assert frameHeight >= 0 : "Can't have a negative height";
      SwingUtilities.invokeLater(() ->
          new FilmStripFrame(graphics, frameWidth, frameHeight).setVisible(true));
    }
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

  private static Pair<Double, Double> determineMaxWidthHeight(Sequence<Graphic> graphics) {
    return graphics.isEmpty()
        // no frames: use a default
        ? new Pair<>(400.0, 300.0)
        // known, finite number of frames: determine max
        : graphics.reduce(new Pair<>(0.0, 0.0), (g, acc) -> new Pair<>(
            Math.max(acc.first(), g.getWidth()),
            Math.max(acc.second(), g.getHeight())
        ));
  }

  private static <T> int length(Sequence<T> seq) {
    int i = 0;
    Sequence<T> itr = seq;
    while (!itr.isEmpty()) {
      itr = itr.rest();
      i++;
    }
    return i;
  }
}
