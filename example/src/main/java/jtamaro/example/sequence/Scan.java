package jtamaro.example.sequence;

import jtamaro.data.Function2;
import jtamaro.data.Sequence;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.of;

public final class Scan {

  // scanl (+) 0 [3,5,2,1] = [0,3,8,10,11]
  // http://learnyouahaskell.com/higher-order-functions#folds
  public static <S, T> Sequence<T> scan(Function2<S, T, T> combine, T acc, Sequence<S> sequence) {
    return sequence.isEmpty()
        ? cons(acc, empty())
        : cons(
            acc,
            scan(combine, combine.apply(sequence.first(), acc), sequence.rest())
        );
  }

  public static void main() {
    System.out.println(scan(Integer::sum, 0, of(1, 3, 2, 9, 2, 1, 8, 7)));
  }
}
