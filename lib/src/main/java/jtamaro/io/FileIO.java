package jtamaro.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import jtamaro.data.Either;
import jtamaro.data.Eithers;
import jtamaro.data.Option;
import jtamaro.data.Options;

/**
 * Procedures to perform file-related I/O operations.
 *
 * <p>Both safe and unsafe procedures are offered: unsafe versions may throw
 * {@link UncheckedIOException}, while safe versions properly model the erroneous state through the
 * return value types.
 */
public final class FileIO {

  private FileIO() {
  }

  /**
   * Read the contents of a file as a UTF-8 string.
   *
   * @return Either the content of the file (on the right) if the operation succeeded, or the
   * {@link IOException} (on the left) in case of failure.
   */
  public static Either<IOException, String> readFile(Path path) {
    try {
      return Eithers.right(readFileUnsafe(path));
    } catch (UncheckedIOException e) {
      return Eithers.left(e.getCause());
    }
  }

  /**
   * Writes the contents of a given string to a file.
   *
   * @return An empty {@link Option} if the operation succeeded, or an option containing the
   * {@link IOException} in case of failure.
   */
  public static Option<IOException> writeFile(Path path, String content) {
    try {
      writeFileUnsafe(path, content);
      return Options.none();
    } catch (UncheckedIOException e) {
      return Options.some(e.getCause());
    }
  }

  /**
   * Writes the contents of a given byte array to a file.
   *
   * @return An empty {@link Option} if the operation succeeded, or an option containing the
   * {@link IOException} in case of failure.
   */
  public static Option<IOException> writeFile(Path path, byte[] content) {
    try {
      writeFileUnsafe(path, content);
      return Options.none();
    } catch (UncheckedIOException e) {
      return Options.some(e.getCause());
    }
  }

  /**
   * Read the contents of a file as a UTF-8 string.
   *
   * @throws UncheckedIOException If the file cannot be read.
   */
  public static String readFileUnsafe(Path path) {
    try {
      return Files.readString(path, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Writes the contents of a given string to a file.
   *
   * @throws UncheckedIOException If the file cannot be written to.
   */
  public static void writeFileUnsafe(Path path, String content) {
    try {
      Files.writeString(path,
          content,
          StandardCharsets.UTF_8,
          StandardOpenOption.WRITE,
          StandardOpenOption.CREATE);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Writes the contents of a given byte array to a file.
   *
   * @throws UncheckedIOException If the file cannot be written to.
   */
  public static void writeFileUnsafe(Path path, byte[] content) {
    try {
      Files.write(path, content, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
