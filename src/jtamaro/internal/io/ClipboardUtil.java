package jtamaro.internal.io;

import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.representation.GraphicImpl;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;


public abstract class ClipboardUtil {

  private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

  public static void copyToClipboard(GraphicImpl graphicImpl, RenderOptions renderOptions) {
    int padding = renderOptions.getPadding();
    int width = (int) graphicImpl.getWidth() + 2 * padding;
    int height = (int) graphicImpl.getHeight() + 2 * padding;
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.translate(padding, padding);
    g2.translate(-graphicImpl.getBBox().getMinX(), -graphicImpl.getBBox().getMinY());
    graphicImpl.render(g2, renderOptions);
    graphicImpl.drawDebugInfo(g2, renderOptions);
    copyToClipboard(bufferedImage);
  }

  public static void copyToClipboard(BufferedImage image) {
    CLIPBOARD.setContents(new TransferableImage(image), null);
  }

  private record TransferableImage(Image image) implements Transferable {

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
      if (flavor.equals(DataFlavor.imageFlavor) && image != null) {
        return image;
      } else {
        throw new UnsupportedFlavorException(flavor);
      }
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] flavors = new DataFlavor[1];
      flavors[0] = DataFlavor.imageFlavor;
      return flavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
      DataFlavor[] flavors = getTransferDataFlavors();
      for (DataFlavor dataFlavor : flavors) {
        if (flavor.equals(dataFlavor)) {
          return true;
        }
      }
      return false;
    }
  }

}
