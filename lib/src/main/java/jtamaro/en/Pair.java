package jtamaro.en;

/**
 * A Pair (or two-tuple) containing two elements.
 * 
 * <p>To work with a Pair, you can use the methods in the Pairs class.
 * 
 * @see jtamaro.en.Pairs
 */
public record Pair<T, U>(T first, U second) {
}
