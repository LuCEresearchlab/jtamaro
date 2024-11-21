package jtamaro.data;

/**
 * Function object that takes one argument.
 *
 * @param <A> Type of the argument of the function.
 * @param <R> Type of the value returned by the function.
 */
public interface Function1<A, R> {

  R apply(A a);

  /**
   * Returns a function that always returns its input argument.
   *
   * @param <T> the type of both the input and output of the function
   */
  static <T> Function1<T, T> identity() {
    return t -> t;
  }
}
