package jtamaro.en.data;

import jtamaro.en.Function1;
import jtamaro.en.Option;

public record Some<T>(T value) implements Option<T> {

  @Override
  public <S> Option<S> flatMap(Function1<T, Option<S>> f) {
    return f.apply(value);
  }

  @Override
  public <S> S fold(Function1<T, S> someCase, S noneValue) {
    return someCase.apply(value);
  }

  @Override
  public <S> Option<S> map(Function1<T, S> f) {
    return new Some<>(f.apply(value));
  }
}
