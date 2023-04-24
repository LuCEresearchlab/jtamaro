package jtamaro.internal.gui;

import jtamaro.internal.representation.GraphicImpl;

import java.util.*;


public final class RenderOptions {

  private int padding;
  private final int fixedWidth;
  private final int fixedHeight;
  private final Set<GraphicImpl> selection;
  private GraphicImpl leadSelection;
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

  public void select(GraphicImpl g) {
    selection.add(g);
    leadSelection = g;
    fireRenderOptionsChanged();
  }

  public void deselect(GraphicImpl g) {
    if (g == leadSelection) {
      leadSelection = null;
    }
    selection.remove(g);
    fireRenderOptionsChanged();
  }

  public boolean isSelected(GraphicImpl g) {
    return selection.contains(g);
  }

  public GraphicImpl getLeadSelection() {
    return leadSelection;
  }

  public boolean isLeadSelection(GraphicImpl g) {
    return selection.contains(g);
  }

  public void addRenderOptionsListener(RenderOptionsListener li) {
    listeners.add(li);
  }

  private void fireRenderOptionsChanged() {
    for (RenderOptionsListener listener : listeners) {
      listener.renderOptionsChanged();
    }
  }

}
