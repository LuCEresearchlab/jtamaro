package jtamaro.en.example.sequence;

import jtamaro.en.Sequence;
import static jtamaro.en.Sequences.range;
import static jtamaro.en.Sequences.filter;
import static jtamaro.en.Sequences.intersperse;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.map;
import static jtamaro.en.Sequences.take;
import static jtamaro.en.Sequences.drop;
import static jtamaro.en.Sequences.reduce;
import static jtamaro.en.Sequences.repeat;
import static jtamaro.en.Sequences.replicate;
import static jtamaro.en.Sequences.iterate;
import static jtamaro.en.Sequences.cycle;

public class Demo {
  
  public static void main(String[] args) {
    Sequence<Integer> is = range(5);
    for (int i : is) {
      System.out.println(i);
    }

    for (int i : filter(i -> i % 2 == 0, is)) {
      System.out.println(i);
    }

    for (int i : intersperse(99, is)) {
      System.out.println(i);
    }

    for (int i : of(3, 2, 1)) {
      System.out.println(i);
    }

    for (String s : map(s -> s+ "!", of("Hi", "Ho"))) {
      System.out.println(s);
    }

    for (String s : take(2, of("A", "B", "C", "D"))) {
      System.out.println(s);
    }

    for (boolean b : drop(2, of(true, false, false, true))) {
      System.out.println(b);
    }

    System.out.println(reduce((a, e) -> a + e, "", of("a", "b", "c", "d")));

    System.out.println(reduce((a, e) -> a + e, "", intersperse("-", of("S", "M", "L", "XL"))));

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

    for (int i : range(10, 0, -1)) {
      System.out.println(i);
    }

    for (char c : range('A', 'F')) {
      System.out.print(c);
    }
    System.out.println();
  }

}
