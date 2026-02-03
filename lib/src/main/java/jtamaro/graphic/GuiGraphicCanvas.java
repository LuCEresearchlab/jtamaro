package jtamaro.graphic;

import java.awt.AWTError;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
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

  private static final int BG_TILE_SIZE = 10;

  private static final AffineTransform AFFINE_ID = new AffineTransform(1f, 0f, 0f, 1f, 0f, 0f);

  private static final RenderingHints RENDERING_HINTS = new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON
  );

  private State state;

  private final RenderOptions renderOptions;

  public GuiGraphicCanvas(RenderOptions renderOptions) {
    super();
    this.state = new State();
    this.renderOptions = renderOptions;
    renderOptions.addRenderOptionsListener(() -> {
      this.state = new State(state.graphic, graphicToImage(renderOptions, state.graphic));
      repaint();
    });
  }

  /**
   * Change the graphic drawn by this canvas.
   */
  public void setGraphic(Graphic graphic) {
    this.state = state.withGraphic(renderOptions, graphic);
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
    return getMouseActionAtCoordinates(absoluteCoordinates).flatMap(p ->
        p.first().getPressAction().fold(
            action -> Options.some(p.withFirst(action)),
            Options::none
        )
    );
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
    return getMouseActionAtCoordinates(absoluteCoordinates).flatMap(p ->
        p.first().getReleaseAction().fold(
            action -> Options.some(p.withFirst(action)),
            Options::none
        )
    );
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
    return getMouseActionAtCoordinates(absoluteCoordinates).flatMap(p ->
        p.first().getMoveAction().fold(
            action -> Options.some(p.withFirst(action)),
            Options::none
        )
    );
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
    return getMouseActionAtCoordinates(absoluteCoordinates).flatMap(p ->
        p.first().getDragAction().fold(
            action -> Options.some(p.withFirst(action)),
            Options::none
        )
    );
  }

  /**
   * Get the mouse action for the graphic drawn at the given absolute coordinates.
   *
   * @param absoluteCoordinates Coordinates of the mouse event with respect to the whole graphic
   *                            drawn by this component (absolute coordinates)
   * @return Pair of {@link ActionableGraphic} (type-erased) and {@link Coordinate} with respect to
   * the {@link ActionableGraphic} at the given coordinates (relative coordinates)
   */
  private Option<Pair<ActionableGraphic<?>, Coordinate>> getMouseActionAtCoordinates(
      Coordinate absoluteCoordinates
  ) {
    // Translate the absolute coordinates with respect to the origin point
    // of the drawn graphic
    final Rectangle2D bbox = state.graphic.getBBox();
    final double padding = renderOptions.getPadding();
    final double dx = padding - bbox.getMinX();
    final double dy = padding - bbox.getMinY();
    final double x = absoluteCoordinates.x() - dx;
    final double y = absoluteCoordinates.y() - dy;

    return state.graphic.relativeLocationOf(x, y).fold(
        rl -> rl.graphic() instanceof ActionableGraphic<?> ag
            ? Options.some(new Pair<>(ag, rl.relativeCoordinates()))
            : Options.none(),
        Options::none
    );
  }

  /**
   * Save the current graphic to the given path as a PNG file.
   */
  public boolean saveGraphic(Path path) {
    try (OutputStream oStream = Files.newOutputStream(path,
        StandardOpenOption.CREATE,
        StandardOpenOption.WRITE)) {
      ImageIO.write(state.image, "png", oStream);
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Failed to write graphics to " + path, e);
      return false;
    }
  }

  /**
   * Copies the current graphic to the given path as a PNG file.
   */
  public boolean copyGraphicToClipboard() {
    final Transferable transferable = new TransferableBufferedImage(state.image);
    try {
      Toolkit.getDefaultToolkit()
          .getSystemClipboard()
          .setContents(transferable, null);
      return true;
    } catch (AWTError
             | HeadlessException
             | IllegalStateException e) {
      LOGGER.log(Level.SEVERE, "Failed to copy graphic to system clipboard", e);
      return false;
    }
  }

  @Override
  public Dimension getPreferredSize() {
    final int padding = renderOptions.getPadding() * 2;
    if (renderOptions.hasFixedSize()) {
      return new Dimension(
          renderOptions.getFixedWidth() + padding,
          renderOptions.getFixedHeight() + padding
      );
    } else {
      return new Dimension(
          (int) state.graphic.getWidth() + padding,
          (int) state.graphic.getHeight() + padding
      );
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    final Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHints(RENDERING_HINTS);

    final int padding = renderOptions.getPadding();
    if (renderOptions.shouldDrawBackground()) {
      paintBackground(g2d);
    }

    // Draw pre-rendered version of the graphic
    g2d.drawImage(state.image, AFFINE_ID, null);

    // Dynamically draw info
    if (renderOptions.isSelected(state.graphic)) {
      final Rectangle2D bbox = state.graphic.getBBox();
      g2d.translate(padding, padding);
      g2d.translate(-bbox.getMinX(), -bbox.getMinY());
      state.graphic.drawDebugInfo(g2d);
    }
  }

  private void paintBackground(Graphics2D g2d) {
    final int width = getWidth();
    final int height = getHeight();
    g2d.setColor(BRIGHT);
    g2d.fillRect(0, 0, width, height);
    g2d.setColor(DARK);
    final int rows = height / BG_TILE_SIZE;
    final int cols = width / BG_TILE_SIZE;

    final int extraVert = BG_TILE_SIZE - (height % BG_TILE_SIZE);
    final int extraHoriz = BG_TILE_SIZE - (width % BG_TILE_SIZE);

    for (int col = 0; col < cols; col++) {
      for (int row = 0; row < rows; row++) {
        if ((col + row) % 2 == 0) {
          g2d.fillRect(col * BG_TILE_SIZE, row * BG_TILE_SIZE, BG_TILE_SIZE, BG_TILE_SIZE);
        }
      }
      if ((col + rows) % 2 == 0) {
        // Horizontal remainder for the column
        g2d.fillRect(col * BG_TILE_SIZE, rows * BG_TILE_SIZE, BG_TILE_SIZE, extraHoriz);
      }
    }
    for (int row = 0; row < rows; row++) {
      if ((cols + row) % 2 == 0) {
        // Vertical remainder for the row
        g2d.fillRect(cols * BG_TILE_SIZE, row * BG_TILE_SIZE, extraVert, BG_TILE_SIZE);
      }
    }
  }

  private static BufferedImage graphicToImage(RenderOptions renderOptions, Graphic graphic) {
    final int padding = renderOptions.getPadding();
    final Rectangle2D bbox = graphic.getBBox();
    final int width = (int) Math.ceil(bbox.getWidth()) + 2 * padding;
    final int height = (int) Math.ceil(bbox.getHeight()) + 2 * padding;

    final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    // According to the documentation, the concrete type is always Graphics2D
    final Graphics2D g2d = (Graphics2D) image.getGraphics();
    try {
      g2d.setRenderingHints(RENDERING_HINTS);
      g2d.translate(padding, padding);
      g2d.translate(-bbox.getMinX(), -bbox.getMinY());
      graphic.render(g2d, renderOptions);
      return image;
    } finally {
      g2d.dispose();
    }
  }

  private record State(Graphic graphic, BufferedImage image) {

    public State() {
      this(new EmptyGraphic(), new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY));
    }

    public State withGraphic(RenderOptions renderOptions, Graphic graphic) {
      return new State(
          graphic,
          graphic.structurallyEqualTo(this.graphic)
              ? image
              : graphicToImage(renderOptions, graphic)
      );
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
