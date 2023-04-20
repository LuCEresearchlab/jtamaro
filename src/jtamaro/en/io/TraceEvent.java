package jtamaro.en.io;

public abstract class TraceEvent {

  private final String kind;
  private final long timeStamp;

  public TraceEvent(String kind) {
    this.kind = kind;
    timeStamp = System.currentTimeMillis();
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public String getKind() {
    return kind;
  }

  public abstract <M> M process(Interaction<M> bang, M model);

  public static TraceEvent createTick() {
    return new TraceEvent("Tick") {
      @Override
      public <M> M process(Interaction<M> bang, M model) {
        return bang.getTickHandler().apply(model);
      }
    };
  }

  public static TraceEvent createKeyPress(KeyboardKey key) {
    return new TraceEvent("KeyPress") {
      @Override
      public <M> M process(Interaction<M> bang, M model) {
        return bang.getKeyPressHandler().apply(model, key);
      }
    };
  }

  public static TraceEvent createKeyRelease(KeyboardKey key) {
    return new TraceEvent("KeyRelease") {
      @Override
      public <M> M process(Interaction<M> bang, M model) {
        return bang.getKeyReleaseHandler().apply(model, key);
      }
    };
  }

  public static TraceEvent createKeyType(KeyboardChar ch) {
    return new TraceEvent("KeyType") {
      @Override
      public <M> M process(Interaction<M> bang, M model) {
        return bang.getKeyTypeHandler().apply(model, ch);
      }
    };
  }

  public static TraceEvent createMousePress(Coordinate coordinate, MouseButton button) {
    return new TraceEvent("MousePress") {
      @Override
      public <M> M process(Interaction<M> bang, M model) {
        return bang.getMousePressHandler().apply(model, coordinate, button);
      }
    };
  }

  public static TraceEvent createMouseRelease(Coordinate coordinate, MouseButton button) {
    return new TraceEvent("MouseRelease") {
      @Override
      public <M> M process(Interaction<M> bang, M model) {
        return bang.getMouseReleaseHandler().apply(model, coordinate, button);
      }
    };
  }

  public static TraceEvent createMouseMove(Coordinate coordinate) {
    return new TraceEvent("MouseMove") {
      @Override
      public <M> M process(Interaction<M> bang, M model) {
        return bang.getMouseMoveHandler().apply(model, coordinate);
      }
    };
  }

}
