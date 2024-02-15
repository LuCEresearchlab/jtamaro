package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.*;
import static org.junit.Assert.assertTrue;

public class SequenceRangeTest {

  //--- range(int)
  @Test
  public void testRange0() {
    assertSequenceEquals(of(), range(0));
  }

  @Test
  public void testRange1() {
    assertSequenceEquals(of(0), range(1));
  }

  @Test
  public void testRange2() {
    assertSequenceEquals(of(0, 1), range(2));
  }

  @Test
  public void testRange3() {
    assertSequenceEquals(of(0, 1, 2), range(3));
  }

  @Test
  public void testRangeIntHasDefiniteSize() {
    assertTrue(range(5).hasDefiniteSize());
  }

  //--- range(int, int)
  @Test
  public void testRange0_3() {
    assertSequenceEquals(of(0, 1, 2), range(0, 3));
  }

  @Test
  public void testRange1_3() {
    assertSequenceEquals(of(1, 2), range(1, 3));
  }

  @Test
  public void testRange2_3() {
    assertSequenceEquals(of(2), range(2, 3));
  }

  @Test
  public void testRange3_3() {
    assertSequenceEquals(of(), range(3, 3));
  }

  @Test
  public void testRange4_3() {
    assertSequenceEquals(of(), range(4, 3));
  }

  @Test
  public void testRangeIntIntHasDefiniteSize() {
    assertTrue(range(0, 5).hasDefiniteSize());
  }


  //--- range(int, int, int)
  @Test
  public void testRange0_3_2() {
    assertSequenceEquals(of(0, 2), range(0, 3, 2));
  }

  @Test
  public void testRange1_3_2() {
    assertSequenceEquals(of(1), range(1, 3, 2));
  }

  @Test
  public void testRange2_3_2() {
    assertSequenceEquals(of(2), range(2, 3, 2));
  }

  @Test
  public void testRange3_3_2() {
    assertSequenceEquals(of(), range(3, 3, 2));
  }

  @Test
  public void testRange0_3_3() {
    assertSequenceEquals(of(0), range(0, 3, 3));
  }

  @Test
  public void testRange1_3_1() {
    assertSequenceEquals(of(1, 2), range(1, 3, 1));
  }

  @Test
  public void testRange3_0_m1() {
    assertSequenceEquals(of(3, 2, 1), range(3, 0, -1));
  }

  @Test
  public void testRange3_0_m2() {
    assertSequenceEquals(of(3, 1), range(3, 0, -2));
  }

  @Test
  public void testRange2_0_m3() {
    assertSequenceEquals(of(2), range(2, 0, -3));
  }

  @Test
  public void testRange3_3_m1() {
    assertSequenceEquals(of(), range(3, 3, -3));
  }

  @Test
  public void testRangeIntIntIntHasDefiniteSize() {
    assertTrue(range(0, 5, 1).hasDefiniteSize());
  }


  //--- range(char, char)
  @Test
  public void testRangeChar2() {
    assertSequenceEquals(of((char) 0, (char) 1), range((char) 2));
  }

  @Test
  public void testRangeA_G() {
    assertSequenceEquals(of('A', 'B', 'C', 'D', 'E', 'F'), range('A', 'G'));
  }

  @Test
  public void testRangeA_A() {
    assertSequenceEquals(of(), range('A', 'A'));
  }

  @Test
  public void testRangeCharCharHasDefiniteSize() {
    assertTrue(range('A', 'Z').hasDefiniteSize());
  }

}
