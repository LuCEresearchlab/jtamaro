package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.*;
import static org.junit.Assert.assertTrue;

public class SequenceRangeClosedTest {

  //--- rangeClosed(int)
  @Test
  public void testRangeClosed0() {
    assertSequenceEquals(of(0), rangeClosed(0));
  }

  @Test
  public void testRangeClosed1() {
    assertSequenceEquals(of(0, 1), rangeClosed(1));
  }

  @Test
  public void testRangeClosed2() {
    assertSequenceEquals(of(0, 1, 2), rangeClosed(2));
  }

  @Test
  public void testRangeClosed3() {
    assertSequenceEquals(of(0, 1, 2, 3), rangeClosed(3));
  }

  @Test
  public void testRangeClosedIntHasDefiniteSize() {
    assertTrue(rangeClosed(5).hasDefiniteSize());
  }


  //--- rangeClosed(int, int)
  @Test
  public void testRangeClosed0_3() {
    assertSequenceEquals(of(0, 1, 2, 3), rangeClosed(0, 3));
  }

  @Test
  public void testRangeClosed1_3() {
    assertSequenceEquals(of(1, 2, 3), rangeClosed(1, 3));
  }

  @Test
  public void testRangeClosed2_3() {
    assertSequenceEquals(of(2, 3), rangeClosed(2, 3));
  }

  @Test
  public void testRangeClosed3_3() {
    assertSequenceEquals(of(3), rangeClosed(3, 3));
  }

  @Test
  public void testRangeClosed4_3() {
    assertSequenceEquals(of(), rangeClosed(4, 3));
  }

  @Test
  public void testRangeClosedIntIntHasDefiniteSize() {
    assertTrue(rangeClosed(0, 5).hasDefiniteSize());
  }


  //--- rangeClosed(int, int, int)
  @Test
  public void testRangeClosed0_3_2() {
    assertSequenceEquals(of(0, 2), rangeClosed(0, 3, 2));
  }

  @Test
  public void testRangeClosed1_3_2() {
    assertSequenceEquals(of(1, 3), rangeClosed(1, 3, 2));
  }

  @Test
  public void testRangeClosed2_3_2() {
    assertSequenceEquals(of(2), rangeClosed(2, 3, 2));
  }

  @Test
  public void testRangeClosed3_3_2() {
    assertSequenceEquals(of(3), rangeClosed(3, 3, 2));
  }

  @Test
  public void testRangeClosed0_3_3() {
    assertSequenceEquals(of(0, 3), rangeClosed(0, 3, 3));
  }

  @Test
  public void testRangeClosed1_3_1() {
    assertSequenceEquals(of(1, 2, 3), rangeClosed(1, 3, 1));
  }

  @Test
  public void testRangeClosed3_0_m1() {
    assertSequenceEquals(of(3, 2, 1, 0), rangeClosed(3, 0, -1));
  }

  @Test
  public void testRangeClosed3_0_m2() {
    assertSequenceEquals(of(3, 1), rangeClosed(3, 0, -2));
  }

  @Test
  public void testRangeClosed2_0_m3() {
    assertSequenceEquals(of(2), rangeClosed(2, 0, -3));
  }

  @Test
  public void testRangeClosed3_3_m1() {
    assertSequenceEquals(of(3), rangeClosed(3, 3, -3));
  }

  @Test
  public void testRangeClosedIntIntIntHasDefiniteSize() {
    assertTrue(rangeClosed(0, 5, 1).hasDefiniteSize());
  }


  //--- range(char, char)
  @Test
  public void testRangeClosedChar2() {
    assertSequenceEquals(of((char) 0, (char) 1, (char) 2), rangeClosed((char) 2));
  }

  @Test
  public void testRangeClosedA_F() {
    assertSequenceEquals(of('A', 'B', 'C', 'D', 'E', 'F'), rangeClosed('A', 'F'));
  }

  @Test
  public void testRangeClosedA_A() {
    assertSequenceEquals(of('A'), rangeClosed('A', 'A'));
  }

  @Test
  public void testRangeClosedCharCharHasDefiniteSize() {
    assertTrue(rangeClosed('A', 'Z').hasDefiniteSize());
  }

}
