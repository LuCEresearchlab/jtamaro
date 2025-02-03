package jtamaro.example.sequence;

import static jtamaro.data.Sequences.of;
import static jtamaro.io.IO.println;

public class Reductions {

  public static void main(String[] args) {
    println(of("a", "b", "c", "d").reduce("", String::concat));
    println(of("a", "b", "c", "d").foldRight("", String::concat));
    println(of("a", "b", "c", "d").foldLeft("", String::concat));
  }
}
