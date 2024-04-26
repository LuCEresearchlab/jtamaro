package jtamaro.en;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import jtamaro.en.data.Cons;
import jtamaro.en.data.Empty;
import org.junit.Assert;
import org.junit.Test;

import static jtamaro.en.Pairs.pair;
import static jtamaro.en.Sequences.cons;
import static jtamaro.en.Sequences.crossProduct;
import static jtamaro.en.Sequences.empty;
import static jtamaro.en.Sequences.first;
import static jtamaro.en.Sequences.flatMap;
import static jtamaro.en.Sequences.foldRight;
import static jtamaro.en.Sequences.from;
import static jtamaro.en.Sequences.fromIterable;
import static jtamaro.en.Sequences.fromStream;
import static jtamaro.en.Sequences.intersperse;
import static jtamaro.en.Sequences.isEmpty;
import static jtamaro.en.Sequences.map;
import static jtamaro.en.Sequences.mapToString;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.ofStringCharacters;
import static jtamaro.en.Sequences.ofStringLines;
import static jtamaro.en.Sequences.range;
import static jtamaro.en.Sequences.rest;
import static jtamaro.en.Sequences.stream;
import static jtamaro.en.Sequences.take;
import static jtamaro.en.Sequences.unzip;
import static jtamaro.en.Sequences.zip;
import static jtamaro.en.Sequences.zipWithIndex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class SequenceTest {

  // consume the given sequence (if it is lazy, resolve all of it)
  // (we simply compute the length to do that)
  public static <T> int consume(Sequence<T> s) {
    return isEmpty(s) ? 0 : 1 + consume(s.rest());
  }


  public static <T> String toString(Sequence<T> sequence) {
    return foldRight("]", (e, r) -> e + r, cons("[", intersperse(", ", mapToString(sequence))));
  }

  public static <T> void assertSequenceEquals(Sequence<T> expected, Sequence<T> actual) {
    Sequence<T> e = expected;
    Sequence<T> a = actual;
    while (!isEmpty(e) && !isEmpty(a)) {
      assertEquals(first(e), first(a));
      e = rest(e);
      a = rest(a);
    }
    assertTrue("There are more elements in expected: " + toString(e), isEmpty(e));
    assertTrue("There are more elements in actual: " + toString(a), isEmpty(a));
    // For convenience, we also test here the Sequences.equalTo method
    assertTrue("Sequences.equalTo fail", Sequences.equalTo(expected, actual));
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
    assertEquals(1L, (long) first(cons(1, empty())));
  }

  @Test
  public void testConsRest() {
    assertTrue(isEmpty(rest(cons(1, empty()))));
  }

  //--- of
  @Test
  public void testOfEmpty() {
    assertTrue(isEmpty(of()));
  }

  @Test
  public void testOfOne() {
    var seq = of(1);
    assertEquals(1L, (long) first(seq));
    assertTrue(isEmpty(rest(seq)));
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
    assertTrue(isEmpty(ofStringCharacters("")));
  }

  @Test
  public void testOfOneStringCharacters() {
    var seq = ofStringCharacters("a");
    assertEquals('a', (long) first(seq));
    assertTrue(isEmpty(rest(seq)));
  }

  @Test
  public void testOfThreeStringCharacters() {
    var seq = ofStringCharacters("abc");
    assertEquals('a', (long) first(seq));
    assertEquals('b', (long) first(rest(seq)));
    assertEquals('c', (long) first(rest(rest(seq))));
    assertTrue(isEmpty(rest(rest(rest(seq)))));
  }


  //--- ofStringLines
  @Test
  public void testOfEmptyStringLines() {
    assertTrue(isEmpty(ofStringLines("")));
  }

  @Test
  public void testOfOneStringLines() {
    var seq = ofStringLines("line1");
    assertEquals("line1", first(seq));
  }

  @Test
  public void testOfThreeStringLines() {
    var seq = ofStringLines("line1\nline2\nline3");
    assertEquals("line1", first(seq));
    assertEquals("line2", first(rest(seq)));
    assertEquals("line3", first(rest(rest(seq))));
    assertTrue(isEmpty(rest(rest(rest(seq)))));
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

  @Test
  public void testZip() {
    assertSequenceEquals(of(new Pair<>("a", 1), new Pair<>("b", 2), new Pair<>("c", 3)),
        zip(of("a", "b", "c"), of(1, 2, 3)));
  }

  @Test
  public void testZipWithIndex() {
    assertSequenceEquals(of(new Pair<>('A', 0), new Pair<>('B', 1), new Pair<>('C', 2)),
        zipWithIndex(range('A', 'D')));
  }

  @Test
  public void testUnzip() {
    Pair<Sequence<Character>, Sequence<Integer>> pair = unzip(of(pair('A', 1), pair('B', 2), pair('C', 3)));
    assertSequenceEquals(of('A', 'B', 'C'), pair.first());
    assertSequenceEquals(of(1, 2, 3), pair.second());
  }

  @Test
  public void testUnzipLazy() {
    Pair<Sequence<Integer>, Sequence<Integer>> pair = unzip(zip(from(0), from(1)));
    assertSequenceEquals(range(0, 5), take(5, pair.first()));
    assertSequenceEquals(range(1, 6), take(5, pair.second()));
  }

  @Test
  public void testCrossProd() {
    assertSequenceEquals(of(pair('A', 1), pair('A', 2), pair('A', 3), pair('B', 1), pair('B', 2), pair('B', 3)),
        crossProduct(of('A', 'B'), range(1, 4)));
  }

  @Test
  public void testFlatMap() {
    assertSequenceEquals(of('A', 'B', 'C', 'C', 'D', 'E', 'E', 'F', 'G'),
        flatMap(Sequences::ofStringCharacters, ofStringLines("ABC\nCDE\nEFG")));
  }

  @Test
  public void testOnIterable() {
    assertSequenceEquals(of("(one)", "(two)", "(three)"),
        map(s -> "(" + s + ")", fromIterable(new ArrayList<>(Arrays.asList("one", "two", "three")))));
  }

  @Test
  public void testFromStream() {
    assertSequenceEquals(of("(a)", "(b)", "(c)"),
        map(s -> "(" + s + ")", fromStream("a\nb\nc".lines())));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyNoFirst() {
    Sequences.empty().first();
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyNoRest() {
    Sequences.empty().rest();
  }

  @Test
  public void testEqualsBothEmpty() {
    Assert.assertEquals(empty(), new Empty<>());
  }

  @Test
  public void testNotEqualsEmptySingleCons() {
    Assert.assertNotEquals(empty(), of(1));
  }

  @Test
  public void testNotEqualsSingleCons() {
    Assert.assertNotEquals(of(1), of(2));
  }

  @Test
  public void testEqualsTwoCons() {
    Assert.assertEquals(of("1", "2"), new Cons<>("1", new Cons<>("2", new Empty<>())));
  }

  @Test
  public void testEqualsConsMap() {
    Assert.assertEquals(of("1", "2"), map(String::valueOf, of(1, 2)));
  }

  @Test
  public void testNotEqualsSecondElem() {
    Assert.assertNotEquals(of("1", "2"), map(String::valueOf, of(1, 3)));
  }

  @Test
  public void testNotEqualsMoreElems() {
    Assert.assertNotEquals(of(1, 2), of(1, 2, 3));
  }

  @Test
  public void testNotEqualsLessElems() {
    Assert.assertNotEquals(of(1, 2, 3), of(1, 2));
  }

  @Test
  public void testStream() {
    Assert.assertArrayEquals(
        IntStream.range(1, 5).map(x -> x + 1).toArray(),
        stream(range(1, 5)).mapToInt(x -> x + 1).toArray()
    );
  }
}
