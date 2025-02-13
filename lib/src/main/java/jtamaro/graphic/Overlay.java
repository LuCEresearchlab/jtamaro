package jtamaro.graphic;

import java.util.Objects;
import javax.swing.tree.MutableTreeNode;

/**
 * A graphic composed by overlaying the two provided graphics, keeping the first one in the
 * foreground and the second one in background. The two graphics are overlaid on their centers.
 *
 * <p>The pinning position of the new graphic is at its center.
 */
final class Overlay extends DelegatingGraphic {

  private final Graphic foreground;

  private final Graphic background;

  /**
   * Default constructor.
   *
   * @param foreground graphic in the foreground
   * @param background graphic in the background
   */
  public Overlay(Graphic foreground, Graphic background) {
    super(new Compose(
        new Pin(Points.CENTER, foreground),
        new Pin(Points.CENTER, background)
    ));
    this.foreground = foreground;
    this.background = background;
  }

  public Graphic getForeground() {
    return foreground;
  }

  public Graphic getBackground() {
    return background;
  }

  @Override
  public MutableTreeNode createInspectTree() {
    return new InspectTreeNode(foreground, background);
  }

  @Override
  protected void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    dumpChild(sb, indent, "foreground", foreground);
    dumpChild(sb, indent, "background", background);
  }

  @Override
  protected String getInspectLabel() {
    return "overlay";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Overlay that
        && Objects.equals(that.foreground, foreground)
        && Objects.equals(that.background, background));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Overlay.class, foreground, background);
  }
}
