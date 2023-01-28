package jtamaro.en.data;

import java.util.function.Function;

import jtamaro.en.Sequence;

public class Iterate<T> extends Sequence<T> {
    
  private final Function<T, T> function;
  private final T element;

  public Iterate(Function<T, T> function, T element) {
    this.function = function;
    this.element = element;
  }

  public boolean isCons() {
    return true;
  }

  public boolean isEmpty() {
    return false;
  }

  public T first() {
    return element;
  }

  public Iterate<T> rest() {
    return new Iterate<T>(function, function.apply(element));
  }

}
