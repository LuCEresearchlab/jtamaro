package jtamaro;

import static jtamaro.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.*;

import org.junit.jupiter.api.Test;

public class SequenceConcatTest {

  @Test
  public void testConcat() {
    assertSequenceEquals(of(1, 2, 3, 4, 5), concat(of(1, 2, 3), of(4, 5)));
  }
  
}
