package jtamaro.optics;

import jtamaro.data.Function1;
import jtamaro.data.Function2;

/**
 * A Traversal lifts an effectful operation on elements to act on structures containing those
 * elements.
 *
 * <p>A {@link Lens} is a Traversal that acts on a single value.</p>
 *
 * @param <S> Source of the traversal
 * @param <T> Modified source of the traversal
 * @param <A> Target of the traversal
 * @param <B> Modified target of the traversal
 */
public interface Traversal<S, T, A, B> extends Fold<S, A>, Setter<S, T, A, B> {

  /**
   * Combine with another Traversal.
   */
  default <A2, B2> Traversal<S, T, A2, B2> then(Traversal<A, B, A2, B2> other) {
    return new Traversal<>() {
      @Override
      public <R> R foldMap(R neutralElement, Function2<R, R, R> reducer, Function1<A2, R> map, S source) {
        return Traversal.this.foldMap(neutralElement, reducer,
            a -> other.foldMap(neutralElement, reducer, map, a), source);
      }

      @Override
      public T over(Function1<A2, B2> lift, S source) {
        return Traversal.this.over(a -> other.over(lift, a), source);
      }
    };
  }
}
