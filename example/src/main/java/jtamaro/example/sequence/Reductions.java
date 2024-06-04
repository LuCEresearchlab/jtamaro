package jtamaro.example.sequence;

import static jtamaro.data.Sequences.foldLeft;
import static jtamaro.data.Sequences.foldRight;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.reduce;
import static jtamaro.io.IO.println;

public class Reductions {

  public static void main(String[] args) {
    println(reduce("", (e, a) -> e + a, of("a", "b", "c", "d")));
    println(foldRight("", (e, a) -> e + a, of("a", "b", "c", "d")));
    println(foldLeft("", (a, e) -> a + e, of("a", "b", "c", "d")));
  }

}
