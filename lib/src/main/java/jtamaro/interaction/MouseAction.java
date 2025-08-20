package jtamaro.interaction;

import jtamaro.data.Function2;

/**
 * Mouse actions for an {@link Interaction}.
 *
 * @param <M> Type of the model of the interaction
 * @see jtamaro.graphic.Actionable
 * @see MousePressAction
 * @see MouseReleaseAction
 * @see MouseMoveAction
 * @see MouseDragAction
 */
public interface MouseAction<M> extends Function2<Coordinate, MouseButton, M> {

}
