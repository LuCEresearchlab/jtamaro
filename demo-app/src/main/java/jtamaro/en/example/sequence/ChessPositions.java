package jtamaro.en.example.sequence;

import static jtamaro.en.IO.*;
import static jtamaro.en.Pairs.*;
import static jtamaro.en.Sequences.*;


public class ChessPositions {

  public static void main(String[] args) {
    println(
      map(
        fileAndRank -> firstElement(fileAndRank) + "" + secondElement(fileAndRank),
        crossProduct(rangeClosed('a', 'f'), rangeClosed(1, 8))
      )
    );
  }

}
