package jtamaro.graphic;

import org.junit.Assert;
import org.junit.Test;

public final class EnvironmentTest {

  @Test
  public void runningInHeadlessMode() {
    Assert.assertEquals("true", System.getProperty("java.awt.headless"));
  }
}
