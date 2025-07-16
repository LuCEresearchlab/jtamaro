package jtamaro.optics;

/**
 * A Review is a backwards {@link Getter}, i.e. a function from <code>B</code> to <code>T</code>.
 *
 * @param <B> Source of the review
 * @param <T> Target of the review
 */
@FunctionalInterface
public interface Review<T, B> {

  /*
   * Note: the lack of a Review#then(Review) method is on purpose.
   * Since Review is a FunctionalInterface, this method becomes potentially
   * ambiguous with AffineFold#focus(AffineFold) in Prism for the compiler
   * (both are functional interfaces with 1 parameter and here the type of that
   * parameter is generic).
   * We chose to forsake the then of Review because it's less likely going to
   * be used than the then of AffineFold. If needed it can be easily implemented
   * by the user.
   */

  /**
   * Retrieve the value targeted by a Review.
   */
  T review(B value);
}
