package jtamaro;


import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;
import static jtamaro.SequenceTest.*;


public class SequenceDropTakeTest {

  //--- drop
  @Test
  public void testDropNone() {
    assertSequenceEquals(of(10, 20, 30), drop(0, of(10, 20, 30)));
  }

  @Test
  public void testDropOne() {
    assertSequenceEquals(of(20, 30), drop(1, of(10, 20, 30)));
  }  

  @Test
  public void testDropAll() {
    assertSequenceEquals(of(), drop(3, of(10, 20, 30)));
  }

  @Test
  public void testDropMore() {
    assertSequenceEquals(of(), drop(4, of(10, 20, 30)));
  }

  @Test
  public void testDropString() {
    assertSequenceEquals(of("Ho"), drop(1, of("Hi", "Ho")));
  }

  @Test
  public void testDropOfHasDefiniteSize() {
    assertTrue(drop(1, of(1, 2, 3)).hasDefiniteSize());
  }

  @Test
  public void testDropRepeatHasDefiniteSize() {
    assertFalse(drop(1, repeat(5)).hasDefiniteSize());
  }


  //--- take
  @Test
  public void testTakeNone() {
    assertSequenceEquals(of(), take(0, of(10, 20, 30)));
  }

  @Test
  public void testTakeOne() {
    assertSequenceEquals(of(10), take(1, of(10, 20, 30)));
  }  

  @Test
  public void testTakeAll() {
    assertSequenceEquals(of(10, 20, 30), take(3, of(10, 20, 30)));
  }

  @Test
  public void testTakeMore() {
    assertSequenceEquals(of(10, 20, 30), take(4, of(10, 20, 30)));
  }

  @Test
  public void testTakeString() {
    assertSequenceEquals(of("Hi"), take(1, of("Hi", "Ho")));
  }

  @Test
  public void testTakeOfHasDefiniteSize() {
    assertTrue(take(1, of(1, 2, 3)).hasDefiniteSize());
  }

  @Test
  public void testTakeRepeatHasDefiniteSize() {
    assertTrue(take(1, repeat(5)).hasDefiniteSize());
  }

}
