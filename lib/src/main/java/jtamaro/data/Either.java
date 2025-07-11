package jtamaro.data;

import java.util.stream.Stream;

/**
 * The Either type represents values with two possibilities.
 *
 * <p>The Either type is sometimes used to represent a value which is either correct or an error;
 * by convention, the <i>left</i> is used to hold an error value and the <i>right</i> is used to
 * hold a correct value (mnemonic: "right" also means "correct").
 */
public sealed interface Either<L, R> permits Left, Right {

  /**
   * Apply a function to this left either.
   *
   * @return Returns the result of applying the given function to the value of this option if this
   * either is a left.
   */
  <T> Either<T, R> flatMapLeft(Function1<L, Either<T, R>> f);

  /**
   * Apply a function to this right either.
   *
   * @return Returns the result of applying the given function to the value of this option if this
   * either is a right.
   */
  <T> Either<L, T> flatMapRight(Function1<R, Either<L, T>> f);

  /**
   * Apply a function to this left either.
   *
   * @return Returns an either containing the result of applying f to this option's value if this
   * either is a left.
   */
  <T> Either<T, R> mapLeft(Function1<L, T> f);

  /**
   * Apply a function to this right either.
   *
   * @return Returns an either containing the result of applying f to this option's value if this
   * either is a right.
   */
  <T> Either<L, T> mapRight(Function1<R, T> f);

  /**
   * Obtain a value depending on whether this either is left or right.
   *
   * @return Returns the result of applying the leftCase function to the value of this either if
   * this either is left or rightCase if this either is right.
   */
  <T> T fold(Function1<L, T> leftCase, Function1<R, T> rightCase);

  /**
   * Produce a stream.
   *
   * @return An empty stream if this either is right or a stream with a single value if this either
   * is left.
   */
  Stream<L> streamLeft();

  /**
   * Produce a stream.
   *
   * @return An empty stream if this either is left or a stream with a single value if this either
   * is right.
   */
  Stream<R> streamRight();

  /**
   * Determine whether this either is right.
   */
  default boolean isRight() {
    return fold($ -> false, $ -> true);
  }
}
