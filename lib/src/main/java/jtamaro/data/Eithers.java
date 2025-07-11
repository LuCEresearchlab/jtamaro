package jtamaro.data;

/**
 * Static methods for working with either instances.
 *
 * @see jtamaro.data.Either
 */
public final class Eithers {

  public static <L, R> Either<L, R> left(L value) {
    return new Left<>(value);
  }

  public static <L, R> Either<L, R> right(R value) {
    return new Right<>(value);
  }

  private Eithers() {
  }
}
