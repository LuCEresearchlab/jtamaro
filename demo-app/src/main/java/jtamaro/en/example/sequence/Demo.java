package jtamaro.en.example.sequence;

import java.util.ArrayList;
import java.util.Arrays;

import jtamaro.en.Pair;
import jtamaro.en.Sequence;

import static jtamaro.en.IO.print;
import static jtamaro.en.IO.println;
import static jtamaro.en.Pairs.*;
import static jtamaro.en.Sequences.*;


public class Demo {

  public static void main(String[] args) {
    Sequence<Integer> is = range(5);
    for (int i : is) {
      System.out.println(i);
    }

    print(intersperse(":", mapToString(is)));

    for (String s : take(2, of("A", "B", "C", "D"))) {
      System.out.println(s);
    }

    for (boolean b : drop(2, of(true, false, false, true))) {
      System.out.println(b);
    }

    System.out.println(reduce("", (e, a) -> a + e, intersperse("-", of("S", "M", "L", "XL"))));

    for (String s : take(4, repeat("-"))) {
      System.out.print(s);
    }
    System.out.println();

    for (String s : replicate("$", 4)) {
      System.out.println(s);
    }

    for (String s : take(4, iterate(s -> s + "x", "x"))) {
      System.out.println(s);
    }

    for (String s : take(5, cycle(of("a", "b", "c")))) {
      System.out.println(s);
    }

    for (int i : range(2)) {
      System.out.println(i);
    }

    for (int i : range(10, 13)) {
      System.out.println(i);
    }

    for (int i : range(1, 5, 2)) {
      System.out.println(i);
    }

    for (char c : concat(range('A', 'F'), range('a', 'f'))) {
      System.out.print(c);
    }
    System.out.println();

    print(concat(range('A', 'F'), range('F', 'A', -1)));

    println(zip(of("a", "b", "c"), of(1, 2, 3)));

    println(zipWithIndex(range('A', (char) ('Z' + 1))));

    Pair<Sequence<Character>, Sequence<Integer>> pair = unzip(of(pair('A', 1), pair('B', 2), pair('C', 3)));
    println(pair.first());
    println(pair.second());

    Pair<Sequence<Integer>, Sequence<Integer>> pair2 = unzip(zip(from(0), from(1)));
    println(take(5, firstElement(pair2)));
    println(take(5, secondElement(pair2)));

    println(ofStringCharacters("ABC"));

    println(ofStringLines("Hello\nWorld!\nHow are you?"));

    println(flatMap(line -> ofStringCharacters(line), ofStringLines("ABC\nCDE\nEFG")));

    println(map(s -> "(" + s + ")", ofStringLines("1\n2\n3\n4\n5\n6")));

    println(map(s -> "(" + s + ")", fromIterable(new ArrayList<String>(Arrays.asList("one", "two", "three")))));

    println(map(s -> "(" + s + ")", fromStream("a\nb\nc".lines())));
  }

}
