package jtamaro.graphic;

import java.util.Objects;
import javax.swing.tree.MutableTreeNode;

/**
 * A graphic composed by placing the two graphics one above the other. The two graphics are
 * horizontally centered.
 *
 * <p>The pinning position of the new graphic is at its center.
 */
final class Above extends DelegatingGraphic {

  private final Graphic top;

  private final Graphic bottom;

  /**
   * Default constructor.
   *
   * @param top    graphic to place on the top
   * @param bottom graphic to place on the bottom
   */
  public Above(Graphic top, Graphic bottom) {
    super(
        new Pin(Points.CENTER, new Compose(
            new Pin(Points.BOTTOM_CENTER, top),
            new Pin(Points.TOP_CENTER, bottom)
        ))
    );
    this.top = top;
    this.bottom = bottom;
  }

  public Graphic getTop() {
    return top;
  }

  public Graphic getBottom() {
    return bottom;
  }

  @Override
  public MutableTreeNode createInspectTree() {
    return new InspectTreeNode(top, bottom);
  }

  @Override
  protected void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    dumpChild(sb, indent, "top", top);
    dumpChild(sb, indent, "bottom", bottom);
  }

  @Override
  protected String getInspectLabel() {
    return "above";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Above that
        && Objects.equals(top, that.top)
        && Objects.equals(bottom, that.bottom));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Above.class, top, bottom);
  }
}
