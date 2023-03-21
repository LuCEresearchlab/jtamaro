package jtamaro.en;

public class Pairs {

  public static <F,S> Pair<F,S> pair(F first, S second) {
    return new Pair<>(first, second);
  }

  public static <F,S> F firstElement(Pair<F,S> pair) {
    return pair.first();
  }

  public static <F,S> S secondElement(Pair<F,S> pair) {
    return pair.second();
  }
  
}
