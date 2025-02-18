package jtamaro.graphic;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.SequencedMap;

/**
 * A graphic composed by placing the two graphics one besides the other. The two graphics are
 * vertically centered.
 *
 * <p>The pinning position of the new graphic is at its center.
 */
final class Beside extends DelegatingGraphic {

  private final Graphic left;

  private final Graphic right;

  /**
   * Default constructor.
   *
   * @param left  graphic to place on the left
   * @param right graphic to place on the right
   */
  public Beside(Graphic left, Graphic right) {
    super(
        new Pin(Points.CENTER, new Compose(
            new Pin(Points.CENTER_RIGHT, left),
            new Pin(Points.CENTER_LEFT, right)
        ))
    );
    this.left = left;
    this.right = right;
  }

  public Graphic getLeft() {
    return left;
  }

  public Graphic getRight() {
    return right;
  }

  @Override
  SequencedMap<String, Graphic> getChildren() {
    final SequencedMap<String, Graphic> children = new LinkedHashMap<>();
    children.put("left", left);
    children.put("right", right);
    return children;
  }

  @Override
  protected String getInspectLabel() {
    return "beside";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Beside that
        && Objects.equals(left, that.left)
        && Objects.equals(right, that.right));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Beside.class, left, right);
  }
}
