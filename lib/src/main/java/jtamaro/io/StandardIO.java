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
   * Prints a textual representation of a given object.
   *
   * @see java.io.PrintStream#print(Object)
   */
  public static void print(Object object) {
    switch (object) {
      case Color color -> System.out.print(Colors.formatAnsiEscape(color));
      case Graphic graphic -> System.out.print(graphic.dump());
      case Pair(Object first, Object second) -> {
        print(first);
        print(second);
      }
      case Sequence<?> seq -> {
        for (Object element : seq) {
          print(element);
        }
      }
      case null -> System.err.print("Nothing to print");
      default -> System.out.print(object);
    }
  }

  /**
   * Prints an object and terminate the line.
   *
   * @see java.io.PrintStream#println(Object)
   */
  public static void println(Object object) {
    switch (object) {
      case Color color -> System.out.println(Colors.formatAnsiEscape(color));
      case Graphic graphic -> System.out.println(graphic.dump());
      case Pair(Object first, Object second) -> {
        println(first);
        println(second);
      }
      case Sequence<?> sequence -> {
        for (Object element : sequence) {
          println(element);
        }
      }
      case null -> System.err.println("Nothing to print");
      default -> System.out.println(object);
    }
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
