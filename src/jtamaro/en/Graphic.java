package jtamaro.en;


/**
 * A Graphic (image) with a position for pinning.
 * The pinning position is used in the following operations:
 * - rotation (to determine the center of rotation)
 * - graphic composition (two graphics get composed aligning their pinning position).
 */
public interface Graphic {
  
  public double getWidth();
  public double getHeight();

}
