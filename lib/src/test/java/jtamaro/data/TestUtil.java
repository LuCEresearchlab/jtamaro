package jtamaro.data;

import java.util.Objects;
import org.junit.Assert;

import static jtamaro.data.Sequences.foldRight;
import static jtamaro.data.Sequences.intersperse;
import static jtamaro.data.Sequences.map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

final class TestUtil {

  public static <T> void assertSequenceEquals(Sequence<T> expected, Sequence<T> actual) {
    Sequence<T> e = expected;
    Sequence<T> a = actual;
    int i = 0;
    while (!e.isEmpty() && !a.isEmpty()) {
      Assert.assertEquals("Element at index " + i + " does not match", e.first(), a.first());
      e = e.rest();
      a = a.rest();
      i++;
    }
    Assert.assertTrue("There are more elements in expected: " + toString(e), e.isEmpty());
    Assert.assertTrue("There are more elements in actual: " + toString(a), a.isEmpty());
  }

  private static <T> String toString(Sequence<T> seq) {
    return Sequences.foldRight(
        "]",
        String::concat,
        new Cons<>("[", Sequences.intersperse(", ", Sequences.map(Objects::toString, seq)))
    );
  }
}
