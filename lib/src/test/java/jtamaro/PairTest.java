package jtamaro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Pairs.*;


public class PairTest {

  @Test
  public void testPairFirstElement() {
    assertEquals(firstElement(pair(1, 2)), 1);
  }


  @Test
  public void testPairSecondElement() {
    assertEquals(secondElement(pair(1, 2)), 2);
  }

}
