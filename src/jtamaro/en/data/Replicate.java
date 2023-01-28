package jtamaro.en.data;

import jtamaro.en.Sequence;

public class Replicate<T> extends Sequence<T> {
      
  private final T element;
  private final int count;

  public Replicate(T element, int count) {
    this.element = element;
    this.count = count;
  }

  public boolean isCons() {
    return count > 0;
  }

  public boolean isEmpty() {
    return !isCons();
  }

  public T first() {
    return element;
  }

  public Replicate<T> rest() {
    return new Replicate<T>(element, count - 1);
  }

}
