package jtamaro.graphic;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.SequencedMap;
import jtamaro.data.Option;
import jtamaro.interaction.MouseDragAction;
import jtamaro.interaction.MouseMoveAction;
import jtamaro.interaction.MousePressAction;
import jtamaro.interaction.MouseReleaseAction;

final class ActionableGraphic<T> extends DelegatingGraphic {

  private final MousePressAction<T> pressAction;

  private final MouseReleaseAction<T> releaseAction;

  private final MouseMoveAction<T> moveAction;

  private final MouseDragAction<T> dragAction;

  public ActionableGraphic(
      MousePressAction<T> pressAction,
      MouseReleaseAction<T> releaseAction,
      MouseMoveAction<T> moveAction,
      MouseDragAction<T> dragAction,
      Graphic graphic
  ) {
    super(graphic);
    this.pressAction = pressAction;
    this.releaseAction = releaseAction;
    this.moveAction = moveAction;
    this.dragAction = dragAction;
  }

  public MousePressAction<T> getPressAction() {
    return pressAction;
  }

  public MouseReleaseAction<T> getReleaseAction() {
    return releaseAction;
  }

  public MouseMoveAction<T> getMoveAction() {
    return moveAction;
  }

  public MouseDragAction<T> getDragAction() {
    return dragAction;
  }

  @Override
  Option<RelativeLocation> relativeLocationOf(double x, double y) {
    return delegate.relativeLocationOf(x, y)
        // "Eat" non-actionable graphic children
        .map(it -> it.isGraphicActionable()
            ? it
            : new RelativeLocation(this, x, y));
  }

  @Override
  SequencedMap<String, Graphic> getChildren() {
    final SequencedMap<String, Graphic> children = new LinkedHashMap<>();
    children.put("graphic", delegate);
    return children;
  }

  @Override
  protected String getInspectLabel() {
    return "actionable";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof ActionableGraphic<?> that
        && Objects.equals(that.pressAction, pressAction)
        && Objects.equals(that.releaseAction, releaseAction)
        && Objects.equals(that.moveAction, moveAction)
        && Objects.equals(that.dragAction, dragAction)
        && Objects.equals(that.delegate, delegate));
  }
}
