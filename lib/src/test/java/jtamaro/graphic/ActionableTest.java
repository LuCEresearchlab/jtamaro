package jtamaro.graphic;

import jtamaro.data.Option;
import jtamaro.data.Options;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import org.junit.Assert;
import org.junit.Test;

import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;

public class ActionableTest {

  @Test
  public void testActionablePriority() {
    final int[] count = {0};
    final Graphic graphic = overlay(
        rectangle(100, 80, RED),
        new Actionable<>(
            rectangle(80, 100, BLUE)
        ).withMousePressHandler(($, c, m) -> {
              count[0]++;
              return count[0];
            }
        ).asGraphic()
    );

    final Option<RelativeLocation> opt = graphic.relativeLocationOf(0, 0);
    Assert.assertFalse(opt.isEmpty());
    opt.flatMap(rl -> rl.graphic() instanceof ActionableGraphic<?> ag
        ? ag.getPressAction()
        : Options.none()
    ).stream().forEach(action ->
      action.apply(null, new Coordinate(0, 0), new MouseButton(0)));
    Assert.assertEquals(1, count[0]);
  }

}
