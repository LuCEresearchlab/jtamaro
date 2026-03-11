package jtamaro.data;

import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

public final class SingleElementIteratorTest {

  @Test
  public void nextOnce() {
    final String element = "element";
    final SingleElementIterator<String> itr = new SingleElementIterator<>(element);

    Assert.assertEquals(element, itr.next());
  }

  @SuppressWarnings("WriteOnlyObject")
  @Test(expected = NoSuchElementException.class)
  public void nextTwice() {
    final SingleElementIterator<Integer> itr = new SingleElementIterator<>(1);
    try {
      itr.next();
    } catch (Exception err) {
      Assert.fail("Failed to retrieve the first element: " + err.getMessage());
    }
    itr.next();
  }

  @Test
  public void hasNextFirst() {
    final SingleElementIterator<Boolean> itr = new SingleElementIterator<>(true);
    Assert.assertTrue(itr.hasNext());
  }

  @Test
  public void hasNextSecond() {
    final SingleElementIterator<Boolean> itr = new SingleElementIterator<>(true);
    itr.next();
    Assert.assertFalse(itr.hasNext());
  }
}
