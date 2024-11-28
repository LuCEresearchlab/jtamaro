package jtamaro.data;

/**
 * A tuple of two elements.
 */
public record Pair<A, B>(A first, B second) {

  /**
   * Returns a pair with the first element being the one provided as an argument and the second the
   * one of the pair this method is being invoked on.
   */
  public <T> Pair<T, B> withFirst(T first) {
    return new Pair<>(first, second);
  }

  /**
   * Returns a pair with the second element being the one provided as an argument and the first the
   * one of the pair this method is being invoked on.
   */
  public <T> Pair<A, T> withSecond(T second) {
    return new Pair<>(first, second);
  }
}
