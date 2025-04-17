package jtamaro.data;

import java.util.function.Consumer;

record Some<T>(T value) implements Option<T> {

  @Override
  public <S> Option<S> flatMap(Function1<T, Option<S>> f) {
    return f.apply(value);
  }

  @Override
  public <S> Option<S> map(Function1<T, S> f) {
    return new Some<>(f.apply(value));
  }

  @Override
  public <S> S fold(Function1<T, S> someCase, Function0<S> noneCase) {
    return someCase.apply(value);
  }

  @Override
  public void forEach(Consumer<T> someCase, Runnable noneCase) {
    someCase.accept(value);
  }
}
