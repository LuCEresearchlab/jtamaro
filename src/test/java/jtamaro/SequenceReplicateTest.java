package jtamaro;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;
import static jtamaro.SequenceTest.*;


public class SequenceReplicateTest {

  //--- repeat
  @Test
  public void testReplicateZero() {
    assertSequenceEquals(of(), replicate("A", 0));
  }

  public void testReplicate() {
    assertSequenceEquals(of("A", "A", "A", "A", "A"), replicate("A", 5));
  }

  @Test
  public void testReplicateHasDefiniteSize() {
    assertTrue(replicate("A", 5).hasDefiniteSize());
  }
  
}
