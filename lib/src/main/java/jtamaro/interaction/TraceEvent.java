package jtamaro.interaction;

sealed abstract class TraceEvent {

  private final long timeStamp;

  public TraceEvent() {
    timeStamp = System.currentTimeMillis();
  }

  public final long getTimeStamp() {
    return timeStamp;
  }

  public final String getKind() {
    return getClass().getSimpleName();
  }

  public abstract <M> M process(Interaction<M> interaction, M model);

  public static final class Tick extends TraceEvent {

    @Override
    public <M> M process(Interaction<M> interaction, M model) {
      return interaction.getTickHandler().apply(model);
    }
  }

  public static final class KeyPressed extends TraceEvent {

    private final KeyboardKey key;

    public KeyPressed(KeyboardKey key) {
      super();
      this.key = key;
    }

    @Override
    public <M> M process(Interaction<M> interaction, M model) {
      return interaction.getKeyPressHandler().apply(model, key);
    }
  }

  public static final class KeyReleased extends TraceEvent {

    private final KeyboardKey key;

    public KeyReleased(KeyboardKey key) {
      super();
      this.key = key;
    }

    @Override
    public <M> M process(Interaction<M> interaction, M model) {
      return interaction.getKeyReleaseHandler().apply(model, key);
    }
  }

  public static final class KeyTyped extends TraceEvent {

    private final KeyboardChar keyboardChar;

    public KeyTyped(KeyboardChar keyboardChar) {
      super();
      this.keyboardChar = keyboardChar;
    }

    @Override
    public <M> M process(Interaction<M> interaction, M model) {
      return interaction.getKeyTypeHandler().apply(model, keyboardChar);
    }
  }

  public static final class MousePressed extends TraceEvent {

    private final Coordinate coordinate;

    private final MouseButton button;

    public MousePressed(Coordinate coordinate, MouseButton button) {
      super();
      this.coordinate = coordinate;
      this.button = button;
    }

    @Override
    public <M> M process(Interaction<M> interaction, M model) {
      return interaction.getMousePressHandler().apply(model, coordinate, button);
    }
  }

  public static final class MouseReleased extends TraceEvent {

    private final Coordinate coordinate;

    private final MouseButton button;

    public MouseReleased(Coordinate coordinate, MouseButton button) {
      super();
      this.coordinate = coordinate;
      this.button = button;
    }

    @Override
    public <M> M process(Interaction<M> interaction, M model) {
      return interaction.getMouseReleaseHandler().apply(model, coordinate, button);
    }
  }

  public static final class MouseMoved extends TraceEvent {

    private final Coordinate coordinate;

    public MouseMoved(Coordinate coordinate) {
      super();
      this.coordinate = coordinate;
    }

    @Override
    public <M> M process(Interaction<M> interaction, M model) {
      return interaction.getMouseMoveHandler().apply(model, coordinate);
    }
  }
}
