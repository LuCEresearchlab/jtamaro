package jtamaro.example.sequence;

import jtamaro.data.Function2;
import jtamaro.data.Sequence;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.of;
import static jtamaro.io.IO.println;

public final class InsertionSort {

  public static <T> Sequence<T> insertionSort(Function2<T, T, Boolean> lessEqual, Sequence<T> sequence) {
    return sequence.isEmpty()
        ? empty()
        : insert(
            sequence.first(),
            insertionSort(lessEqual, sequence.rest()),
            lessEqual
        );
  }

  public static <T> Sequence<T> insert(T element, Sequence<T> sequence, Function2<T, T, Boolean> lessEqual) {
    return sequence.isEmpty()
        ? cons(element, empty())
        : lessEqual.apply(element, sequence.first())
            ? cons(element, sequence)
            : cons(
                sequence.first(),
                insert(element, sequence.rest(), lessEqual)
            );
  }

  public static void main() {
    println(insertionSort((a, b) -> a <= b, of(1, 3, 2, 9, 2, 1, 8, 7)));
    println(insertionSort((a, b) -> a.compareTo(b) <= 0, of("B", "A", "Z", "C")));
  }
}
