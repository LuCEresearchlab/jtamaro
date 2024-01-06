package jtamaro.en.example.sequence;

import static jtamaro.en.IO.*;
import static jtamaro.en.Sequences.*;

import jtamaro.en.Function2;
import jtamaro.en.Sequence;


public class InsertionSort {

    public static <T> Sequence<T> insertionSort(Function2<T,T,Boolean> lessEqual, Sequence<T> sequence) {
        return isEmpty(sequence)
            ? empty()
            : insert(
                first(sequence),
                insertionSort(lessEqual, rest(sequence)),
                lessEqual
            );
    }
    
    public static <T> Sequence<T> insert(T element, Sequence<T> sequence, Function2<T,T,Boolean> lessEqual) {
        return isEmpty(sequence)
            ? cons(element, empty())
            : lessEqual.apply(element, first(sequence))
                ? cons(element, sequence)
                : cons(
                    first(sequence),
                    insert(element, rest(sequence), lessEqual)
                );
    }


    public static void main(String[] args) {
      println(insertionSort((a, b) -> a <= b, of(1, 3, 2, 9, 2, 1, 8, 7)));
      println(insertionSort((a, b) -> a.compareTo(b) <= 0, of("B", "A", "Z", "C")));
    }

}
