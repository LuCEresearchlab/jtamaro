package jtamaro.en.data;

import jtamaro.en.Function1;
import jtamaro.en.Option;

public record None<T>() implements Option<T> {

  @Override
  public <S> S fold(Function1<T, S> someCase, S noneValue) {
    return noneValue;
  }

  @Override
  public <S> Option<S> flatMap(Function1<T, Option<S>> f) {
    return new None<>();
  }

  @Override
  public <S> Option<S> map(Function1<T, S> f) {
    return new None<>();
  }
}
