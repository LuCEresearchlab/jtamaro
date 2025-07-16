package jtamaro.optics;

import jtamaro.data.Function1;
import jtamaro.data.Function2;

/**
 * A Fold has the ability to extract some number of elements of type <code>A</code> from a container
 * of type <code>S</code>. For example, toListOf can be used to obtain the contained elements as a
 * list.
 *
 * <p>Unlike a {@link Traversal}, there is no way to set or update elements.
 *
 * @param <S> Container source of the traversal
 * @param <A> Target of the traversal
 */
public interface Fold<S, A> {


  /**
   * Map each target to <code>R</code> and fold the results.
   */
  <R> R foldMap(R neutralElement, Function2<R, R, R> reducer, Function1<A, R> map, S source);

  /**
   * Fold the targets.
   */
  default A fold(A neutralElement, Function2<A, A, A> reducer, S source) {
    return foldMap(neutralElement, reducer, Function1.identity(), source);
  }

  /**
   * Combine with another Focus.
   */
  default <U> Fold<S, U> then(Fold<A, U> other) {
    return new Fold<>() {
      @Override
      public <R> R foldMap(R neutralElement, Function2<R, R, R> reducer, Function1<U, R> map, S source) {
        return Fold.this.foldMap(neutralElement, reducer,
            a -> other.foldMap(neutralElement, reducer, map, a), source);
      }
    };
  }
}
