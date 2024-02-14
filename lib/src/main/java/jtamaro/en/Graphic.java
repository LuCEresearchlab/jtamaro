package jtamaro.en;


/**
 * A Graphic (image) with a position for pinning.
 *
 * <p>The pinning position is used in the following operations:
 *
 * <ul>
 *   <li>rotation (to determine the center of rotation)</li>
 *   <li>graphic composition (two graphics get composed aligning their pinning position)</li>
 * </ul>
 */
public interface Graphic {

  double getWidth();

  double getHeight();

}
