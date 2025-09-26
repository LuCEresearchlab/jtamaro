package jtamaro.optics;

import jtamaro.data.Either;
import jtamaro.data.Eithers;
import jtamaro.data.Function1;
import jtamaro.data.Function2;

/**
 * A Lens is a generalised or first-class field.
 *
 * <p>It combines a {@link Getter} and a {@link Setter}.
 *
 * @param <S> Source of the lens
 * @param <T> Modified source of the lens
 * @param <A> Field of the lens
 * @param <B> Modified field of the lens
 */
public interface Lens<S, T, A, B> extends AffineTraversal<S, T, A, B>, Getter<S, A> {

  @Override
  T over(Function1<A, B> lift, S source); // Remove inherited default implementation

  @Override
  default T set(B value, S source) {
    return over(_ -> value, source);
  }

  @Override
  default Either<T, A> getOrModify(S source) {
    return Eithers.right(view(source));
  }

  @Override
  default <R> R foldMap(R neutralElement, Function2<R, R, R> reducer, Function1<A, R> map, S source) {
    return getOrModify(source).fold(_ -> neutralElement, map);
  }

  /**
   * Combine with another Lens.
   */
  default <A2, B2> Lens<S, T, A2, B2> then(Lens<A, B, A2, B2> other) {
    return new Lens<>() {
      @Override
      public A2 view(S source) {
        return other.view(Lens.this.view(source));
      }

      @Override
      public T over(Function1<A2, B2> lift, S source) {
        return Lens.this.over(a -> other.over(lift, a), source);
      }
    };
  }
}
