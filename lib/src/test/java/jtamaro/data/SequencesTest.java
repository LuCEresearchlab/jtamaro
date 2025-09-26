package jtamaro.data;

import java.util.List;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

public final class SequencesTest {

  @Test
  public void testEmptyIsEmpty() {
    Assert.assertTrue(Sequences.empty().isEmpty());
  }

  @Test
  public void testConsIsNotEmpty() {
    Assert.assertFalse(Sequences.cons(1, Sequences.empty()).isEmpty());
  }

  @Test
  public void testConsFirst() {
    Assert.assertEquals(1L, (long) Sequences.cons(1, Sequences.empty()).first());
  }

  @Test
  public void testConsRest() {
    Assert.assertTrue(Sequences.cons(1, Sequences.empty()).rest().isEmpty());
  }

  @Test
  public void testOfEmpty() {
    Assert.assertTrue(Sequences.of().isEmpty());
  }

  @Test
  public void testOfOne() {
    final Sequence<Long> seq = Sequences.of(1L);
    Assert.assertEquals(1L, (long) seq.first());
    Assert.assertTrue(seq.rest().isEmpty());
  }

  @Test
  public void testOfThree() {
    final Sequence<Integer> seq = Sequences.of(1, 2, 3);
    Assert.assertEquals(1L, (long) seq.first());
    Assert.assertEquals(2L, (long) seq.rest().first());
    Assert.assertEquals(3L, (long) seq.rest().rest().first());
    Assert.assertTrue(seq.rest().rest().rest().isEmpty());
  }

  @Test
  public void testOfEmptyStringCharacters() {
    Assert.assertTrue(Sequences.ofStringCharacters("").isEmpty());
  }

  @Test
  public void testOfOneStringCharacters() {
    final Sequence<Character> seq = Sequences.ofStringCharacters("a");
    Assert.assertEquals('a', (char) seq.first());
    Assert.assertTrue(seq.rest().isEmpty());
  }

  @Test
  public void testOfThreeStringCharacters() {
    final Sequence<Character> seq = Sequences.ofStringCharacters("abc");
    Assert.assertEquals('a', (char) seq.first());
    Assert.assertEquals('b', (char) seq.rest().first());
    Assert.assertEquals('c', (char) seq.rest().rest().first());
    Assert.assertTrue(seq.rest().rest().rest().isEmpty());
  }

  @Test
  public void testOfEmptyStringLines() {
    Assert.assertTrue(Sequences.ofStringLines("").isEmpty());
  }

  @Test
  public void testOfOneStringLines() {
    final Sequence<String> seq = Sequences.ofStringLines("line1");
    Assert.assertEquals("line1", seq.first());
  }

