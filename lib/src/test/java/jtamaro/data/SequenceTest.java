package jtamaro.data;

import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;

public final class SequenceTest {

  @Test
  public void testStreamEmpty() {
    Assert.assertEquals(0L, Sequences.empty().stream().count());
  }

  @Test
  public void testStreamNonEmpty() {
    Assert.assertArrayEquals(IntStream.range(1, 5).map(x -> x + 1).toArray(),
        Sequences.range(1, 5).stream().mapToInt(x -> x + 1).toArray());
  }

  @Test
  public void testEqualsBothEmpty() {
    Assert.assertEquals(Sequences.empty(), new Empty<>());
  }

  @Test
  public void testNotEqualsEmptySingleCons() {
    Assert.assertNotEquals(Sequences.empty(), Sequences.of(1));
  }

  @Test
  public void testNotEqualsSingleCons() {
    Assert.assertNotEquals(Sequences.of(1), Sequences.of(2));
  }

  @Test
  public void testEqualsTwoCons() {
    Assert.assertEquals(Sequences.of("1", "2"), new Cons<>("1", new Cons<>("2", new Empty<>())));
  }

  @Test
  public void testNotEqualsSecondElem() {
    Assert.assertNotEquals(Sequences.of("1", "2"), Sequences.of("1", "3"));
  }

  @Test
  public void testNotEqualsMoreElems() {
    Assert.assertNotEquals(Sequences.of(1, 2), Sequences.of(1, 2, 3));
  }

  @Test
  public void testNotEqualsLessElems() {
    Assert.assertNotEquals(Sequences.of(1, 2, 3), Sequences.of(1, 2));
  }
}
