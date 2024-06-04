package jtamaro.example.sequence;

import static jtamaro.data.Sequences.crossProduct;
import static jtamaro.data.Sequences.map;
import static jtamaro.data.Sequences.rangeClosed;
import static jtamaro.io.IO.println;

public class ChessPositions {

  public static void main(String[] args) {
    println(
        map(
            fileAndRank -> fileAndRank + "" + fileAndRank.second(),
            crossProduct(rangeClosed('a', 'f'), rangeClosed(1, 8))
        )
    );
  }
}
