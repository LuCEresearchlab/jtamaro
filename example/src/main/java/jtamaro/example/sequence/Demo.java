package jtamaro.example.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

import static jtamaro.data.Sequences.fromIterable;
import static jtamaro.data.Sequences.fromStream;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.ofStringCharacters;
import static jtamaro.data.Sequences.ofStringLines;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.rangeClosed;
import static jtamaro.data.Sequences.replicate;
import static jtamaro.data.Sequences.unzip;
import static jtamaro.io.IO.print;
import static jtamaro.io.IO.println;

public class Demo {

  public static void main(String[] args) {
    Sequence<Integer> is = range(5);
    for (int i : is) {
      println(i);
    }

    println(is.map(Object::toString)
        .intersperse(":"));

    for (String s : of("A", "B", "C", "D").take(2)) {
      println(s);
    }

    for (boolean b : of(true, false, false, true).drop(2)) {
      println(b);
    }

    println(of("S", "M", "L", "XL")
        .intersperse("-")
        .reduce("", (e, a) -> a + e));

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

    for (char c : rangeClosed('A', 'F').concat(rangeClosed('a', 'f'))) {
      print(c);
    }
    println();

    println(range('A', 'F').concat(range('F', 'A', -1)));

    println(of("a", "b", "c").zipWith(of(1, 2, 3)));

    println(range('A', (char) ('Z' + 1)).zipWithIndex());

    Pair<Sequence<Character>, Sequence<Integer>> pair = unzip(of(
        new Pair<>('A', 1),
        new Pair<>('B', 2),
        new Pair<>('C', 3)));
    println(pair.first());
    println(pair.second());

    Pair<Sequence<Integer>, Sequence<Integer>> pair2 = unzip(range(0, 10).zipWith(range(1, 11)));
    println(pair2.first().take(5));
    println(pair2.second().take(5));

    println(ofStringCharacters("ABC"));

    println(ofStringLines("Hello\nWorld!\nHow are you?"));

    println(ofStringLines("ABC\nCDE\nEFG").flatMap(Sequences::ofStringCharacters));

    println(ofStringLines("1\n2\n3\n4\n5\n6").map(s -> "(" + s + ")"));

    println(fromIterable(new ArrayList<>(Arrays.asList("one", "two", "three")))
        .map(s -> "(" + s + ")"));

    println(fromStream("a\nb\nc".lines())
        .map(s -> "(" + s + ")"));
  }
}
