package jtamaro.example.sequence;

import jtamaro.data.Function2;
import jtamaro.data.Sequence;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.of;
import static jtamaro.io.IO.println;

public class QuickSort {

  public static <T> Sequence<T> quickSort(Function2<T, T, Boolean> lessEqual, Sequence<T> sequence) {
    return sequence.isEmpty()
        ? empty()
        : quickSort(lessEqual, sequence.rest().filter(x -> lessEqual.apply(x, sequence.first())))
            .concat(cons(
                sequence.first(),
                quickSort(lessEqual,
                    sequence.rest().filter(x -> !lessEqual.apply(x, sequence.first())))));
  }

  public static <T> Sequence<T> quickSortNice(Function2<T, T, Boolean> lessEqual, Sequence<T> sequence) {
    if (sequence.isEmpty()) {
      return empty();
    } else {
      final T pivot = sequence.first();
      final Sequence<T> smaller = sequence.rest().filter(x -> lessEqual.apply(x, pivot));
      final Sequence<T> larger = sequence.rest().filter(x -> !lessEqual.apply(x, pivot));
      return quickSortNice(lessEqual, smaller)
          .concat(cons(pivot, quickSortNice(lessEqual, larger)));
    }
  }

  public static void main(String[] args) {
    println(quickSort((a, b) -> a <= b, of(1, 3, 2, 9, 2, 1, 8, 7)));
    println(quickSort((a, b) -> a.compareTo(b) <= 0, of("B", "A", "Z", "C")));
  }
}
