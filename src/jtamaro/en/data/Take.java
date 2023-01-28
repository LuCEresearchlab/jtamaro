package jtamaro.en.data;

import jtamaro.en.Sequence;

public class Take<T> extends Sequence<T> {

  private final int n;
  private final Sequence<T> sequence;

  public Take(int n, Sequence<T> sequence) {
    this.n = n;
    this.sequence = sequence;
  }

  public boolean isCons() {
    return n > 0 && sequence.isCons();
  }

  public boolean isEmpty() {
    return !isCons();
  }

  public T first() {
    return sequence.first();
  }

  public Take<T> rest() {
    return new Take<T>(n - 1, sequence.rest());
  }

}
