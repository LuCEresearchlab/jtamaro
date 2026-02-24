package jtamaro.graphic;

import java.util.ArrayList;
import java.util.List;

/**
 * Graphic render configuration.
 *
 * @apiNote Not for public use.
 * @hidden
 */
public final class RenderOptions {

  private final int fixedWidth;

  private final int fixedHeight;

  private final boolean drawBackground;

  private final List<Listener> listeners;

  private int padding;

  private Graphic selection;

  public RenderOptions(int padding) {
    this(padding, -1, -1, true);
  }

  public RenderOptions(
      int padding,
      int fixedWidth,
      int fixedHeight,
      boolean drawBackground
  ) {
    this.padding = padding;
    this.fixedWidth = fixedWidth;
    this.fixedHeight = fixedHeight;
    this.drawBackground = drawBackground;
    this.selection = null;
    this.listeners = new ArrayList<>();
  }

  public int getPadding() {
    return padding;
  }

  public void setPadding(int padding) {
    this.padding = padding;
    fireRenderOptionsChanged();
  }

  public boolean hasFixedSize() {
    return fixedWidth >= 0 || fixedHeight >= 0;
  }

  public int getFixedWidth() {
    return fixedWidth;
  }

  public int getFixedHeight() {
    return fixedHeight;
  }

  public boolean shouldDrawBackground() {
    return drawBackground;
  }

  public void clearSelection() {
    selection = null;
    fireRenderOptionsChanged();
  }

  void select(Graphic g) {
    selection = g;
    fireRenderOptionsChanged();
  }

  boolean isSelected(Graphic g) {
    return selection == g;
  }

  Graphic getSelection() {
    return selection;
  }

  public void addRenderOptionsListener(Listener li) {
    listeners.add(li);
  }

  private void fireRenderOptionsChanged() {
    for (final Listener listener : listeners) {
      listener.renderOptionsChanged();
    }
  }

  /**
   * Graphic render configuration listener.
   *
   * @apiNote For internal usage only!
   * @hidden
   */
  public interface Listener {

    void renderOptionsChanged();
  }
}
