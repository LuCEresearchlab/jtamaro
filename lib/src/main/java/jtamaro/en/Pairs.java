package jtamaro.en;

/**
 * Static methods for working with pairs.
 * 
 * @see jtamaro.en.Pair
 */
public final class Pairs {

  // prevent instantiation
  private Pairs() {
  }

  /**
   * Construct a new Pair given two elements.
   * 
   * @param <F> The type of the first element
   * @param <S> The type of the second element
   * @param first The value of the first element
   * @param second The value of the second element
   * @return A new Pair containing the two elements.
   */
  public static <F, S> Pair<F, S> pair(F first, S second) {
    return new Pair<>(first, second);
  }

  /**
   * Get the first element of the given Pair.
   * 
   * @param <F> The type of the first element of the Pair
   * @param <S> The type of the second element of the Pair
   * @param pair The Pair to "deconstruct" (to extract the first element from)
   * @return The first element of the given Pair.
   */
  public static <F, S> F firstElement(Pair<F, S> pair) {
    return pair.first();
  }

  /**
   * Get the second element of the given Pair.
   * 
   * @param <F> The type of the first element of the Pair
   * @param <S> The type of the second element of the Pair
   * @param pair The Pair to "deconstruct" (to extract the second element from)
   * @return The second element of the given Pair.
   */
  public static <F, S> S secondElement(Pair<F, S> pair) {
    return pair.second();
  }

}
