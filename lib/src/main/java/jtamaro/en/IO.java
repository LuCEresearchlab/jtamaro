package jtamaro.en;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Scanner;
import jtamaro.en.io.GifWriter;
import jtamaro.internal.gui.GraphicFrame;
import jtamaro.en.graphic.AbstractGraphic;
import jtamaro.en.io.ColorFrame;
import jtamaro.en.io.FilmStripFrame;
import jtamaro.en.io.Interaction;
import jtamaro.en.music.AbsoluteChord;
import jtamaro.en.music.Instrument;
import jtamaro.en.music.Note;
import jtamaro.en.music.TimedChord;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.swing.*;
import java.io.IOException;
import jtamaro.internal.io.PngWriter;

import static jtamaro.en.Pairs.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Music.*;


/**
 * This class includes methods to perform input and output for JTamaro classes.
 * It allows outputting JTamaro graphics, colors, points, sequences, and pairs.
 *
 * <p>It provides methods for textual output, specifically, to easily print
 * sequences and pairs to the standard output.
 *
 * <p>More importantly, it provides methods for graphical IO,
 * specifically to: show graphics, to animate sequences of graphics, and
 * to produce interactions.
 *
 * <p>It also provides methods to save graphics as PNG files and
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
   * @param <T>      the type of elements
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
   * @param <T>      the type of elements
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
   * @param <F>  the type of the first element
   * @param <S>  the type of the second element
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
   * @param <F>  the type of the first element
   * @param <S>  the type of the second element
   * @param pair the pair to print
   */
  public static <F, S> void println(Pair<F, S> pair) {
    System.out.println(pair.first());
    System.out.println(pair.second());
  }

  /**
   * Read a line from standard input.
   */
  public static String readLine() {
    return new Scanner(System.in).nextLine();
  }

  /**
   * To&nbsp;Do placeholder method that shows an error message
   * in STDERR and returns null;
   */
  public static <T> T todo() {
    return todo("implement");
  }

  /**
   * To&nbsp;Do placeholder method that shows an error message
   * in STDERR and returns null;
   */
  public static <T> T todo(String message) {
    try {
      throw new Exception();
    } catch (Exception e) {
      final StackTraceElement cause = e.getStackTrace()[1];
      final String logMessage = String.format("TODO: %1$s in file %2$s at line %3$02d",
          message, cause.getFileName(), cause.getLineNumber());
      System.err.println(logMessage);
    }
    return null;
  }

  /**
   * Read the contents of a file as a string.
   */
  public static String readFile(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Writes the contents of a given string to a file.
   */
  public static void writeFile(Path path, String content) {
    try {
      Files.writeString(path, content, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Writes the contents of a given byte array to a file.
   */
  public static void writeFile(Path path, byte[] content) {
    try {
      Files.write(path, content, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
    } catch (IOException e) {
      throw new RuntimeException(e);
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
      return;
    }

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
    if (graphic == null) {
      System.err.println("Nothing to show");
      return;
    }

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

  private static Pair<Integer, Integer> determineMaxWidthHeight(Sequence<Graphic> graphics) {
    int frameWidth;
    int frameHeight;
    if (isEmpty(graphics)) {
      // no frames: use a default
      frameWidth = 400;
      frameHeight = 300;
    } else if (graphics.hasDefiniteSize()) {
      // known, finite number of frames: determine max
      frameWidth = reduce(0, (e, a) -> Math.max(a, (int) Math.ceil(width(e))), graphics);
      frameHeight = reduce(0, (e, a) -> Math.max(a, (int) Math.ceil(height(e))), graphics);
    } else {
      // unknown number of frames, potentially infinite: use size of first frame
      frameWidth = (int) Math.ceil(width(first(graphics)));
      frameHeight = (int) Math.ceil(height(first(graphics)));
    }
    return pair(frameWidth, frameHeight);
  }

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
   * Open a window with a looped animation of the given graphics.
   *
   * @param graphics             sequence of graphics (frames) to animate
   * @param millisecondsPerFrame delay between frames
   */
  public static void animate(Sequence<Graphic> graphics, int millisecondsPerFrame) {
    animate(graphics, true, millisecondsPerFrame);
  }

  /**
   * Open a window with an animation of the given graphics,
   * at 25 frames per second.
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
    final Pair<Integer, Integer> wh = determineMaxWidthHeight(graphics);
    final String titleFramesSuffix = graphics.hasDefiniteSize() ? " (" + length(graphics) + " frames)" : "";
    final String titleLoopedSuffix = loop ? ", looped" : "";
    new Interaction<>(graphics)
        .withName("Animation" + titleFramesSuffix + titleLoopedSuffix)
        .withCanvasSize(firstElement(wh), secondElement(wh))
        .withMsBetweenTicks(millisecondsPerFrame)
        .withTickHandler(model -> {
          Sequence<Graphic> rest = model.rest();
          return rest.isEmpty() ? (loop ? graphics : rest) : rest;
        })
        .withRenderer(Sequence::first)
        .withStoppingPredicate(Sequence::isEmpty)
        .run();
  }

  private static <T> int length(Sequence<T> s) {
    return isEmpty(s) ? 0 : 1 + length(rest(s));
  }

  /**
   * Open a window with a scrollable filmstrip,
   * with each from for each of the given graphics.
   * This method automatically detects the width and height of the frames.
   * For empty sequences, it uses a default frame size.
   * For sequences with a definite size, it determines the maximum width and height.
   * For sequences without a definite size, it uses the width and height of their first frame.
   *
   * @param graphics sequence of graphics (frames) to show in the filmstrip
   */
  public static void showFilmStrip(Sequence<Graphic> graphics) {
    final Pair<Integer, Integer> wh = determineMaxWidthHeight(graphics);
    showFilmStrip(graphics, firstElement(wh), secondElement(wh));
  }

  /**
   * Open a window with a scrollable filmstrip,
   * with each from for each of the given graphics.
   *
   * @param graphics    sequence of graphics (frames) to show in the filmstrip
   * @param frameWidth  the width of frames (should be at least the maximum of all the graphics' widths)
   * @param frameHeight the height of frames (should be at least the maximum of all the graphics' heights)
   */
  public static void showFilmStrip(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    if (graphics == null) {
      System.err.println("Nothing to show");
      return;
    }

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
   * @param graphic  the Graphic to render
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
   * @param graphics             the finite sequence of graphics (frames) to save in the GIF
   * @param loop                 whether to loop the animation when playing
   * @param millisecondsPerFrame how many milliseconds to wait between each frame
   * @param filename             the name of the file (including the .gif extension)
   */
  public static void saveAnimatedGif(Sequence<Graphic> graphics, boolean loop, int millisecondsPerFrame, String filename) {
    assert !graphics.isEmpty() : "Animation must have at least one frame";
    assert graphics.hasDefiniteSize() : "The sequence of graphics must contain a finite number of frames";
    assert millisecondsPerFrame > 0 : "Must wait a positive number of milliseconds between frames";
    assert filename.endsWith(".gif") : "Filename must end with .gif";
    // TODO: maybe use determineMaxWidthHeight?
    try {
      GifWriter.saveAnimation(map(g -> ((AbstractGraphic) g).getImplementation(), graphics), millisecondsPerFrame, loop, filename);
    } catch (IOException ex) {
      System.err.println("Error saving animated GIF to " + filename + ": " + ex.getMessage());
    }
  }

  //--- Music
  public static void play(Sequence<TimedChord> song, int bpm) {
    play(song, bpm, 0, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void play(Sequence<TimedChord> song, int bpm, int channel, Instrument instrument) {
    System.out.println("IO.play:");
    int msPerBeat = 60 * 1000 / bpm;
    try {
      Receiver receiver = MidiSystem.getReceiver();
      receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument.internalPcNumber(), 0), -1);
      for (TimedChord c : cons(timed(2, chord(of())), song)) {
        System.out.println(c);
        c.play(receiver, channel, msPerBeat);
      }
    } catch (MidiUnavailableException | InvalidMidiDataException ex) {
      ex.printStackTrace();
    }
    try {
      System.out.println("waiting at end of play.");
      Thread.sleep(4 * msPerBeat);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  public static void playChords(Sequence<AbsoluteChord> chords) {
    playChords(chords, 120);
  }

  public static void playChords(Sequence<AbsoluteChord> chords, int bpm) {
    playChords(chords, bpm, 0, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void playDrumChords(Sequence<AbsoluteChord> chords, int bpm) {
    // MIDI channel 10 (internally channel 9) contains the drums
    playChords(chords, bpm, 9, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void playChords(Sequence<AbsoluteChord> chords, int bpm, int channel, Instrument instrument) {
    play(map(chord -> timed(1, chord), chords), bpm, channel, instrument);
  }

  public static void playNotes(Sequence<Note> notes) {
    playNotes(notes, 120);
  }

  public static void playNotes(Sequence<Note> notes, Instrument instrument) {
    playNotes(notes, 120, 0, instrument);
  }

  public static void playNotes(Sequence<Note> notes, int bpm) {
    playNotes(notes, bpm, 0, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void playDrumNotes(Sequence<Note> notes, int bpm) {
    // MIDI channel 10 (internally channel 9) contains the drums
    playNotes(notes, bpm, 9, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void playNotes(Sequence<Note> notes, int bpm, int channel, Instrument instrument) {
    play(map(n -> timed(1, chord(of(n))), notes), bpm, channel, instrument);
  }

}
