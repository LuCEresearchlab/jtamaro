package jtamaro.interaction;

/**
 * Global mouse movement action for an {@link Interaction}.
 *
 * <p>Unlike {@link MouseMoveAction}, this interface is used for events dispatched that are not on a
 * specific {@link jtamaro.graphic.Actionable}, but on the whole canvas (e.g., the coordinates are
 * global).
 *
 * @param <M> Type of the model of the interaction
 * @see jtamaro.interaction.Interaction#withGlobalMouseMoveHandler(GlobalMouseMoveAction)
 */
public interface GlobalMouseReleaseAction<M> extends GlobalMouseAction<M> {

}
