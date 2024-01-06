package jtamaro.en.example.sequence;

import static jtamaro.en.IO.print;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.example.Toolbelt.concats;

public class Concats {
  public static void main(String[] args) {
    print(concats(of(
      of(1, 2, 3),
      of(4, 5),
      of(6, 7, 8)
    )));
  }
}
