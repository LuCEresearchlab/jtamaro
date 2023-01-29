package jtamaro.en.data;

import jtamaro.en.Sequence;

public class Empty<T> extends Sequence<T> {

  public T first() {
    throw new RuntimeException("Empty sequence has no first element");
  }

  public Sequence<T> rest() {
    throw new RuntimeException("Empty sequence has no rest");
  }

  public boolean isEmpty() {
    return true;
  }

  public boolean hasDefiniteSize() {
    return true;
  }

}
