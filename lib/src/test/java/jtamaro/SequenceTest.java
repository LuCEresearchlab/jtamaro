package jtamaro;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import jtamaro.en.Pair;
import jtamaro.en.Sequence;

import static jtamaro.en.IO.println;
import static jtamaro.en.Pairs.*;
import static jtamaro.en.Sequences.*;
import static org.junit.jupiter.api.Assertions.*;


public class SequenceTest {

  public static <T> void assertSequenceEquals(Sequence<T> expected, Sequence<T> actual) {
    Sequence<T> e = expected;
    Sequence<T> a = actual;
    while (!isEmpty(e) && !isEmpty(a)) {
      assertEquals(first(e), first(a));
      e = rest(e);
      a = rest(a);
    }
    assertTrue(isEmpty(e));
    assertTrue(isEmpty(a));
  }


  //--- isEmpty, first, rest
  @Test
  public void testEmptyIsEmpty() {
    assertTrue(isEmpty(empty()));
  }

  @Test
  public void testConsIsNotEmpty() {
    assertFalse(isEmpty(cons(1, empty())));
  }

  @Test
  public void testConsFirst() {
    assertEquals(1, first(cons(1, empty())));
  }

  @Test
  public void testConsRest() {
    assertTrue(isEmpty(rest(cons(1, empty()))));
  }


  //--- of
  @Test
  public void testOfEmpty() {
    assert isEmpty(of());
  }

  @Test
  public void testOfOne() {
    var seq = of(1);
    assert first(seq) == 1;
    assert isEmpty(rest(seq));
  }

  @Test
  public void testOfThree() {
    var seq = of(1, 2, 3);
    assert first(seq) == 1;
    assert first(rest(seq)) == 2;
    assert first(rest(rest(seq))) == 3;
    assert isEmpty(rest(rest(rest(seq))));
  }


  //--- ofStringCharacters
  @Test
  public void testOfEmptyStringCharacters() {
    assert isEmpty(ofStringCharacters(""));
  }

  @Test
  public void testOfOneStringCharacters() {
    var seq = ofStringCharacters("a");
    assert first(seq) == 'a';
    assert isEmpty(rest(seq));
  }

  @Test
  public void testOfThreeStringCharacters() {
    var seq = ofStringCharacters("abc");
    assert first(seq) == 'a';
    assert first(rest(seq)) == 'b';
    assert first(rest(rest(seq))) == 'c';
    assert isEmpty(rest(rest(rest(seq))));
  }
  

  //--- ofStringLines
  @Test
  public void testOfEmptyStringLines() {
    assert isEmpty(ofStringLines(""));
  }

  @Test
  public void testOfOneStringLines() {
    var seq = ofStringLines("line1");
    assert first(seq).equals("line1");
    assert isEmpty(rest(seq));
  }

  @Test
  public void testOfThreeStringLines() {
    var seq = ofStringLines("line1\nline2\nline3");
    assert first(seq).equals("line1");
    assert first(rest(seq)).equals("line2");
    assert first(rest(rest(seq))).equals("line3");
    assert isEmpty(rest(rest(rest(seq))));
  }


  //--- hasDefiniteSize
  @Test
  public void testEmptyHasDefiniteSize() {
    assertTrue(empty().hasDefiniteSize());
  }

  @Test
  public void testConsEmptyHasDefiniteSize() {
    assertTrue(cons(5, empty()).hasDefiniteSize());
  }

  @Test
  public void testOfHasDefiniteSize() {
    assertTrue(of(1, 2, 3).hasDefiniteSize());
  }

  @Test
  public void testOfStringCharactersHasDefiniteSize() {
    assertTrue(ofStringCharacters("ABC").hasDefiniteSize());
  }

  @Test
  public void testOfStringLinesHasDefiniteSize() {
    assertTrue(ofStringLines("A\nB\nC").hasDefiniteSize());
  }


  public static void demo() {
    
    // TODO: Turn these into unit tests

    println(zip(of("a", "b", "c"), of(1, 2, 3)));

    println(zipWithIndex(range('A', (char) ('Z' + 1))));

    Pair<Sequence<Character>, Sequence<Integer>> pair = unzip(of(pair('A', 1), pair('B', 2), pair('C', 3)));
    println(pair.first());
    println(pair.second());

    Pair<Sequence<Integer>, Sequence<Integer>> pair2 = unzip(zip(from(0), from(1)));
    println(take(5, firstElement(pair2)));
    println(take(5, secondElement(pair2)));

    println(crossProduct(range('A', 'D'), range(1, 3)));

    println(flatMap(line -> ofStringCharacters(line), ofStringLines("ABC\nCDE\nEFG")));

    println(map(s -> "(" + s + ")", fromIterable(new ArrayList<String>(Arrays.asList("one", "two", "three")))));

    println(map(s -> "(" + s + ")", fromStream("a\nb\nc".lines())));
  }

}
