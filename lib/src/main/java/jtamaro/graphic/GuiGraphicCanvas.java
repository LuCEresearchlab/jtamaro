package jtamaro.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import jtamaro.data.Function1;
import jtamaro.data.Option;
import jtamaro.data.Options;
import jtamaro.data.Pair;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseAction;
import jtamaro.interaction.MouseDragAction;
import jtamaro.interaction.MouseMoveAction;
import jtamaro.interaction.MousePressAction;
import jtamaro.interaction.MouseReleaseAction;

/**
 * Swing canvas component that renders a graphic.
 *
 * @apiNote Not for public use.
 * @hidden
 */
public final class GuiGraphicCanvas extends JComponent {

  private static final Logger LOGGER = Logger.getLogger(GuiGraphicCanvas.class.getSimpleName());

  private static final Color BRIGHT = new Color(250, 250, 250);

  private static final Color DARK = new Color(230, 230, 230);

  private final RenderOptions renderOptions;

  private Graphic graphic;

  public GuiGraphicCanvas(RenderOptions renderOptions) {
    super();
    this.renderOptions = renderOptions;
    this.graphic = new EmptyGraphic();

    renderOptions.addRenderOptionsListener(this::repaint);
  }

  public void setGraphic(Graphic graphic) {
    this.graphic = graphic;
    revalidate();
    repaint();
  }

  /**
   * Get the mouse press action for the graphic drawn at the given absolute coordinates.
   *
   * @param absoluteCoordinates Coordinates of the mouse event with respect to the whole graphic
   *                            drawn by this component (absolute coordinates)
   * @return Pair of {@link MousePressAction} (type-erased) and {@link Coordinate} with respect to
   * the {@link ActionableGraphic} at the given coordinates (relative coordinates)
   */
  public Option<Pair<MouseAction<?>, Coordinate>> getMousePressAction(
      Coordinate absoluteCoordinates
  ) {
    return getMouseActionAtCoordinates(absoluteCoordinates, ActionableGraphic::getPressAction);
  }

  /**
   * Get the mouse release action for the graphic drawn at the given absolute coordinates.
   *
   * @param absoluteCoordinates Coordinates of the mouse event with respect to the whole graphic
   *                            drawn by this component (absolute coordinates)
   * @return Pair of {@link MouseReleaseAction} (type-erased) and {@link Coordinate} with respect to
   * the {@link ActionableGraphic} at the given coordinates (relative coordinates)
   */
  public Option<Pair<MouseAction<?>, Coordinate>> getMouseReleaseAction(
      Coordinate absoluteCoordinates
  ) {
    return getMouseActionAtCoordinates(absoluteCoordinates, ActionableGraphic::getReleaseAction);
  }

  /**
   * Get the mouse move action for the graphic drawn at the given absolute coordinates.
   *
   * @param absoluteCoordinates Coordinates of the mouse event with respect to the whole graphic
   *                            drawn by this component (absolute coordinates)
   * @return Pair of {@link MouseMoveAction} (type-erased) and {@link Coordinate} with respect to
   * the {@link ActionableGraphic} at the given coordinates (relative coordinates)
   */
  public Option<Pair<MouseAction<?>, Coordinate>> getMouseMoveAction(
      Coordinate absoluteCoordinates
  ) {
    return getMouseActionAtCoordinates(absoluteCoordinates, ActionableGraphic::getMoveAction);
  }

  /**
   * Get the mouse drag action for the graphic drawn at the given absolute coordinates.
   *
   * @param absoluteCoordinates Coordinates of the mouse event with respect to the whole graphic
   *                            drawn by this component (absolute coordinates)
   * @return Pair of {@link MouseDragAction} (type-erased) and {@link Coordinate} with respect to
   * the {@link ActionableGraphic} at the given coordinates (relative coordinates)
   */
  public Option<Pair<MouseAction<?>, Coordinate>> getMouseDragAction(
      Coordinate absoluteCoordinates
  ) {
    return getMouseActionAtCoordinates(absoluteCoordinates, ActionableGraphic::getDragAction);
  }

