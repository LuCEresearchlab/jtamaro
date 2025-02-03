package jtamaro.example.sequence;

import static jtamaro.data.Sequences.rangeClosed;
import static jtamaro.io.IO.println;

public class ChessPositions {

  public static void main(String[] args) {
    println(rangeClosed('a', 'f').crossProduct(rangeClosed(1, 8))
        .map(fileAndRank -> fileAndRank + "" + fileAndRank.second()));
  }
}
