package jtamaro;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;


public class SequenceReduceTest {

  @Test
  public void testReduceEmpty() {
    assertEquals(0, reduce((a,e)->a, 0, of()));
  }

  @Test
  public void testReduceMany() {
    assertEquals(110, reduce((a,e)->a+1, 100, range(10)));
  }

  @Test
  public void testReduceManyNoncommutative() {
    assertEquals("ABCD", reduce((a,e)->a+e, "", rangeClosed('A', 'D')));
  }

  @Test
  public void testReduceManyNoncommutativeReverse() {
    assertEquals("DCBA", reduce((a,e)->e+a, "", rangeClosed('A', 'D')));
  }
  
}
