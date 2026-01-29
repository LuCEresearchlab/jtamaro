package jtamaro.graphic;

import jtamaro.data.Function1;
import jtamaro.interaction.KeyboardChar;
import jtamaro.interaction.KeyboardKey;
import jtamaro.optics.Lens;
import org.junit.Assert;
import org.junit.Test;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.MONOSPACED;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.GraphicIO.interact;

public final class ModelLessInteractionDemoTest {

  @Test
  public void testTextContent() {
    final Text t = new Text("Hello", MONOSPACED, 10, WHITE);
    final String newContent = "Bye";
    final Text actual = (Text) TEXT_CONTENT.set(newContent, t);

    Assert.assertEquals(
        newContent,
        TEXT_CONTENT.view(actual)
    );
    Assert.assertEquals(
        new Text(newContent, t.getFont(), t.getSize(), t.getColor()),
        actual
    );
  }

  @Test
  public void testOverlayForeground() {
    final Overlay o = new Overlay(new EmptyGraphic(), new EmptyGraphic());
    final Graphic newForeground = new Rectangle(10, 10, WHITE);
    final Overlay actual = (Overlay) OVERLAY_FOREGROUND.set(newForeground, o);

    Assert.assertEquals(
        newForeground,
        OVERLAY_FOREGROUND.view(actual)
    );
    Assert.assertEquals(
        new Overlay(newForeground, o.getBackground()),
        actual
    );
  }

  private static final Lens<Graphic, Graphic, Graphic, Graphic> OVERLAY_FOREGROUND = new Lens<>() {
    @Override
    public Graphic over(Function1<Graphic, Graphic> lift, Graphic source) {
      if (source instanceof Overlay overlay) {
        return new Overlay(lift.apply(overlay.getForeground()), overlay.getBackground());
      } else {
        throw new IllegalArgumentException("Expected overlay");
      }
    }

    @Override
    public Graphic view(Graphic source) {
      if (source instanceof Overlay overlay) {
        return overlay.getForeground();
      } else {
        throw new IllegalArgumentException("Expected overlay");
      }
    }
  };

  private static final Lens<Graphic, Graphic, String, String> TEXT_CONTENT = new Lens<>() {
    @Override
    public Graphic over(Function1<String, String> lift, Graphic source) {
      if (source instanceof Text text) {
        return new Text(
            lift.apply(text.getContent()),
            text.getFont(),
            text.getSize(),
            text.getColor()
        );
      } else {
        throw new IllegalArgumentException("Expected text");
      }
    }

    @Override
    public String view(Graphic source) {
      if (source instanceof Text text) {
        return text.getContent();
      } else {
        throw new IllegalArgumentException("Expected text");
      }
    }
  };

  /* Demo interaction */

  private static final Graphic INITIAL_GRAPHIC = overlay(
      text("", MONOSPACED, 20, WHITE),
      rectangle(500, 40, BLACK)
  );

  private static Graphic onKeyType(Graphic g, KeyboardChar c) {
    return OVERLAY_FOREGROUND.then(TEXT_CONTENT)
        .over(content -> content + c.keyChar(), g);
  }

  private static Graphic onKeyPress(Graphic g, KeyboardKey key) {
    if (key.keyCode() == KeyboardKey.BACK_SPACE) {
      return OVERLAY_FOREGROUND.then(TEXT_CONTENT)
          .over(content -> switch (content.length()) {
            case 0, 1 -> "";
            default -> content.substring(0, content.length() - 2);
          }, g);
    } else {
      return g;
    }
  }

  // Demo interaction
  static void main() {
    interact(INITIAL_GRAPHIC)
        .withName("Model-less TextField")
        .withRenderer(Function1.identity())
        .withKeyPressHandler(ModelLessInteractionDemoTest::onKeyPress)
        .withKeyTypeHandler(ModelLessInteractionDemoTest::onKeyType)
        .withMsBetweenTicks(300)
        .run();
  }
}
