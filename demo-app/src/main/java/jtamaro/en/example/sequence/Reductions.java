package jtamaro.en.example.sequence;

import static jtamaro.en.Sequences.*;


public class Reductions {

  public static void main(String[] args) {
    System.out.println(reduce("", (e, a) -> e + a, of("a", "b", "c", "d")));
    System.out.println(foldRight("", (e, a) -> e + a, of("a", "b", "c", "d")));
    System.out.println(foldLeft("", (a, e) -> a + e, of("a", "b", "c", "d")));
  }

}
