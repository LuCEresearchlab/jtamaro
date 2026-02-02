package jtamaro.io;

import java.util.Scanner;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.graphic.Color;
import jtamaro.graphic.Colors;
import jtamaro.graphic.Graphic;

/**
 * Procedures to perform I/O operations on standard IO streams ({@code STDIN}, {@code STDOUT} and
 * {@code STDERR}).
 *
 * <p>Note: all methods in this class are <b>impure</b>!
 */
public final class StandardIO {

  private StandardIO() {
  }

  /* **** CLI **** */

  /**
   * Print the given pair, one element right after the next, to the standard output. Each element is
   * converted into a String to be printed. This does not add any newlines.
   *
   * @param <F>  the type of the first element
   * @param <S>  the type of the second element
   * @param pair the pair to print
   */
  public static <F, S> void print(Pair<F, S> pair) {
    if (pair == null) {
      System.err.print("Nothing to print");
    } else {
      System.out.print(pair.first());
      System.out.print(pair.second());
    }
  }

  /**
   * Print the given sequence, one element right after the next, to standard output. Each element is
   * converted into a String to be printed. This does not add any newlines.
   *
   * @param <T>      the type of elements
   * @param sequence the sequence to print
   */
  public static <T> void print(Sequence<T> sequence) {
    if (sequence == null) {
      System.err.print("Nothing to print");
    } else {
      for (T element : sequence) {
        System.out.print(element);
      }
    }
  }

  /**
   * Prints an object.
   *
   * @see java.io.PrintStream#print(Object)
   */
  public static void print(Object object) {
    if (object == null) {
      System.err.print("Nothing to print");
    } else {
      System.out.print(object);
    }
  }

  /**
   * Print a colored textual representation of the given color and terminate the line.
   *
   * @param color the color to print
   * @see java.io.PrintStream#println(Object)
   * @see Graphic#dump()
   */
  public static void println(Color color) {
    if (color == null) {
      System.err.println("Nothing to print");
    } else {
      System.out.println(Colors.formatAnsiEscape(color));
    }
  }

  /**
   * Print a tree-like visualization of the given graphic and terminate the line.
   *
   * @param graphic the graphic to print
   * @see java.io.PrintStream#println(Object)
   * @see Graphic#dump()
   */
  public static void println(Graphic graphic) {
    if (graphic == null) {
      System.err.println("Nothing to print");
    } else {
      System.out.println(graphic.dump());
    }
  }

  /**
   * Print the given pair, one element per line, to the standard output. Each element is converted
   * into a String to be printed.
   *
   * @param <F>  the type of the first element
   * @param <S>  the type of the second element
   * @param pair the pair to print
   */
  public static <F, S> void println(Pair<F, S> pair) {
    if (pair == null) {
      System.err.println("Nothing to print");
    } else {
      System.out.println(pair.first());
      System.out.println(pair.second());
    }
  }

  /**
   * Print the given sequence, one element per line, to standard output. Each element is converted
   * into a String to be printed.
   *
   * @param <T>      the type of elements
   * @param sequence the sequence to print
   */
  public static <T> void println(Sequence<T> sequence) {
    if (sequence == null) {
      System.err.println("Nothing to print");
    } else {
      for (T element : sequence) {
        System.out.println(element);
      }
    }
  }

  /**
   * Prints an object and terminate the line.
   *
   * @see java.io.PrintStream#println(Object)
   */
  public static void println(Object object) {
    System.out.println(object);
  }

  /**
   * Terminate the line.
   *
   * @see java.io.PrintStream#println()
   */
  public static void println() {
    System.out.println();
  }

  /**
   * Read a line from standard input.
   */
  public static String readLine() {
    return new Scanner(System.in).nextLine();
  }

  /* **** To-Do **** */

  /**
   * To&nbsp;Do placeholder method that shows an error message in STDERR and returns null.
   */
  public static <T> T todo() {
    return todo("implement");
  }

  /**
   * To&nbsp;Do placeholder method that shows an error message in STDERR and returns null.
   */
  public static <T> T todo(String message) {
    try {
      throw new Exception();
    } catch (Exception e) {
      final StackTraceElement cause = e.getStackTrace()[1];
      System.err.printf("TODO: %1$s in file %2$s at line %3$d%n",
          message,
          cause.getFileName(),
          cause.getLineNumber());
    }
    return null;
  }
}
