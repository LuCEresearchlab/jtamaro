package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.replicate;
import static org.junit.Assert.assertTrue;

public class SequenceReplicateTest {

  //--- repeat
  @Test
  public void testReplicateZero() {
    assertSequenceEquals(of(), replicate("A", 0));
  }

  @Test
  public void testReplicate() {
    assertSequenceEquals(of("A", "A", "A", "A", "A"), replicate("A", 5));
  }

  @Test
  public void testReplicateHasDefiniteSize() {
    assertTrue(replicate("A", 5).hasDefiniteSize());
  }

}
