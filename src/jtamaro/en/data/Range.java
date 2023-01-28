package jtamaro.en.data;

import jtamaro.en.Sequence;

public class Range extends Sequence<Integer> {

  private final int from;
  private final int toExclusive;
  private final int step;

  public Range(int from, int toExclusive, int step) {
    this.from = from;
    this.toExclusive = toExclusive;
    this.step = step;
  }

  public boolean isCons() {
    return from < toExclusive;
  }

  public boolean isEmpty() {
    return !isCons();
  }

  public Integer first() {
    return from;
  }

  public Range rest() {
    return new Range(from + step, toExclusive, step);
  }

}
