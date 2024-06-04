package jtamaro.example.sequence;

import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.rangeClosed;
import static jtamaro.data.Sequences.replicate;
import static jtamaro.io.IO.print;
import static jtamaro.io.IO.println;

public class ForLoops {

  public static void main(String[] args) {
    for (int i : range(5)) {
      print(i);
    }
    println();

    for (char c : rangeClosed('A', 'F')) {
      print(c);
    }
    println();

    for (String s : of("Hi", "Ho")) {
      println(s + "!");
    }

    for (String s : replicate("-", 10)) {
      print(s);
    }
    println();
  }

}
