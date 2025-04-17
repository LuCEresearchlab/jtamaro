package jtamaro.graphic;

import jtamaro.data.Option;
import jtamaro.data.Options;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.SequencedMap;

final class LabeledGraphic extends DelegatingGraphic {

  private final String label;

  private final Graphic graphic;

  public LabeledGraphic(String label, Graphic graphic) {
    super(graphic);
    this.label = label;
    this.graphic = graphic;
  }

  public String getLabel() {
    return label;
  }

  @Override
  public Option<Graphic> nodeContaining(double x, double y) {
    return graphic.nodeContaining(x, y).map(g -> this);
  }

  @Override
  SequencedMap<String, Graphic> getChildren() {
    final SequencedMap<String, Graphic> children = new LinkedHashMap<>();
    children.put("graphic", graphic);
    return children;
  }

  @Override
  protected SequencedMap<String, String> getProps(boolean plainText) {
    final SequencedMap<String, String> props = super.getProps(plainText);
    props.put("label", label);
    return props;
  }

  @Override
  protected String getInspectLabel() {
    return "labeled";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof LabeledGraphic that
        && Objects.equals(that.label, label)
        && Objects.equals(that.graphic, graphic));
  }
}
