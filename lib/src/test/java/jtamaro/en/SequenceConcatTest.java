package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.concat;
import static jtamaro.en.Sequences.of;

public class SequenceConcatTest {

  @Test
  public void testConcat() {
    assertSequenceEquals(of(1, 2, 3, 4, 5), concat(of(1, 2, 3), of(4, 5)));
  }

}
