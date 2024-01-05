package jtamaro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jtamaro.en.Color;

import static jtamaro.en.Colors.*;


public class ColorTest {
  
  private void assertColor(int r, int g, int b, int a, Color actual) {
    assertEquals("rgba("+r+", "+g+", "+b+", "+a+")", actual.getImplementation().toString());
  }


  @Test
  public void testRgbaTransparentBlack() {
    assertColor(0, 0, 0, 0, rgb(0, 0, 0, 0));
  }

  @Test
  public void testRgbaOpaqueBlack() {
    assertColor(0, 0, 0, 255, rgb(0, 0, 0, 1.0));
  }

  @Test
  public void testRgbBlack() {
    assertColor(0, 0, 0, 255, rgb(0, 0, 0));
  }

  @Test
  public void testRgbWhite() {
    assertColor(255, 255, 255, 255, rgb(255, 255, 255));
  }

  @Test
  public void testRgbRed() {
    assertColor(255, 0, 0, 255, rgb(255, 0, 0));
  }

  @Test
  public void testRgbGreen() {
    assertColor(0, 255, 0, 255, rgb(0, 255, 0));
  }

  @Test
  public void testRgbBlue() {
    assertColor(0, 0, 255, 255, rgb(0, 0, 255));
  }

  @Test
  public void testRgbCyan() {
    assertColor(0, 255, 255, 255, rgb(0, 255, 255));
  }

  @Test
  public void testRgbMagenta() {
    assertColor(255, 0, 255, 255, rgb(255, 0, 255));
  }

  @Test
  public void testRgbYellow() {
    assertColor(255, 255, 0, 255, rgb(255, 255, 0));
  }

  //-- Color Constants
  @Test
  public void testTransparent() {
    assertColor(0, 0, 0, 0, TRANSPARENT);
  }

  @Test
  public void testBlack() {
    assertColor(0, 0, 0, 255, BLACK);
  }

  @Test
  public void testWhite() {
    assertColor(255, 255, 255, 255, WHITE);
  }

  @Test
  public void testRed() {
    assertColor(255, 0, 0, 255, RED);
  }

  @Test
  public void testGreen() {
    assertColor(0, 255, 0, 255, GREEN);
  }

  @Test
  public void testBlue() {
    assertColor(0, 0, 255, 255, BLUE);
  }

  @Test
  public void testCyan() {
    assertColor(0, 255, 255, 255, CYAN);
  }

  @Test
  public void testMagenta() {
    assertColor(255, 0, 255, 255, MAGENTA);
  }

  @Test
  public void testYellow() {
    assertColor(255, 255, 0, 255, YELLOW);
  }

  //--- Color.toString
  @Test
  public void testToStringBlack() {
    assertEquals("rgb(0, 0, 0, 1.0)", rgb(0, 0, 0, 1.0).toString());
  }

  @Test
  public void testToStringBlackTransparent() {
    assertEquals("rgb(0, 0, 0, 0.0)", rgb(0, 0, 0, 0.0).toString());
  }

  @Test
  public void testToStringRed() {
    assertEquals("rgb(255, 0, 0, 1.0)", rgb(255, 0, 0, 1.0).toString());
  }

  @Test
  public void testToStringGreen() {
    assertEquals("rgb(0, 255, 0, 1.0)", rgb(0, 255, 0, 1.0).toString());
  }

  @Test
  public void testToStringBlue() {
    assertEquals("rgb(0, 0, 255, 1.0)", rgb(0, 0, 255, 1.0).toString());
  }

}
