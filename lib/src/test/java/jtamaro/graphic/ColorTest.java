package jtamaro.graphic;

import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

  private void assertColor(int r, int g, int b, int a, Color actual) {
    Assert.assertEquals(new Color(r, g, b, a / 255.0), actual);
  }

  @Test
  public void testRgbaTransparentBlack() {
    assertColor(0, 0, 0, 0, Colors.rgba(0, 0, 0, 0));
  }

  @Test
  public void testRgbaOpaqueBlack() {
    assertColor(0, 0, 0, 255, Colors.rgba(0, 0, 0, 1.0));
  }

  @Test
  public void testRgbBlack() {
    assertColor(0, 0, 0, 255, Colors.rgb(0, 0, 0));
  }

  @Test
  public void testRgbWhite() {
    assertColor(255, 255, 255, 255, Colors.rgb(255, 255, 255));
  }

  @Test
  public void testRgbRed() {
    assertColor(255, 0, 0, 255, Colors.rgb(255, 0, 0));
  }

  @Test
  public void testRgbGreen() {
    assertColor(0, 255, 0, 255, Colors.rgb(0, 255, 0));
  }

  @Test
  public void testRgbBlue() {
    assertColor(0, 0, 255, 255, Colors.rgb(0, 0, 255));
  }

  @Test
  public void testRgbCyan() {
    assertColor(0, 255, 255, 255, Colors.rgb(0, 255, 255));
  }

  @Test
  public void testRgbMagenta() {
    assertColor(255, 0, 255, 255, Colors.rgb(255, 0, 255));
  }

  @Test
  public void testRgbYellow() {
    assertColor(255, 255, 0, 255, Colors.rgb(255, 255, 0));
  }

  //-- Color Constants
  @Test
  public void testTransparent() {
    assertColor(0, 0, 0, 0, Colors.TRANSPARENT);
  }

  @Test
  public void testBlack() {
    assertColor(0, 0, 0, 255, Colors.BLACK);
  }

  @Test
  public void testWhite() {
    assertColor(255, 255, 255, 255, Colors.WHITE);
  }

  @Test
  public void testRed() {
    assertColor(255, 0, 0, 255, Colors.RED);
  }

  @Test
  public void testGreen() {
    assertColor(0, 255, 0, 255, Colors.GREEN);
  }

  @Test
  public void testBlue() {
    assertColor(0, 0, 255, 255, Colors.BLUE);
  }

  @Test
  public void testCyan() {
    assertColor(0, 255, 255, 255, Colors.CYAN);
  }

  @Test
  public void testMagenta() {
    assertColor(255, 0, 255, 255, Colors.MAGENTA);
  }

  @Test
  public void testYellow() {
    assertColor(255, 255, 0, 255, Colors.YELLOW);
  }

  //--- Color.toString
  @Test
  public void testToStringBlack() {
    Assert.assertEquals("Color[rgb=#000000, opacity=1.00]", Colors.rgba(0, 0, 0, 1.0).toString());
  }

  @Test
  public void testToStringBlackTransparent() {
    Assert.assertEquals("Color[rgb=#000000, opacity=0.00]", Colors.rgba(0, 0, 0, 0.0).toString());
  }

  @Test
  public void testToStringRed() {
    Assert.assertEquals("Color[rgb=#FF0000, opacity=1.00]", Colors.rgba(255, 0, 0, 1.0).toString());
  }

  @Test
  public void testToStringGreen() {
    Assert.assertEquals("Color[rgb=#00FF00, opacity=1.00]", Colors.rgba(0, 255, 0, 1.0).toString());
  }

  @Test
  public void testToStringBlue() {
    Assert.assertEquals("Color[rgb=#0000FF, opacity=1.00]", Colors.rgba(0, 0, 255, 1.0).toString());
  }
}
