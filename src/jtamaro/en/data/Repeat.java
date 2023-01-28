package jtamaro.en.data;

import jtamaro.en.Sequence;

public class Repeat<T> extends Sequence<T> {

  private final T element;

  public Repeat(T element) {
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

  public Repeat<T> rest() {
    return this;
  }

}
