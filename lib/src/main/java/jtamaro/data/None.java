package jtamaro.data;

import java.util.function.Consumer;

record None<T>() implements Option<T> {

  @Override
  public <S> Option<S> flatMap(Function1<T, Option<S>> f) {
    return new None<>();
  }

  @Override
  public <S> Option<S> map(Function1<T, S> f) {
    return new None<>();
  }

  @Override
  public <S> S fold(Function1<T, S> someCase, Function0<S> noneCase) {
    return noneCase.apply();
  }

  @Override
  public void forEach(Consumer<T> someCase, Runnable noneCase) {
    noneCase.run();
  }
}
