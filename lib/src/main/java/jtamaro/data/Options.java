package jtamaro.data;

/**
 * Static methods for working with options.
 *
 * @see jtamaro.data.Option
 */
public final class Options {

  private Options() {
  }

  /**
   * Returns an empty {@link Option}.
   */
  public static <T> Option<T> none() {
    return new None<>();
  }

  /**
   * Returns an {@link Option} containing the given value.
   */
  public static <T> Option<T> some(T value) {
    return new Some<>(value);
  }

}
