package jtamaro.data;

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
}
