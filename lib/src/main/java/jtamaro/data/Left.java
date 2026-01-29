package jtamaro.data;

import java.util.stream.Stream;

record Left<L, R>(L value) implements Either<L, R> {

  @Override
  public <T> T fold(Function1<L, T> leftCase, Function1<R, T> rightCase) {
    return leftCase.apply(value);
  }

  @Override
  public <T> Either<T, R> mapLeft(Function1<L, T> f) {
    return new Left<>(f.apply(value));
  }

  @Override
  public <T> Either<L, T> mapRight(Function1<R, T> f) {
    return new Left<>(value);
  }

  @Override
  public <T> Either<T, R> flatMapLeft(Function1<L, Either<T, R>> f) {
    return f.apply(value);
  }

  @Override
  public <T> Either<L, T> flatMapRight(Function1<R, Either<L, T>> f) {
    return new Left<>(value);
  }

  @Override
  public Option<L> optionLeft() {
    return new Some<>(value);
  }

  @Override
  public Option<R> optionRight() {
    return new None<>();
  }

  @Override
  public Stream<L> streamLeft() {
    return Stream.of(value);
  }

  @Override
  public Stream<R> streamRight() {
    return Stream.empty();
  }
}
