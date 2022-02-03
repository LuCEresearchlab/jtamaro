package jtamaro.internal.representation;


public final class ColorImpl {

  private final int red;
  private final int green;
  private final int blue;
  private final int alpha;


  public ColorImpl(int red, int green, int blue, int alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  public java.awt.Color toAWT() {
    return new java.awt.Color(red, green, blue, alpha);
  }

  @Override
  public String toString() {
    return "rgba(" + red + ", " + green + ", " + blue + ", " + alpha +")";
  }

}
