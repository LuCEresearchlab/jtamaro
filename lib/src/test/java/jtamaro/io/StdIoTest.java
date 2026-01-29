package jtamaro.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;
import jtamaro.graphic.Colors;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import org.junit.Assert;
import org.junit.Test;

public final class StdIoTest {

  private static final String NL = System.lineSeparator();

  @Test
  public void printSequenceNull() {
    final PrintStream stdErr = System.err;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testErr = new PrintStream(oStream)) {
      System.setErr(testErr);

      StandardIO.print((Sequence<Object>) null);

      Assert.assertEquals("Nothing to print", oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setErr(stdErr);
    }
  }

  @Test
  public void printSequenceElements() {
    final PrintStream stdOut = System.out;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testOut = new PrintStream(oStream)) {
      System.setOut(testOut);

      StandardIO.print(Sequences.of(1, 2, 3));

      Assert.assertEquals("123", oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setOut(stdOut);
    }
  }

  @Test
  public void printlnSequenceNull() {
    final PrintStream stdErr = System.err;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testErr = new PrintStream(oStream)) {
      System.setErr(testErr);

      StandardIO.println((Sequence<Object>) null);

      Assert.assertEquals("Nothing to print" + NL, oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setErr(stdErr);
    }
  }

  @Test
  public void printlnSequenceElements() {
    final PrintStream stdOut = System.out;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testOut = new PrintStream(oStream)) {
      System.setOut(testOut);

      final Sequence<String> seq = Sequences.of("AA", "B", "CCC");
      StandardIO.println(seq);

      Assert.assertEquals(
          seq.reduce("", (acc, it) -> acc + NL + it),
          oStream.toString()
      );
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setOut(stdOut);
    }
  }

  @Test
  public void printlnGraphicNull() {
    final PrintStream stdErr = System.err;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testErr = new PrintStream(oStream)) {
      System.setErr(testErr);

      StandardIO.println((Graphic) null);

      Assert.assertEquals("Nothing to print" + NL, oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setErr(stdErr);
    }
  }

  @Test
  public void printlnGraphic() {
    final PrintStream stdOut = System.out;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testOut = new PrintStream(oStream)) {
      System.setOut(testOut);

      final Graphic g = Graphics.above(
          Graphics.triangle(100, 100, 60, Colors.RED),
          Graphics.rectangle(100, 100, Colors.WHITE)
      );
      StandardIO.println(g);

      Assert.assertEquals(g.dump() + NL, oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setOut(stdOut);
    }
  }

  @Test
  public void printPairNull() {
    final PrintStream stdErr = System.err;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testErr = new PrintStream(oStream)) {
      System.setErr(testErr);

      StandardIO.print((Pair<Object, Object>) null);

      Assert.assertEquals("Nothing to print", oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setErr(stdErr);
    }
  }

  @Test
  public void printPair() {
    final PrintStream stdOut = System.out;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testOut = new PrintStream(oStream)) {
      System.setOut(testOut);

      StandardIO.print(new Pair<>(15, 52));

      Assert.assertEquals("1552", oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setOut(stdOut);
    }
  }

  @Test
  public void printlnPairNull() {
    final PrintStream stdErr = System.err;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testErr = new PrintStream(oStream)) {
      System.setErr(testErr);

      StandardIO.println((Pair<Object, Object>) null);

      Assert.assertEquals("Nothing to print" + NL, oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setErr(stdErr);
    }
  }

  @Test
  public void printlnPair() {
    final PrintStream stdOut = System.out;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testOut = new PrintStream(oStream)) {
      System.setOut(testOut);

      final Pair<String, Integer> p = new Pair<>("One", 1);
      StandardIO.println(p);

      Assert.assertEquals(p.first() + NL + p.second() + NL, oStream.toString());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setOut(stdOut);
    }
  }


  @Test
  public void readLine() {
    final InputStream stdIn = System.in;
    try (ByteArrayInputStream iStream = new ByteArrayInputStream(
        "Input string".getBytes()
    )) {
      System.setIn(iStream);

      Assert.assertEquals("Input string", StandardIO.readLine());
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    } finally {
      System.setIn(stdIn);
    }
  }

  @Test
  public void testToDoMessage() {
    final PrintStream stdErr = System.err;
    try (ByteArrayOutputStream oStream = new ByteArrayOutputStream();
         PrintStream testErr = new PrintStream(oStream)) {
      System.setErr(testErr);

      StandardIO.todo("test message");
      final String outContent = oStream.toString();

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
