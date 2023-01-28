package jtamaro.en.data;

import java.util.function.Function;

import jtamaro.en.Sequence;


public class Map<T,U> extends Sequence<U> {

  private final Function<T,U> f;
  private final Sequence<T> sequence;
  
  public Map(Function<T,U> f, Sequence<T> sequence) {
    this.f = f;
    this.sequence = sequence;
  }

  public boolean isCons() {
    return sequence.isCons();
  }

  public boolean isEmpty() {
    return !isCons();
  }

  public U first() {
    return f.apply(sequence.first());
  }

  public Map<T,U> rest() {
    return new Map<T,U>(f, sequence.rest());
  }

}
