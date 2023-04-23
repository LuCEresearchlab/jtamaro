package jtamaro.internal.io;

import jtamaro.en.Sequence;
import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.representation.GraphicImpl;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// based on https://memorynotfound.com/generate-gif-image-java-delay-infinite-loop-example/
public class GifWriter {

  public static void saveAnimation(Sequence<GraphicImpl> graphicImpls, int millisecondsPerFrame, boolean loop, String filename) throws IOException {
    // https://docs.oracle.com/javase/8/docs/api/javax/imageio/metadata/doc-files/gif_metadata.html
    assert millisecondsPerFrame / 10 > 0;
    assert millisecondsPerFrame / 10 < 65536;
    assert !graphicImpls.isEmpty() : "Animation must have at least one frame";
    assert graphicImpls.hasDefiniteSize() : "Animation must have a finite number of frames";

    final int bufferedImageType = BufferedImage.TYPE_INT_ARGB;
    final ImageOutputStream out = new FileImageOutputStream(new File(filename));
    final ImageWriter writer = ImageIO.getImageWritersBySuffix("gif").next();
    final ImageWriteParam params = writer.getDefaultWriteParam();
    final ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(bufferedImageType);
    final IIOMetadata metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);
    final String metaFormatName = metadata.getNativeMetadataFormatName();
    final IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);
    final IIOMetadataNode graphicsControlExtensionNode = getOrCreateNode(root, "GraphicControlExtension");
    graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
    graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(millisecondsPerFrame / 10));
    graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");
    final IIOMetadataNode commentsNode = getOrCreateNode(root, "CommentExtensions");
    commentsNode.setAttribute("CommentExtension", "Created by JTamaro");
    final IIOMetadataNode appExtensionsNode = getOrCreateNode(root, "ApplicationExtensions");
    final IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
    child.setAttribute("applicationID", "NETSCAPE");
    child.setAttribute("authenticationCode", "2.0");
    final int loopContinuously = loop ? 0 : 1;
    child.setUserObject(new byte[]{0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF)});
    appExtensionsNode.appendChild(child);
    metadata.setFromTree(metaFormatName, root);
    writer.setOutput(out);
    writer.prepareWriteSequence(null);
    for (final GraphicImpl graphicImpl : graphicImpls) {
      final int width = (int) graphicImpl.getWidth();
      final int height = (int) graphicImpl.getHeight();
      final BufferedImage image = new BufferedImage(width, height, bufferedImageType);
      final Graphics2D g2 = image.createGraphics();
      final RenderOptions renderOptions = new RenderOptions(0);
      g2.translate(-graphicImpl.getBBox().getMinX(), -graphicImpl.getBBox().getMinY());
      graphicImpl.render(g2, renderOptions);
      //graphicImpl.drawDebugInfo(g2, renderOptions);
      final IIOImage iioImage = new IIOImage(image, null, metadata);
      writer.writeToSequence(iioImage, params);
    }
    writer.endWriteSequence();
    out.close();
  }

  private static IIOMetadataNode getOrCreateNode(IIOMetadataNode parent, String childName) {
    final int length = parent.getLength();
    for (int i = 0; i < length; i++) {
      if (parent.item(i).getNodeName().equalsIgnoreCase(childName)) {
        return (IIOMetadataNode) parent.item(i);
      }
    }
    final IIOMetadataNode node = new IIOMetadataNode(childName);
    parent.appendChild(node);
    return node;
  }

}
