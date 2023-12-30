package jtamaro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static jtamaro.en.Colors.BLACK;
import static jtamaro.en.Graphics.*;

import org.junit.jupiter.api.Test;

public class GraphicTest {
  
  private static final double DELTA = 0.0001;


  @Test
  public void testEmptyGraphicIs0x0() {
    var graphic = emptyGraphic();
    assertEquals(0.0, width(graphic), DELTA);
    assertEquals(0.0, height(graphic), DELTA);
  }

  @Test
  public void test0x0RectangleIs0x0() {
    var graphic = rectangle(0, 0, BLACK);
    assertEquals(0.0, width(graphic), DELTA);
    assertEquals(0.0, height(graphic), DELTA);
  }

  @Test
  public void test1x1RectangleIs1x1() {
    var graphic = rectangle(1, 1, BLACK);
    assertEquals(1.0, width(graphic), DELTA);
    assertEquals(1.0, height(graphic), DELTA);
  }

}
