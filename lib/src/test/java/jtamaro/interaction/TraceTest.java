package jtamaro.interaction;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicInteger;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;
import org.junit.Assert;
import org.junit.Test;

public final class TraceTest {

  @Test
  public void defaultLenZero() {
    Assert.assertEquals(0, new Trace<>().length());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getEmpty() {
    new Trace<>().get(0);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getOutOfBounds() {
    new Trace<>().get(1);
  }

  @Test
  public void getFirst() {
    final Trace<Character> trace = new Trace<>();
    trace.append(new TraceEvent.KeyboardCharEvent<>(
        new KeyboardChar('~'),
        (_, k) -> k.keyChar()
    ));

    Assert.assertEquals((Character) '~', trace.get(0).process(' '));
  }

  @Test
  public void getSecond() {
    final Trace<String> trace = new Trace<>();
    trace.append(new TraceEvent.Tick<>(s -> s + s, 1L));
    trace.append(new TraceEvent.GlobalMouseEvent<>(
        "press",
        new Coordinate(0, 0),
        new MouseButton(MouseButton.PRIMARY),
        (s, _, _) -> s + s
    ));
    trace.append(new TraceEvent.MouseEvent<>(
        "move",
        new Coordinate(10, 20),
        new MouseButton(MouseButton.NO_BUTTON),
        (c, _) -> c.toString()
    ));

    Assert.assertEquals("..", trace.get(1).process("."));
  }

  @Test
  public void getEventSequence() {
    final Trace<String> trace = new Trace<>();
    final Sequence<TraceEvent<String>> events = Sequences.of(
        new TraceEvent.Tick<>(s -> s + s, 1L),
        new TraceEvent.Tick<>(s -> s + s, 2L),
        new TraceEvent.Tick<>(s -> s + s, 3L)
    );
    events.forEach(trace::append);

    Assert.assertEquals(events.reverse(), trace.getEventSequence());
  }

  @Test
  public void testListener() {
    final AtomicInteger counter = new AtomicInteger(0);
    final Trace<Double> trace = new Trace<>();
    final TraceListener lInc = _ -> counter.getAndIncrement();
    final TraceListener lDec = _ -> counter.getAndDecrement();
    trace.addTraceListener(lInc);
    trace.addTraceListener(lDec);
    trace.append(new TraceEvent.KeyboardKeyEvent<>(
        new KeyboardKey(KeyEvent.VK_1),
        (x, _) -> x + 1.0
    ));
    trace.removeTraceListener(lDec);
    trace.append(new TraceEvent.Tick<>(x -> x * 2.0, 1L));

    Assert.assertEquals(1, counter.get());
  }
}
