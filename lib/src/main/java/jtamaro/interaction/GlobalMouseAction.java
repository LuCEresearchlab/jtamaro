package jtamaro.interaction;

import jtamaro.data.Function3;

/**
 * Global mouse actions for an {@link Interaction}.
 *
 * <p>Unlike {@link MouseAction}, this interface is used for events dispatched that are not on a
 * specific {@link jtamaro.graphic.Actionable}, but on the whole canvas (e.g., the coordinates are
 * global).
 *
 * @param <M> Type of the model of the interaction
 * @see jtamaro.interaction.Interaction#withGlobalMouseMoveHandler(GlobalMouseMoveAction)
 * @see jtamaro.interaction.Interaction#withGlobalMousePressHandler(GlobalMousePressAction)
 * @see jtamaro.interaction.Interaction#withGlobalMouseReleaseHandler(GlobalMouseReleaseAction)
 * @see GlobalMousePressAction
 * @see GlobalMouseReleaseAction
 * @see GlobalMouseMoveAction
 */
public interface GlobalMouseAction<M> extends Function3<M, Coordinate, MouseButton, M> {

}
