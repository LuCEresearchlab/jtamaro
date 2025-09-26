package jtamaro.optics.processor;

import java.lang.annotation.Annotation;
import jtamaro.data.Sequence;
import jtamaro.optics.Glasses;

@Glasses
public record PubRecord(int a, Double b, Annotation an) {

  @Glasses
  protected record InnerRecord(Sequence<String> words) {
  }
}
