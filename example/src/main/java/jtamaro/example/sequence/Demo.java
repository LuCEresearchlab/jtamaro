package jtamaro.example.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

import static jtamaro.data.Sequences.concat;
import static jtamaro.data.Sequences.drop;
import static jtamaro.data.Sequences.flatMap;
import static jtamaro.data.Sequences.fromIterable;
import static jtamaro.data.Sequences.fromStream;
import static jtamaro.data.Sequences.intersperse;
import static jtamaro.data.Sequences.map;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.ofStringCharacters;
import static jtamaro.data.Sequences.ofStringLines;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.rangeClosed;
import static jtamaro.data.Sequences.reduce;
import static jtamaro.data.Sequences.replicate;
import static jtamaro.data.Sequences.take;
import static jtamaro.data.Sequences.unzip;
import static jtamaro.data.Sequences.zip;
import static jtamaro.data.Sequences.zipWithIndex;
import static jtamaro.io.IO.print;
import static jtamaro.io.IO.println;

public class Demo {

  public static void main(String[] args) {
    Sequence<Integer> is = range(5);
    for (int i : is) {
      println(i);
    }

    println(intersperse(":", map(Object::toString, is)));

    for (String s : take(2, of("A", "B", "C", "D"))) {
      println(s);
    }

    for (boolean b : drop(2, of(true, false, false, true))) {
      println(b);
    }

    println(reduce("", (e, a) -> a + e, intersperse("-", of("S", "M", "L", "XL"))));

    for (String s : replicate("$", 4)) {
      println(s);
    }

    for (int i : range(2)) {
      println(i);
    }

    for (int i : range(10, 13)) {
      println(i);
    }

    for (int i : range(1, 5, 2)) {
      println(i);
    }

    for (char c : concat(rangeClosed('A', 'F'), rangeClosed('a', 'f'))) {
      print(c);
    }
    println();

    println(concat(range('A', 'F'), range('F', 'A', -1)));

    println(zip(of("a", "b", "c"), of(1, 2, 3)));

    println(zipWithIndex(range('A', (char) ('Z' + 1))));

    Pair<Sequence<Character>, Sequence<Integer>> pair = unzip(of(
        new Pair<>('A', 1),
        new Pair<>('B', 2),
        new Pair<>('C', 3)));
    println(pair.first());
    println(pair.second());

    Pair<Sequence<Integer>, Sequence<Integer>> pair2 = unzip(zip(range(0, 10), range(1, 11)));
    println(take(5, pair2.first()));
    println(take(5, pair2.second()));

    println(ofStringCharacters("ABC"));

    println(ofStringLines("Hello\nWorld!\nHow are you?"));

    println(flatMap(Sequences::ofStringCharacters, ofStringLines("ABC\nCDE\nEFG")));

    println(map(s -> "(" + s + ")", ofStringLines("1\n2\n3\n4\n5\n6")));

    println(map(s -> "(" + s + ")",
        fromIterable(new ArrayList<>(Arrays.asList("one", "two", "three")))));

    println(map(s -> "(" + s + ")", fromStream("a\nb\nc".lines())));
  }
}
