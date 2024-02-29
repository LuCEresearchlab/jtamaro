package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.Pairs.firstElement;
import static jtamaro.en.Pairs.pair;
import static jtamaro.en.Pairs.secondElement;
import static org.junit.Assert.assertEquals;


public class PairTest {

  @Test
  public void testPairFirstElement() {
    assertEquals(1L, (long) firstElement(pair(1, 2)));
  }


  @Test
  public void testPairSecondElement() {
    assertEquals(2L, (long) secondElement(pair(1, 2)));
  }

}
