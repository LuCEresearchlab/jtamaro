package jtamaro.data;

/**
 * A tuple of three elements.
 */
public record Triplet<A, B, C>(A first, B second, C third) {

  /**
   * Returns a triple with the first element being the one provided as an argument and the other two
   * from the triple this method is being invoked on.
   */
  public <T> Triplet<T, B, C> withFirst(T first) {
    return new Triplet<>(first, second, third);
  }

  /**
   * Returns a triple with the second element being the one provided as an argument and the other
   * two from the triple this method is being invoked on.
   */
  public <T> Triplet<A, T, C> withSecond(T second) {
    return new Triplet<>(first, second, third);
  }

  /**
   * Returns a triple with the third element being the one provided as an argument and the other two
   * from the triple this method is being invoked on.
   */
  public <T> Triplet<A, B, T> withThird(T third) {
    return new Triplet<>(first, second, third);
  }
}
