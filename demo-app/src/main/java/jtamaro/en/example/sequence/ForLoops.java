package jtamaro.en.example.sequence;

import static jtamaro.en.Sequences.*;


public class ForLoops {

  public static void main(String[] args) {
    for (int i : range(5)) {
      System.out.print(i);
    }
    System.out.println();

    for (char c : rangeClosed('A', 'F')) {
      System.out.print(c);
    }
    System.out.println();

    for (String s : of("Hi", "Ho")) {
      System.out.println(s + "!");
    }

    for (String s : replicate("-", 10)) {
      System.out.print(s);
    }
    System.out.println();
  }

}
