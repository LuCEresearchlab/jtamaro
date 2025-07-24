package jtamaro.optics;

import jtamaro.data.Function1;
import jtamaro.data.Function2;
import jtamaro.data.Option;
import jtamaro.data.Options;

/**
 * An AffineFold is a {@link Fold} that contains at most one element, or a {@link Getter} where the
 * function may be partial.
 *
 * @param <S> Source of the traversal
 * @param <A> Target of the traversal
 */
@FunctionalInterface
public interface AffineFold<S, A> extends Fold<S, A> {

  /**
   * Retrieve the targeted value.
   */
  Option<A> preview(S source);

  @Override
  default <R> R foldMap(R neutralElement, Function2<R, R, R> reducer, Function1<A, R> map, S source) {
    return preview(source).fold(
        it -> reducer.apply(neutralElement, map.apply(it)),
        () -> neutralElement
    );
  }

  /**
   * Combine with another AffineFold.
   */
  default <U> AffineFold<S, U> then(AffineFold<A, U> other) {
    return source -> AffineFold.this.foldMap(Options.none(),
        (acc, it) -> acc.fold(Options::some, () -> it),
        other::preview,
        source);
  }
}