  /**
   * Get the mouse action for the graphic drawn at the given absolute coordinates.
   *
   * @param absoluteCoordinates Coordinates of the mouse event with respect to the whole graphic
   *                            drawn by this component (absolute coordinates)
   * @param getAction           Function to obtain the desired action from the
   *                            {@link ActionableGraphic} at the given coordinates
   * @return Pair of {@link MouseAction} (type-erased) and {@link Coordinate} with respect to the
   * {@link ActionableGraphic} at the given coordinates (relative coordinates)
   */
  private <T extends MouseAction<?>> Option<Pair<T, Coordinate>> getMouseActionAtCoordinates(
      Coordinate absoluteCoordinates,
      Function1<ActionableGraphic<?>, T> getAction
  ) {
    // Translate the absolute coordinates with respect to the origin point
    // of the drawn graphic
    final Rectangle2D bbox = graphic.getBBox();
    final double padding = renderOptions.getPadding();
    final double dx = padding - bbox.getMinX();
    final double dy = padding - bbox.getMinY();
    final double x = absoluteCoordinates.x() - dx;
    final double y = absoluteCoordinates.y() - dy;

    return graphic.relativeLocationOf(x, y).fold(
        rl -> rl.graphic() instanceof ActionableGraphic<?> ag
            ? Options.some(new Pair<>(getAction.apply(ag), rl.relativeCoordinates()))
            : Options.none(),
        Options::none
    );
  }

  public boolean saveGraphic(Path path) {
    final BufferedImage image = getBufImage();

    try (OutputStream oStream = Files.newOutputStream(path,
        StandardOpenOption.CREATE,
        StandardOpenOption.WRITE)) {
      ImageIO.write(image, "png", oStream);
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Failed to write graphics to " + path, e);
      return false;
    }
  }

  public boolean copyGraphicToClipboard() {
    final Transferable transferable = new TransferableBufferedImage(getBufImage());
    try {
      Toolkit.getDefaultToolkit()
          .getSystemClipboard()
          .setContents(transferable, null);
      return true;
    } catch (Exception e) {
      // Unsupported operation...
      return false;
    }
  }

  @Override
  public Dimension getPreferredSize() {
    final int padding = renderOptions.getPadding();
    if (renderOptions.hasFixedSize()) {
      return new Dimension(
          renderOptions.getFixedWidth() + 2 * padding,
          renderOptions.getFixedHeight() + 2 * padding
      );
    } else {
      return new Dimension(
          (int) graphic.getWidth() + 2 * padding,
          (int) graphic.getHeight() + 2 * padding
      );
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    final Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHints(new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    ));
    final int padding = renderOptions.getPadding();
    if (renderOptions.shouldDrawBackground()) {
      paintBackground(g);
    }
    g2.translate(padding, padding);
    g2.translate(-graphic.getBBox().getMinX(), -graphic.getBBox().getMinY());
    graphic.render(g2, renderOptions);
    graphic.drawDebugInfo(g2, renderOptions);
  }

  private void paintBackground(Graphics g) {
    final int width = getWidth();
    final int height = getHeight();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    final int tileSize = 10;
    final int rows = height / tileSize;
    final int cols = width / tileSize;
    for (int col = 0; col <= cols; col++) {
      for (int row = 0; row <= rows; row++) {
        g.setColor((col + row) % 2 == 0 ? BRIGHT : DARK);
        int tileWidth = Math.min(width - col * tileSize, tileSize);
        int tileHeight = Math.min(height - row * tileSize, tileSize);
        g.fillRect(col * tileSize, row * tileSize, tileWidth, tileHeight);
      }
    }
  }

  private BufferedImage getBufImage() {
    final int padding = renderOptions.getPadding();
    final Rectangle2D bBox = graphic.getBBox();
    final int width = (int) Math.ceil(bBox.getWidth()) + 2 * padding;
    final int height = (int) Math.ceil(bBox.getHeight()) + 2 * padding;

    final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    // According to the documentation, the concrete type is always Graphics2D
    final Graphics2D g2d = (Graphics2D) image.getGraphics();
    try {
      g2d.setRenderingHints(new RenderingHints(
          RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON));
      g2d.translate(padding, padding);
      g2d.translate(-bBox.getMinX(), -bBox.getMinY());
      graphic.render(g2d, renderOptions);
      graphic.drawDebugInfo(g2d, renderOptions);
      return image;
    } finally {
      g2d.dispose();
    }
  }

  private record TransferableBufferedImage(BufferedImage bufferedImage) implements Transferable {

    @Override
    public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{DataFlavor.imageFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
      return flavor.isMimeTypeEqual(DataFlavor.imageFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
      if (isDataFlavorSupported(flavor)) {
        return bufferedImage;
      } else {
        throw new UnsupportedFlavorException(flavor);
      }
    }
  }
}
