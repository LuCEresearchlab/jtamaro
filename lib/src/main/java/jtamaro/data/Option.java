package jtamaro.data;

import java.util.stream.Stream;

/**
 * Option type is used to handle errors.
 */
public sealed interface Option<T> permits Some, None {

  /**
   * Apply a function to this option.
   *
   * @return Returns the result of applying the given function to the value of this option if this
   * option is non-empty.
   */
  <S> Option<S> flatMap(Function1<T, Option<S>> f);

  /**
   * Apply a function to this option.
   *
   * @return Returns an option containing the result of applying f to this option's value if this
   * option is non-empty.
   */
  <S> Option<S> map(Function1<T, S> f);

  /**
   * Obtain a value depending on whether this option is empty or not.
   *
   * @return Returns the result of applying the someCase function to the value of this option if
   * this option is non-empty or noneCase if this option is empty.
   */
  <S> S fold(Function1<T, S> someCase, Function0<S> noneCase);

  /**
   * Determine whether this option is empty.
   */
  default boolean isEmpty() {
    return fold(_ -> false, () -> true);
  }

  /**
   * Produce a stream.
   *
   * @return An empty stream if this option is empty or a stream with a single value if this option
   * is non-empty.
   */
  default Stream<T> stream() {
    return fold(Stream::of, Stream::empty);
  }
}
