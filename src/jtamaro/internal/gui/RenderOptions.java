package jtamaro.internal.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import jtamaro.internal.representation.GraphicImpl;


public final class RenderOptions {
  
  private int padding;
  private final Set<GraphicImpl> selection;
  private GraphicImpl leadSelection;
  private final List<RenderOptionsListener> listeners;

  
  public RenderOptions(int padding) {
    this.padding = padding;
    selection = Collections.newSetFromMap(new IdentityHashMap<>());
    listeners = new ArrayList<RenderOptionsListener>();
  }

  public void setPadding(int padding) {
    this.padding = padding;
    fireRenderOptionsChanged();
  }
  
  public int getPadding() {
    return padding;
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
