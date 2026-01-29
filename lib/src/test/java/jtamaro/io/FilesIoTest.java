package jtamaro.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import jtamaro.data.Eithers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public final class FilesIoTest {

  private static final Path TMP_DIR = Path.of(System.getProperty("java.io.tmpdir"));

  @BeforeClass
  public static void assertTmpDirWritable() {
    Assert.assertTrue(Files.isDirectory(TMP_DIR));
    Assert.assertTrue(Files.isWritable(TMP_DIR));
  }

  @Test
  public void readFileRight() throws IOException {
    final Path p = Files.createTempFile(TMP_DIR, null, null);
    Files.writeString(p, "Hello world!");

    Assert.assertEquals(Eithers.right("Hello world!"), FileIO.readFile(p));
  }

  @Test
  public void readFileLeft() throws IOException {
    final Path p = TMP_DIR.resolve("should_not_exist");
    Files.deleteIfExists(p);

    Assert.assertFalse(FileIO.readFile(p).isRight());
  }

  @Test
  public void writeStringSuccess() throws IOException {
    final Path p = Files.createTempFile(TMP_DIR, null, null);

    Assert.assertTrue(FileIO.writeFile(p, "Hello there").isEmpty());
    Assert.assertEquals("Hello there", Files.readString(p));
  }

  @Test
  public void writeStringFailure() throws IOException {
    final Path p = TMP_DIR.resolve("missing_dir").resolve("f");
    Files.deleteIfExists(p.getParent());

    Assert.assertFalse(FileIO.writeFile(p, "@").isEmpty());
  }

  @Test
  public void writeBytesSuccess() throws IOException {
    final Path p = Files.createTempFile(TMP_DIR, null, null);
    final byte[] bytes = {12, 10, 15, 14, 11, 10, 11, 14};

    Assert.assertTrue(FileIO.writeFile(p, bytes).isEmpty());
    Assert.assertArrayEquals(bytes, Files.readAllBytes(p));
  }

  @Test
  public void writeBytesFailure() throws IOException {
    final Path p = TMP_DIR.resolve("missing_dir").resolve("f");
    Files.deleteIfExists(p.getParent());

    Assert.assertFalse(FileIO.writeFile(p, new byte[]{1, 0, 0, 1}).isEmpty());
  }
}
