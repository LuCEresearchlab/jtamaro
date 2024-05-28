package jtamaro.data;

import java.util.List;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

import static jtamaro.data.Sequences.concat;
import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.crossProduct;
import static jtamaro.data.Sequences.drop;
import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.filter;
import static jtamaro.data.Sequences.first;
import static jtamaro.data.Sequences.flatMap;
import static jtamaro.data.Sequences.foldLeft;
import static jtamaro.data.Sequences.foldRight;
import static jtamaro.data.Sequences.fromIterable;
import static jtamaro.data.Sequences.fromStream;
import static jtamaro.data.Sequences.intersperse;
import static jtamaro.data.Sequences.isEmpty;
import static jtamaro.data.Sequences.map;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.ofStringCharacters;
import static jtamaro.data.Sequences.ofStringLines;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.rangeClosed;
import static jtamaro.data.Sequences.reduce;
import static jtamaro.data.Sequences.replicate;
import static jtamaro.data.Sequences.rest;
import static jtamaro.data.Sequences.reverse;
import static jtamaro.data.Sequences.take;
import static jtamaro.data.Sequences.unzip;
import static jtamaro.data.Sequences.zip;
import static jtamaro.data.Sequences.zipWithIndex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class SequencesTest {

  @Test
  public void testEmptyIsEmpty() {
    Assert.assertTrue(Sequences.isEmpty(Sequences.empty()));
  }

  @Test
  public void testConsIsNotEmpty() {
    Assert.assertFalse(Sequences.isEmpty(Sequences.cons(1, Sequences.empty())));
  }

  @Test
  public void testConsFirst() {
    Assert.assertEquals(1L, (long) Sequences.first(Sequences.cons(1, Sequences.empty())));
  }

  @Test
  public void testConsRest() {
    Assert.assertTrue(Sequences.isEmpty(Sequences.rest(Sequences.cons(1, Sequences.empty()))));
  }

  @Test
  public void testOfEmpty() {
    Assert.assertTrue(Sequences.isEmpty(Sequences.of()));
  }

  @Test
  public void testOfOne() {
    final Sequence<Long> seq = Sequences.of(1L);
    Assert.assertEquals(1L, (long) Sequences.first(seq));
    Assert.assertTrue(Sequences.isEmpty(Sequences.rest(seq)));
  }

  @Test
  public void testOfThree() {
    final Sequence<Integer> seq = Sequences.of(1, 2, 3);
    Assert.assertEquals(1L, (long) Sequences.first(seq));
    Assert.assertEquals(2L, (long) Sequences.first(Sequences.rest(seq)));
    Assert.assertEquals(3L, (long) Sequences.first(Sequences.rest(Sequences.rest(seq))));
    Assert.assertTrue(Sequences.isEmpty(Sequences.rest(Sequences.rest(Sequences.rest(seq)))));
  }

  @Test
  public void testOfEmptyStringCharacters() {
    Assert.assertTrue(Sequences.isEmpty(Sequences.ofStringCharacters("")));
  }

  @Test
  public void testOfOneStringCharacters() {
    final Sequence<Character> seq = Sequences.ofStringCharacters("a");
    Assert.assertEquals('a', (char) Sequences.first(seq));
    Assert.assertTrue(Sequences.isEmpty(Sequences.rest(seq)));
  }

  @Test
  public void testOfThreeStringCharacters() {
    final Sequence<Character> seq = Sequences.ofStringCharacters("abc");
    Assert.assertEquals('a', (char) Sequences.first(seq));
    Assert.assertEquals('b', (char) Sequences.first(Sequences.rest(seq)));
    Assert.assertEquals('c', (char) Sequences.first(Sequences.rest(Sequences.rest(seq))));
    Assert.assertTrue(Sequences.isEmpty(Sequences.rest(Sequences.rest(Sequences.rest(seq)))));
  }

  @Test
  public void testOfEmptyStringLines() {
    Assert.assertTrue(Sequences.isEmpty(Sequences.ofStringLines("")));
  }

  @Test
  public void testOfOneStringLines() {
    final Sequence<String> seq = Sequences.ofStringLines("line1");
    Assert.assertEquals("line1", Sequences.first(seq));
  }

  @Test
  public void testOfThreeStringLines() {
    final Sequence<String> seq = Sequences.ofStringLines("line1\nline2\nline3");
    Assert.assertEquals("line1", Sequences.first(seq));
    Assert.assertEquals("line2", Sequences.first(Sequences.rest(seq)));
    Assert.assertEquals("line3", Sequences.first(Sequences.rest(Sequences.rest(seq))));
    Assert.assertTrue(Sequences.isEmpty(Sequences.rest(Sequences.rest(Sequences.rest(seq)))));
  }

  @Test
  public void testRangeEmpty() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.range(0, 0));
  }

  @Test
  public void testRangeOneElem() {
    TestUtil.assertSequenceEquals(Sequences.of(0), Sequences.range(0, 1));
  }

  @Test
  public void testRangeTwoElems() {
    TestUtil.assertSequenceEquals(Sequences.of(0, 1), Sequences.range(0, 2));
  }

  @Test
  public void testRangeThreeElems() {
    TestUtil.assertSequenceEquals(Sequences.of(0, 1, 2), Sequences.range(0, 3));
  }

  @Test
  public void testRangeOneUntilThree() {
    TestUtil.assertSequenceEquals(Sequences.of(1, 2), Sequences.range(1, 3));
  }

  @Test
  public void testRangeTwoUntilThree() {
    TestUtil.assertSequenceEquals(Sequences.of(2), Sequences.range(2, 3));
  }

  @Test
  public void testRangeThreeUntilThree() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.range(3, 3));
  }

  @Test
  public void testRangeThreeUntilThreeStepTwo() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.range(3, 3, 2));
  }

  @Test
  public void testRangeZeroUntilThreeStepThree() {
    TestUtil.assertSequenceEquals(Sequences.of(0), Sequences.range(0, 3, 3));
  }

  @Test
  public void testRangeOneUntilThreeStepOne() {
    TestUtil.assertSequenceEquals(Sequences.of(1, 2), Sequences.range(1, 3, 1));
  }

  @Test
  public void testRangeThreeUntilZeroStepMinusOne() {
    TestUtil.assertSequenceEquals(Sequences.of(3, 2, 1), Sequences.range(3, 0, -1));
  }

  @Test
  public void testRangeThreeUntilZeroStepMinusTwo() {
    TestUtil.assertSequenceEquals(Sequences.of(3, 1), Sequences.range(3, 0, -2));
  }

  @Test
  public void testRangeTwoUntilZeroStepMinusThree() {
    TestUtil.assertSequenceEquals(Sequences.of(2), Sequences.range(2, 0, -3));
  }

  @Test
  public void testRangeThreeUntilThreeStepMinusThree() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.range(3, 3, -3));
  }

  @Test
  public void testRangeThreeUntilFourStepMinusOne() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.range(3, 4, -1));
  }

  @Test
  public void testRangeFiveUntilFourStepOne() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.range(5, 4, 1));
  }

  @Test
  public void testRangeClosedOneElem() {
    TestUtil.assertSequenceEquals(Sequences.of(0), Sequences.rangeClosed(0, 0));
  }

  @Test
  public void testRangeClosedTwoElems() {
    TestUtil.assertSequenceEquals(Sequences.of(0, 1), Sequences.rangeClosed(0, 1));
  }

  @Test
  public void testRangeClosedThreeElems() {
    TestUtil.assertSequenceEquals(Sequences.of(0, 1, 2), Sequences.rangeClosed(0, 2));
  }

  @Test
  public void testRangeClosedOneToThree() {
    TestUtil.assertSequenceEquals(Sequences.of(1, 2), Sequences.rangeClosed(1, 2));
  }

  @Test
  public void testRangeClosedTwoToThree() {
    TestUtil.assertSequenceEquals(Sequences.of(2), Sequences.rangeClosed(2, 2));
  }

  @Test
  public void testRangeClosedFourToThreeStepTwo() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.rangeClosed(4, 3, 2));
  }

  @Test
  public void testRangeClosedZeroToThreeStepFour() {
    TestUtil.assertSequenceEquals(Sequences.of(0), Sequences.rangeClosed(0, 3, 4));
  }

  @Test
  public void testRangeClosedOneToThreeStepOne() {
    TestUtil.assertSequenceEquals(Sequences.of(1, 2, 3), Sequences.rangeClosed(1, 3, 1));
  }

  @Test
  public void testRangeClosedThreeToZeroStepMinusOne() {
    TestUtil.assertSequenceEquals(Sequences.of(3, 2, 1, 0), Sequences.rangeClosed(3, 0, -1));
  }

  @Test
  public void testRangeClosedThreeToZeroStepMinusTwo() {
    TestUtil.assertSequenceEquals(Sequences.of(3, 1), Sequences.rangeClosed(3, 0, -2));
  }

  @Test
  public void testRangeClosedTwoToZeroStepMinusThree() {
    TestUtil.assertSequenceEquals(Sequences.of(2), Sequences.rangeClosed(2, 0, -3));
  }

  @Test
  public void testRangeClosedThreeToThreeStepMinusThree() {
    TestUtil.assertSequenceEquals(Sequences.of(3), Sequences.rangeClosed(3, 3, -3));
  }

  @Test
  public void testRangeClosedThreeToFourStepMinusOne() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.rangeClosed(3, 4, -1));
  }

  @Test
  public void testRangeClosedFiveToFourStepOne() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.rangeClosed(5, 4, 1));
  }

  @Test
  public void testReplicate() {
    TestUtil.assertSequenceEquals(Sequences.of(1, 1, 1, 1), Sequences.replicate(1, 4));
  }

  @Test
  public void testOnIterable() {
    TestUtil.assertSequenceEquals(
        Sequences.of("one", "two", "three"),
        Sequences.fromIterable(List.of("one", "two", "three"))
    );
  }

  @Test
  public void testFromStream() {
    TestUtil.assertSequenceEquals(
        Sequences.of("a", "b", "c"),
        Sequences.fromStream(Stream.of("a", "b", "c"))
    );
  }

  @Test
  public void testReverseEmpty() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.reverse(Sequences.empty()));
  }

  @Test
  public void testReverseOne() {
    TestUtil.assertSequenceEquals(Sequences.of(1), Sequences.reverse(Sequences.of(1)));
  }

  @Test
  public void testReverseTwo() {
    TestUtil.assertSequenceEquals(Sequences.of(1, 0), Sequences.reverse(Sequences.range(2)));
  }

  @Test
  public void testReverseFive() {
    TestUtil.assertSequenceEquals(Sequences.rangeClosed(4, 0, -1), Sequences.reverse(Sequences.rangeClosed(0, 4)));
  }

  @Test
  public void testMapEmpty() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.map(x -> x, Sequences.empty()));
  }

  @Test
  public void testMapOneElem() {
    TestUtil.assertSequenceEquals(Sequences.of(3), Sequences.map(String::length, Sequences.of("ABC")));
  }

  @Test
  public void testMapTwoElems() {
    TestUtil.assertSequenceEquals(Sequences.of(3, 2), Sequences.map(String::length, Sequences.of("ABC", "12")));
  }

  @Test
  public void testMapThreeElems() {
    TestUtil.assertSequenceEquals(Sequences.of(3, 2, 4), Sequences.map(String::length, Sequences.of("ABC", "12", "....")));
  }

  @Test
  public void testMapApplicationCountConsumed() {
    final int[] count = new int[]{0};
    Sequences.map(e -> {
      count[0]++;
      return e;
    }, Sequences.range(10));
    Assert.assertEquals(10, count[0]);
  }

  @Test
  public void testMapMapApplicationCountConsumed() {
    final int[] innerCount = new int[]{0};
    final int[] outerCount = new int[]{0};
    Sequences.map(e -> {
      innerCount[0]++;
      return e;
    }, Sequences.map(e -> {
      outerCount[0]++;
      return e;
    }, Sequences.range(10)));
    Assert.assertEquals(10, innerCount[0]);
    Assert.assertEquals(10, outerCount[0]);
  }

  @Test
  public void testFilterEmptyTrue() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.filter(x -> true, Sequences.empty()));
  }

  @Test
  public void testFilterEmptyFalse() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.filter(x -> false, Sequences.empty()));
  }

  @Test
  public void testFilter1True() {
    TestUtil.assertSequenceEquals(Sequences.of(1), Sequences.filter(s -> true, Sequences.of(1)));
  }

  @Test
  public void testFilter1False() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.filter(s -> false, Sequences.of(1)));
  }

  @Test
  public void testFilter1TrueTest() {
    TestUtil.assertSequenceEquals(Sequences.of(1), Sequences.filter(s -> s == 1, Sequences.of(1)));
  }

  @Test
  public void testFilter1FalseTest() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.filter(s -> s != 1, Sequences.of(1)));
  }

  @Test
  public void testFilterMany() {
    TestUtil.assertSequenceEquals(Sequences.of(0, 2, 4, 6, 8), Sequences.filter(i -> i % 2 == 0, Sequences.range(10)));
  }

  @Test
  public void testFilterPredicateApplicationCountConsumed() {
    final int[] count = new int[]{0};
    Sequences.filter(s -> {
      count[0]++;
      return true;
    }, Sequences.range(10));
    Assert.assertEquals(10, count[0]);
  }

  @Test
  public void testFilterFilterPredicateApplicationCountConsumed() {
    int[] innerCount = new int[]{0};
    int[] outerCount = new int[]{0};
    Sequences.filter(s -> {
      outerCount[0]++;
      return false;
    }, Sequences.filter(s -> {
      innerCount[0]++;
      return true;
    }, Sequences.range(10)));
    Assert.assertEquals(10, innerCount[0]);
    Assert.assertEquals(10, outerCount[0]);
  }

  @Test
  public void testReduceEmpty() {
    Assert.assertEquals(0L, (long) Sequences.reduce(0, (e, a) -> a, Sequences.empty()));
  }

  @Test
  public void testReduceMany() {
    Assert.assertEquals(110L, (long) Sequences.reduce(100, (e, a) -> a + 1, Sequences.range(10)));
  }

  @Test
  public void testReduceManyNonCommutative() {
    Assert.assertEquals("ABCD", Sequences.reduce("", (e, a) -> e + a, Sequences.rangeClosed('A', 'D')));
  }

  @Test
  public void testReduceManyNonCommutativeReverse() {
    Assert.assertEquals("DCBA", Sequences.reduce("", (e, a) -> a + e, Sequences.rangeClosed('A', 'D')));
  }

  @Test
  public void testFoldRightEmpty() {
    Assert.assertEquals(0L, (long) Sequences.foldRight(0, (e, a) -> a, Sequences.empty()));
  }

  @Test
  public void testFoldRightMany() {
    Assert.assertEquals(110L, (long) Sequences.foldRight(100, (e, a) -> a + 1, Sequences.range(10)));
  }

  @Test
  public void testFoldRightManyNonCommutative() {
    Assert.assertEquals("ABCD", Sequences.foldRight("", (e, a) -> e + a, Sequences.rangeClosed('A', 'D')));
  }

  @Test
  public void testFoldRightManyNonCommutativeReverse() {
    Assert.assertEquals("DCBA", Sequences.foldRight("", (e, a) -> a + e, Sequences.rangeClosed('A', 'D')));
  }

  @Test
  public void testFoldLeftEmpty() {
    Assert.assertEquals(0L, (long) Sequences.foldLeft(0, (a, e) -> a, Sequences.empty()));
  }

  @Test
  public void testFoldLeftMany() {
    Assert.assertEquals(110L, (long) Sequences.foldLeft(100, (a, e) -> a + 1, Sequences.range(10)));
  }

  @Test
  public void testFoldLeftManyNonCommutative() {
    Assert.assertEquals("ABCD", Sequences.foldLeft("", (a, e) -> a + e, Sequences.rangeClosed('A', 'D')));
  }

  @Test
  public void testFoldLeftManyNonCommutativeReverse() {
    Assert.assertEquals("DCBA", Sequences.foldLeft("", (a, e) -> e + a, Sequences.rangeClosed('A', 'D')));
  }

  @Test
  public void testFlatMap() {
    TestUtil.assertSequenceEquals(Sequences.of('A', 'B', 'C', 'C', 'D', 'E', 'E', 'F', 'G'),
        Sequences.flatMap(Sequences::ofStringCharacters, Sequences.ofStringLines("ABC\nCDE\nEFG")));
  }

  @Test
  public void testDropNone() {
    TestUtil.assertSequenceEquals(Sequences.of(10, 20, 30), Sequences.drop(0, Sequences.of(10, 20, 30)));
  }

  @Test
  public void testDropOne() {
    TestUtil.assertSequenceEquals(Sequences.of(20, 30), Sequences.drop(1, Sequences.of(10, 20, 30)));
  }

  @Test
  public void testDropAll() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.drop(3, Sequences.of(10, 20, 30)));
  }

  @Test
  public void testDropMore() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.drop(4, Sequences.of(10, 20, 30)));
  }

  @Test
  public void testDropString() {
    TestUtil.assertSequenceEquals(Sequences.of("Ho"), Sequences.drop(1, Sequences.of("Hi", "Ho")));
  }

  @Test
  public void testTakeNone() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.take(0, Sequences.of(10, 20, 30)));
  }

  @Test
  public void testTakeOne() {
    TestUtil.assertSequenceEquals(Sequences.of(10), Sequences.take(1, Sequences.of(10, 20, 30)));
  }

  @Test
  public void testTakeAll() {
    TestUtil.assertSequenceEquals(Sequences.of(10, 20, 30), Sequences.take(3, Sequences.of(10, 20, 30)));
  }

  @Test
  public void testTakeMore() {
    TestUtil.assertSequenceEquals(Sequences.of(10, 20, 30), Sequences.take(4, Sequences.of(10, 20, 30)));
  }

  @Test
  public void testTakeString() {
    TestUtil.assertSequenceEquals(Sequences.of("Hi"), Sequences.take(1, Sequences.of("Hi", "Ho")));
  }

  @Test
  public void testConcat() {
    TestUtil.assertSequenceEquals(
        Sequences.range(1, 10),
        Sequences.concat(Sequences.of(1, 2, 3, 4), Sequences.of(5, 6, 7, 8, 9))
    );
  }

  @Test
  public void testConcatSubTypes() {
    TestUtil.assertSequenceEquals(
        Sequences.of(1, 2f, 3L, 4.0),
        Sequences.concat(Sequences.of(1), Sequences.concat(Sequences.of(2f), Sequences.concat(
            Sequences.of(3L), Sequences.of(4.0))))
    );
  }

  @Test
  public void testConcatEmpty() {
    TestUtil.assertSequenceEquals(
        Sequences.range(1, 5),
        Sequences.concat(Sequences.of(1, 2, 3, 4), Sequences.empty())
    );
  }

  @Test
  public void testIntersperseZero() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.intersperse("A", Sequences.empty()));
  }

  @Test
  public void testIntersperseOne() {
    TestUtil.assertSequenceEquals(
        Sequences.of("1", "A", "2", "A", "3"),
        Sequences.intersperse("A", Sequences.of("1", "2", "3"))
    );
  }

  @Test
  public void testIntersperseTwo() {
    TestUtil.assertSequenceEquals(
        Sequences.of("X", "A", "Y"),
        Sequences.intersperse("A", Sequences.of("X", "Y"))
    );
  }

  @Test
  public void testIntersperseThree() {
    TestUtil.assertSequenceEquals(
        Sequences.of("X", "A", "Y", "A", "Z"),
        Sequences.intersperse("A", Sequences.of("X", "Y", "Z"))
    );
  }

  @Test
  public void testZip() {
    TestUtil.assertSequenceEquals(
        Sequences.of(new Pair<>("a", 1), new Pair<>("b", 2), new Pair<>("c", 3)),
        Sequences.zip(Sequences.of("a", "b", "c"), Sequences.of(1, 2, 3))
    );
  }

  @Test
  public void testZipLeftLonger() {
    TestUtil.assertSequenceEquals(
        Sequences.of(new Pair<>("a", 1), new Pair<>("b", 2), new Pair<>("c", 3)),
        Sequences.zip(Sequences.of("a", "b", "c", "d"), Sequences.of(1, 2, 3))
    );
  }


  @Test
  public void testZipRightLonger() {
    TestUtil.assertSequenceEquals(
        Sequences.of(new Pair<>("a", 1), new Pair<>("b", 2)),
        Sequences.zip(Sequences.of("a", "b"), Sequences.of(1, 2, 3))
    );
  }

  @Test
  public void testZipWithIndex() {
    TestUtil.assertSequenceEquals(
        Sequences.of(new Pair<>('A', 0), new Pair<>('B', 1), new Pair<>('C', 2)),
        Sequences.zipWithIndex(Sequences.rangeClosed('A', 'C'))
    );
  }

  @Test
  public void testUnzip() {
    Pair<Sequence<Character>, Sequence<Integer>> pair = Sequences.unzip(Sequences.of(
        new Pair<>('A', 1),
        new Pair<>('B', 2),
        new Pair<>('C', 3))
    );
    TestUtil.assertSequenceEquals(Sequences.of('A', 'B', 'C'), pair.first());
    TestUtil.assertSequenceEquals(Sequences.of(1, 2, 3), pair.second());
  }

  @Test
  public void testCrossProd() {
    TestUtil.assertSequenceEquals(Sequences.of(new Pair<>('A', 1),
            new Pair<>('A', 2),
            new Pair<>('A', 3),
            new Pair<>('B', 1),
            new Pair<>('B', 2),
            new Pair<>('B', 3)),
        Sequences.crossProduct(Sequences.of('A', 'B'), Sequences.range(1, 4)));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyNoFirst() {
    Sequences.first(Sequences.empty());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyNoRest() {
    Sequences.rest(Sequences.empty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRangeStepZero() {
    Sequences.range(0, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRangeClosedStepZero() {
    Sequences.rangeClosed(0, 1, 0);
  }
}
