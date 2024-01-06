package jtamaro;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;


public class SequenceReduceTest {

  //--- foldLeft
  @Test
  public void testFoldLeftEmpty() {
    assertEquals(0, foldLeft(0, (a, e) -> a, of()));
  }

  @Test
  public void testFoldLeftMany() {
    assertEquals(110, foldLeft(100, (a, e) -> a + 1, range(10)));
  }

  @Test
  public void testFoldLeftManyNoncommutative() {
    assertEquals("ABCD", foldLeft("", (a, e) -> a + e, rangeClosed('A', 'D')));
  }

  @Test
  public void testFoldLeftManyNoncommutativeReverse() {
    assertEquals("DCBA", foldLeft("", (a, e) -> e + a, rangeClosed('A', 'D')));
  }

  //--- foldRight
  @Test
  public void testFoldRightEmpty() {
    assertEquals(0, foldRight(0, (e, a) -> a, of()));
  }

  @Test
  public void testFoldRightMany() {
    assertEquals(110, foldRight(100, (e, a) -> a + 1, range(10)));
  }

  @Test
  public void testFoldRightManyNoncommutative() {
    assertEquals("ABCD", foldRight("", (e, a) -> e + a, rangeClosed('A', 'D')));
  }

  @Test
  public void testFoldRightManyNoncommutativeReverse() {
    assertEquals("DCBA", foldRight("", (e, a) -> a + e, rangeClosed('A', 'D')));
  }

  //--- reduce
  @Test
  public void testReduceEmpty() {
    assertEquals(0, reduce(0, (e, a) -> a, of()));
  }

  @Test
  public void testReduceMany() {
    assertEquals(110, reduce(100, (e, a) -> a + 1, range(10)));
  }

  @Test
  public void testReduceManyNoncommutative() {
    assertEquals("ABCD", reduce("", (e, a) -> e + a, rangeClosed('A', 'D')));
  }

  @Test
  public void testReduceManyNoncommutativeReverse() {
    assertEquals("DCBA", reduce("", (e, a) -> a + e, rangeClosed('A', 'D')));
  }
  
}
