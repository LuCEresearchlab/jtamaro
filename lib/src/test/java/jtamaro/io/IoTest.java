package jtamaro.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.Test;

public final class IoTest {

  @Test
  public void testToDo() {
    final PrintStream stdErr = System.err;
    try (final ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         final PrintStream testErr = new PrintStream(oStream)) {
      System.setErr(testErr);

      IO.todo("test message");
      final String outContent = oStream.toString(StandardCharsets.UTF_8);

      Assert.assertTrue(outContent.contains("test message"));

      try {
        throw new Exception();
      } catch (Exception e) {
        final StackTraceElement ste = e.getStackTrace()[0];
        Assert.assertNotNull(ste.getFileName());
        Assert.assertTrue(outContent.contains(ste.getFileName()));
      }
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setErr(stdErr);
    }
  }
}
