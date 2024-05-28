package jtamaro.graphic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

/**
 * Graphic render configuration.
 *
 * @apiNote Not for public use.
 * @hidden
 */
public final class RenderOptions {

  private int padding;

  private final int fixedWidth;

  private final int fixedHeight;

  private final Set<Graphic> selection;

  private Graphic leadSelection;

  private final List<RenderOptionsListener> listeners;

  public RenderOptions(int padding) {
    this(padding, -1, -1);
  }

  public RenderOptions(int padding, int fixedWidth, int fixedHeight) {
    this.padding = padding;
    this.fixedWidth = fixedWidth;
    this.fixedHeight = fixedHeight;
    selection = Collections.newSetFromMap(new IdentityHashMap<>());
    listeners = new ArrayList<>();
  }

  public void setPadding(int padding) {
    this.padding = padding;
    fireRenderOptionsChanged();
  }

  public int getPadding() {
    return padding;
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

  public void clearSelection() {
    selection.clear();
    leadSelection = null;
    fireRenderOptionsChanged();
  }

  void select(Graphic g) {
    selection.add(g);
    leadSelection = g;
    fireRenderOptionsChanged();
  }

  void deselect(Graphic g) {
    if (g == leadSelection) {
      leadSelection = null;
    }
    selection.remove(g);
    fireRenderOptionsChanged();
  }

  boolean isSelected(Graphic g) {
    return selection.contains(g);
  }

  Graphic getLeadSelection() {
    return leadSelection;
  }

  boolean isLeadSelection(Graphic g) {
    return selection.contains(g);
  }

  public void addRenderOptionsListener(RenderOptionsListener li) {
    listeners.add(li);
  }

  private void fireRenderOptionsChanged() {
    for (final RenderOptionsListener listener : listeners) {
      listener.renderOptionsChanged();
    }
  }
}
