package jtamaro.example.sequence;

import static jtamaro.data.Sequences.of;
import static jtamaro.example.Toolbelt.concats;
import static jtamaro.io.IO.println;

public class Concats {

  public static void main(String[] args) {
    println(concats(of(
        of(1, 2, 3),
        of(4, 5),
        of(6, 7, 8)
    )));
  }
}