  @Test
  public void testOfThreeStringLines() {
    final Sequence<String> seq = Sequences.ofStringLines("line1\nline2\nline3");
    Assert.assertEquals("line1", seq.first());
    Assert.assertEquals("line2", seq.rest().first());
    Assert.assertEquals("line3", seq.rest().rest().first());
    Assert.assertTrue(seq.rest().rest().rest().isEmpty());
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
  public void testRangeDoubleEmpty() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.range(0.0, 0.0, 0.1));
  }

  @Test
  public void testRangeDoubleOneElem() {
    TestUtil.assertSequenceEquals(Sequences.of(0.0), Sequences.range(0.0, 0.1, 0.1));
  }

  @Test
  public void testRangeDoubleTwoElems() {
    TestUtil.assertSequenceEquals(Sequences.of(0.0, 1.0), Sequences.range(0.0, 2.0, 1.0));
  }

  @Test
  public void testRangeDoubleThreeElems() {
    TestUtil.assertSequenceEquals(Sequences.of(0.0, 0.1, 0.2), Sequences.range(0, 0.3, 0.1));
  }

  @Test
  public void testRangeDoubleThreeUntilZeroStepMinusOne() {
    TestUtil.assertSequenceEquals(Sequences.of(3.0, 2.0, 1.0), Sequences.range(3.0, 0.0, -1.0));
  }

  @Test
  public void testRangeDoubleThreeUntilZeroStepMinusZeroDotFive() {
    TestUtil.assertSequenceEquals(Sequences.of(3.0, 2.5, 2.0, 1.5, 1.0, 0.5),
        Sequences.range(3.0, 0.0, -0.5));
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
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().reverse());
  }

  @Test
  public void testReverseOne() {
    TestUtil.assertSequenceEquals(Sequences.of(1), Sequences.of(1).reverse());
  }

  @Test
  public void testReverseTwo() {
    TestUtil.assertSequenceEquals(Sequences.of(1, 0), Sequences.range(2).reverse());
  }

  @Test
  public void testReverseFive() {
    TestUtil.assertSequenceEquals(Sequences.rangeClosed(4, 0, -1),
        Sequences.rangeClosed(0, 4).reverse());
  }

  @Test
  public void testMapEmpty() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().map(x -> x));
  }

  @Test
  public void testMapOneElem() {
    TestUtil.assertSequenceEquals(Sequences.of(3),
        Sequences.of("ABC").map(String::length));
  }

  @Test
  public void testMapTwoElems() {
    TestUtil.assertSequenceEquals(Sequences.of(3, 2),
        Sequences.of("ABC", "12").map(String::length));
  }

  @Test
  public void testMapThreeElems() {
    TestUtil.assertSequenceEquals(Sequences.of(3, 2, 4),
        Sequences.of("ABC", "12", "....").map(String::length));
  }

  @Test
  public void testMapApplicationCountConsumed() {
    final int[] count = new int[]{0};
    Sequences.range(10).map(e -> {
      count[0]++;
      return e;
    });
    Assert.assertEquals(10, count[0]);
  }

  @Test
  public void testMapMapApplicationCountConsumed() {
    final int[] firstCount = new int[]{0};
    final int[] secondCount = new int[]{0};
    Sequences.range(10).map(e -> {
      firstCount[0]++;
      return e;
    }).map(e -> {
      secondCount[0]++;
      return e;
    });
    Assert.assertEquals(10, firstCount[0]);
    Assert.assertEquals(10, secondCount[0]);
  }

  @Test
  public void testFilterEmptyTrue() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().filter(_ -> true));
  }

  @Test
  public void testFilterEmptyFalse() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().filter(_ -> false));
  }

  @Test
  public void testFilter1True() {
    TestUtil.assertSequenceEquals(Sequences.of(1), Sequences.of(1).filter(_ -> true));
  }

  @Test
  public void testFilter1False() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.of(1).filter(_ -> false));
  }

  @Test
  public void testFilter1TrueTest() {
    TestUtil.assertSequenceEquals(Sequences.of(1), Sequences.of(1).filter(s -> s == 1));
  }

  @Test
  public void testFilter1FalseTest() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.of(1).filter(s -> s != 1));
  }

  @Test
  public void testFilterMany() {
    TestUtil.assertSequenceEquals(Sequences.of(0, 2, 4, 6, 8),
        Sequences.range(10).filter(i -> i % 2 == 0));
  }

  @Test
  public void testFilterPredicateApplicationCountConsumed() {
    final int[] count = new int[]{0};
    Sequences.range(10).filter(_ -> {
      count[0]++;
      return true;
    });
    Assert.assertEquals(10, count[0]);
  }

  @Test
  public void testFilterFilterPredicateApplicationCountConsumed() {
    int[] firstCount = new int[]{0};
    int[] secondCount = new int[]{0};
    Sequences.range(10).filter(_ -> {
      firstCount[0]++;
      return true;
    }).filter(_ -> {
      secondCount[0]++;
      return false;
    });
    Assert.assertEquals(10, firstCount[0]);
    Assert.assertEquals(10, secondCount[0]);
  }

  @Test
  public void testReduceEmpty() {
    Assert.assertEquals(0L, (long) Sequences.empty().reduce(0, (_, a) -> a));
  }

  @Test
  public void testReduceMany() {
    Assert.assertEquals(110L, (long) Sequences.range(10).reduce(100, (_, a) -> a + 1));
  }

  @Test
  public void testReduceManyNonCommutative() {
    Assert.assertEquals("ABCD", Sequences.rangeClosed('A', 'D').reduce("", (e, a) -> e + a));
  }

  @Test
  public void testReduceManyNonCommutativeReverse() {
    Assert.assertEquals("DCBA", Sequences.rangeClosed('A', 'D').reduce("", (e, a) -> a + e));
  }

  @Test
  public void testFoldRightEmpty() {
    Assert.assertEquals(0L, (long) Sequences.empty().foldRight(0, (_, a) -> a));
  }

  @Test
  public void testFoldRightMany() {
    Assert.assertEquals(110L,
        (long) Sequences.range(10).foldRight(100, (_, a) -> a + 1));
  }

  @Test
  public void testFoldRightManyNonCommutative() {
    Assert.assertEquals("ABCD", Sequences.rangeClosed('A', 'D').foldRight("", (e, a) -> e + a));
  }

  @Test
  public void testFoldRightManyNonCommutativeReverse() {
    Assert.assertEquals("DCBA", Sequences.rangeClosed('A', 'D').foldRight("", (e, a) -> a + e));
  }

  @Test
  public void testFoldLeftEmpty() {
    Assert.assertEquals(0L, (long) Sequences.empty().foldLeft(0, (a, _) -> a));
  }

  @Test
  public void testFoldLeftMany() {
    Assert.assertEquals(110L, (long) Sequences.range(10).foldLeft(100, (a, _) -> a + 1));
  }

  @Test
  public void testFoldLeftManyNonCommutative() {
    Assert.assertEquals("ABCD", Sequences.rangeClosed('A', 'D').foldLeft("", (a, e) -> a + e));
  }

  @Test
  public void testFoldLeftManyNonCommutativeReverse() {
    Assert.assertEquals("DCBA", Sequences.rangeClosed('A', 'D').foldLeft("", (a, e) -> e + a));
  }

  @Test
  public void testFlatMap() {
    TestUtil.assertSequenceEquals(Sequences.of('A', 'B', 'C', 'C', 'D', 'E', 'E', 'F', 'G'),
        Sequences.ofStringLines("ABC\nCDE\nEFG").flatMap(Sequences::ofStringCharacters));
  }

  @Test
  public void testEmptyFlatMap() {
    TestUtil.assertSequenceEquals(Sequences.of(),
        Sequences.<String>of().flatMap(Sequences::ofStringCharacters));
  }

  @Test
  public void testDropEmptyNone() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().drop(0));
  }

  @Test
  public void testDropEmptyOne() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().drop(1));
  }

  @Test
  public void testDropNone() {
    TestUtil.assertSequenceEquals(Sequences.of(10, 20, 30),
        Sequences.of(10, 20, 30).drop(0));
  }

  @Test
  public void testDropOne() {
    TestUtil.assertSequenceEquals(Sequences.of(20, 30),
        Sequences.of(10, 20, 30).drop(1));
  }

  @Test
  public void testDropAll() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.of(10, 20, 30).drop(3));
  }

  @Test
  public void testDropMore() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.of(10, 20, 30).drop(4));
  }

  @Test
  public void testDropString() {
    TestUtil.assertSequenceEquals(Sequences.of("Ho"), Sequences.of("Hi", "Ho").drop(1));
  }

  @Test
  public void takeEmptyNone() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().take(0));
  }

  @Test
  public void takeEmptyOne() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().take(1));
  }

  @Test
  public void testTakeNone() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.of(10, 20, 30).take(0));
  }

  @Test
  public void testTakeOne() {
    TestUtil.assertSequenceEquals(Sequences.of(10), Sequences.of(10, 20, 30).take(1));
  }

  @Test
  public void testTakeAll() {
    TestUtil.assertSequenceEquals(Sequences.of(10, 20, 30), Sequences.of(10, 20, 30).take(3));
  }

  @Test
  public void testTakeMore() {
    TestUtil.assertSequenceEquals(Sequences.of(10, 20, 30), Sequences.of(10, 20, 30).take(4));
  }

  @Test
  public void testTakeString() {
    TestUtil.assertSequenceEquals(Sequences.of("Hi"), Sequences.of("Hi", "Ho").take(1));
  }

  @Test
  public void testConcat() {
    TestUtil.assertSequenceEquals(Sequences.range(1, 10),
        Sequences.of(1, 2, 3, 4).concat(Sequences.of(5, 6, 7, 8, 9)));
  }

  @Test
  public void testConcatAssociativity() {
    TestUtil.assertSequenceEquals(
        Sequences.of(1).concat(Sequences.of(2)).concat(Sequences.of(3)),
        Sequences.of(1).concat(Sequences.of(2).concat(Sequences.of(3))));
  }

  @Test
  public void testConcatEmpty() {
    TestUtil.assertSequenceEquals(Sequences.range(1, 5),
        Sequences.of(1, 2, 3, 4).concat(Sequences.empty()));
  }

  @Test
  public void testIntersperseZero() {
    TestUtil.assertSequenceEquals(Sequences.empty(), Sequences.empty().intersperse("A"));
  }

  @Test
  public void testIntersperseOne() {
    TestUtil.assertSequenceEquals(
        Sequences.of("1", "A", "2", "A", "3"),
        Sequences.of("1", "2", "3").intersperse("A")
    );
  }

  @Test
  public void testIntersperseTwo() {
    TestUtil.assertSequenceEquals(
        Sequences.of("X", "A", "Y"),
        Sequences.of("X", "Y").intersperse("A")
    );
  }

  @Test
  public void testIntersperseThree() {
    TestUtil.assertSequenceEquals(
        Sequences.of("X", "A", "Y", "A", "Z"),
        Sequences.of("X", "Y", "Z").intersperse("A")
    );
  }

  @Test
  public void testZip() {
    TestUtil.assertSequenceEquals(
        Sequences.of(new Pair<>("a", 1), new Pair<>("b", 2), new Pair<>("c", 3)),
        Sequences.of("a", "b", "c").zipWith(Sequences.of(1, 2, 3))
    );
  }

  @Test
  public void testZipLeftLonger() {
    TestUtil.assertSequenceEquals(
        Sequences.of(new Pair<>("a", 1), new Pair<>("b", 2), new Pair<>("c", 3)),
        Sequences.of("a", "b", "c", "d").zipWith(Sequences.of(1, 2, 3))
    );
  }


  @Test
  public void testZipRightLonger() {
    TestUtil.assertSequenceEquals(
        Sequences.of(new Pair<>("a", 1), new Pair<>("b", 2)),
        Sequences.of("a", "b").zipWith(Sequences.of(1, 2, 3))
    );
  }

  @Test
  public void testZipLeftEmpty() {
    TestUtil.assertSequenceEquals(
        Sequences.empty(),
        Sequences.empty().zipWith(Sequences.of(1, 2, 3))
    );
  }

  @Test
  public void testZipRightEmpty() {
    TestUtil.assertSequenceEquals(
        Sequences.empty(),
        Sequences.of(1, 2, 3).zipWith(Sequences.empty())
    );
  }

  @Test
  public void testZipWithIndex() {
    TestUtil.assertSequenceEquals(
        Sequences.of(new Pair<>('A', 0), new Pair<>('B', 1), new Pair<>('C', 2)),
        Sequences.rangeClosed('A', 'C').zipWithIndex()
    );
  }

  @Test
  public void testZipWithIndexEmpty() {
    TestUtil.assertSequenceEquals(
        Sequences.empty(),
        Sequences.empty().zipWithIndex()
    );
  }

  @Test
  public void testZipWithIndexSemanticallyEqualToZipWith() {
    final Sequence<Character> seq = Sequences.rangeClosed('A', 'Z');
    final Sequence<Integer> indices = Sequences.range(seq.foldLeft(0, (acc, _) -> acc + 1));
    TestUtil.assertSequenceEquals(
        seq.zipWith(indices),
        seq.zipWithIndex()
    );
  }

  @Test
  public void testUnzip() {
    final Pair<Sequence<Character>, Sequence<Integer>> pair = Sequences.unzip(Sequences.of(
        new Pair<>('A', 1),
        new Pair<>('B', 2),
        new Pair<>('C', 3))
    );
    TestUtil.assertSequenceEquals(Sequences.of('A', 'B', 'C'), pair.first());
    TestUtil.assertSequenceEquals(Sequences.of(1, 2, 3), pair.second());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyNoFirst() {
    Sequences.empty().first();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testEmptyNoRest() {
    Sequences.empty().rest();
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
