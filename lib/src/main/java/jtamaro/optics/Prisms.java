package jtamaro.optics;

import jtamaro.data.Either;
import jtamaro.data.Eithers;
import jtamaro.data.Option;
import jtamaro.data.Options;

/**
 * Procedures for creating Prism instances for classes of the {@link jtamaro.data} package.
 */
public final class Prisms {

  private Prisms() {
  }

  /**
   * Prism that decomposes an Either on its Left.
   *
   * @see Either
   */
  public static <A, B, R> Prism<Either<A, R>, Either<B, R>, A, B> left() {
    return new Prism<>() {
      @Override
      public Either<Either<B, R>, A> getOrModify(Either<A, R> source) {
        return source.fold(Eithers::right, r -> Eithers.left(Eithers.right(r)));
      }

      @Override
      public Option<A> preview(Either<A, R> source) {
        return source.fold(Options::some, r -> Options.none());
      }

      @Override
      public Either<B, R> review(B value) {
        return Eithers.left(value);
      }
    };
  }

  /**
   * Prism that decomposes an Either on its Right.
   *
   * @see Either
   */
  public static <A, B, L> Prism<Either<L, A>, Either<L, B>, A, B> right() {
    return new Prism<>() {
      @Override
      public Either<Either<L, B>, A> getOrModify(Either<L, A> source) {
        return source.fold(l -> Eithers.left(Eithers.left(l)), Eithers::right);
      }

      @Override
      public Option<A> preview(Either<L, A> source) {
        return source.fold(l -> Options.none(), Options::some);
      }

      @Override
      public Either<L, B> review(B value) {
        return Eithers.right(value);
      }
    };
  }

  /**
   * Prism that decomposes an Option on its Some.
   *
   * @see Option
   */
  public static <A, B> Prism<Option<A>, Option<B>, A, B> some() {
    return new Prism<>() {
      @Override
      public Either<Option<B>, A> getOrModify(Option<A> source) {
        return source.fold(Eithers::right, () -> Eithers.left(Options.none()));
      }

      @Override
      public Option<A> preview(Option<A> source) {
        return source;
      }

      @Override
      public Option<B> review(B value) {
        return Options.some(value);
      }
    };
  }
}
