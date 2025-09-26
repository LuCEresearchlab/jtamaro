package jtamaro.optics;

import jtamaro.data.Either;
import jtamaro.data.Eithers;
import jtamaro.data.Function1;
import jtamaro.data.Function2;
import jtamaro.data.Option;

/**
 * A Prism generalises the notion of a constructor (just as a {@link Lens} generalises the notion of
 * a field).
 *
 * @param <S> Source of the prism
 * @param <T> Modified source of the prism
 * @param <A> Field of the prism
 * @param <B> Modified field of the prism
 */
public interface Prism<S, T, A, B> extends AffineTraversal<S, T, A, B>, Review<T, B> {

  @Override
  default <R> R foldMap(R neutralElement, Function2<R, R, R> reducer, Function1<A, R> map, S source) {
    return getOrModify(source).fold(_ -> neutralElement, map);
  }

  @Override
  default T over(Function1<A, B> lift, S source) {
    return getOrModify(source).fold(Function1.identity(), r -> review(lift.apply(r)));
  }

  @Override
  default T set(B value, S source) {
    return over(_ -> value, source);
  }

  /**
   * Combine with another Prism.
   */
  default <A2, B2> Prism<S, T, A2, B2> then(Prism<A, B, A2, B2> other) {
    return new Prism<>() {
      @Override
      public Either<T, A2> getOrModify(S source) {
        return Prism.this.getOrModify(source).fold(
            Eithers::left,
            a -> other.getOrModify(a).fold(
                b -> Eithers.left(Prism.this.set(b, source)),
                Eithers::right
            )
        );
      }

      @Override
      public Option<A2> preview(S source) {
        return Prism.this.preview(source).flatMap(other::preview);
      }

      @Override
      public T review(B2 value) {
        return Prism.this.review(other.review(value));
      }
    };
  }
}
