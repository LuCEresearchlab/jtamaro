package jtamaro.data;

/**
 * A tuple of two elements.
 */
public record Pair<A, B>(A first, B second) {

  public <T> Pair<T, B> withFirst(T first) {
    return new Pair<>(first, second);
  }

  public <T> Pair<A, T> withSecond(T second) {
    return new Pair<>(first, second);
  }
}
