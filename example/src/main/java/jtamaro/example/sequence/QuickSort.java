package jtamaro.example.sequence;

import jtamaro.data.Function2;
import jtamaro.data.Sequence;

import static jtamaro.data.Sequences.concat;
import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.filter;
import static jtamaro.data.Sequences.first;
import static jtamaro.data.Sequences.isEmpty;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.rest;
import static jtamaro.io.IO.println;

public class QuickSort {

  public static <T> Sequence<T> quickSort(Function2<T, T, Boolean> lessEqual, Sequence<T> sequence) {
    return isEmpty(sequence)
        ? empty()
        : concat(
            quickSort(lessEqual, filter(x -> lessEqual.apply(x, first(sequence)), rest(sequence))),
            cons(
                first(sequence),
                quickSort(lessEqual,
                    filter(x -> !lessEqual.apply(x, first(sequence)), rest(sequence)))
            )
        );
  }

  public static <T> Sequence<T> quickSortNice(Function2<T, T, Boolean> lessEqual, Sequence<T> sequence) {
    if (isEmpty(sequence)) {
      return empty();
    } else {
      final T pivot = first(sequence);
      final Sequence<T> smaller = filter(x -> lessEqual.apply(x, pivot), rest(sequence));
      final Sequence<T> larger = filter(x -> !lessEqual.apply(x, pivot), rest(sequence));
      return concat(
          quickSortNice(lessEqual, smaller),
          cons(
              pivot,
              quickSortNice(lessEqual, larger)
          )
      );
    }
  }

  public static void main(String[] args) {
    println(quickSort((a, b) -> a <= b, of(1, 3, 2, 9, 2, 1, 8, 7)));
    println(quickSort((a, b) -> a.compareTo(b) <= 0, of("B", "A", "Z", "C")));
  }
}
