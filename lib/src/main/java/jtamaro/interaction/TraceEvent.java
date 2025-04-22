package jtamaro.interaction;

import jtamaro.data.Function1;
import jtamaro.data.Function2;
import jtamaro.data.Function3;

sealed abstract class TraceEvent<M> {

  private final long timeStamp;

  public TraceEvent() {
    timeStamp = System.currentTimeMillis();
  }

  public final long getTimeStamp() {
    return timeStamp;
  }

  public String getKind() {
    return getClass().getSimpleName();
  }

  public abstract M process(M model);

  public static final class Tick<M> extends TraceEvent<M> {

    private final Function1<M, M> tickHandler;

    public Tick(Function1<M, M> tickHandler) {
      this.tickHandler = tickHandler;
    }

    @Override
    public M process(M model) {
      return tickHandler.apply(model);
    }
  }

  public static final class KeyboardKeyEvent<M> extends TraceEvent<M> {

    private final KeyboardKey key;

    private final Function2<M, KeyboardKey, M> action;

    public KeyboardKeyEvent(KeyboardKey key, Function2<M, KeyboardKey, M> action) {
      super();
      this.key = key;
      this.action = action;
    }

    @Override
    public M process(M model) {
      return action.apply(model, key);
    }
  }

  public static final class KeyboardCharEvent<M> extends TraceEvent<M> {

    private final KeyboardChar key;

    private final Function2<M, KeyboardChar, M> action;

    public KeyboardCharEvent(KeyboardChar key, Function2<M, KeyboardChar, M> action) {
      super();
      this.key = key;
      this.action = action;
    }

    @Override
    public M process(M model) {
      return action.apply(model, key);
    }
  }

  public static final class MouseEvent<M> extends TraceEvent<M> {

    private final String kind;

    private final Coordinate coordinate;

    private final MouseButton button;

    private final Function3<M, Coordinate, MouseButton, M> action;


    public MouseEvent(
        String kind,
        Coordinate coordinate,
        MouseButton button,
        Function3<M, Coordinate, MouseButton, M> action
    ) {
      super();
      this.kind = kind;
      this.coordinate = coordinate;
      this.button = button;
      this.action = action;
    }

    @Override
    public M process(M model) {
      return action.apply(model, coordinate, button);
    }

    @Override
    public String getKind() {
      return kind;
    }
  }
}
