package jtamaro.en.graphic;

import java.util.HashMap;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.PinPointImpl;
import jtamaro.internal.representation.Place;


public final class Pin extends AbstractGraphic {

  private static final HashMap<String,Place> PLACES;

  static {
    PLACES = new HashMap<>();
    PLACES.put("top-left", Place.TL);
    PLACES.put("top-middle", Place.TM);
    PLACES.put("top-right", Place.TR);
    PLACES.put("middle-left", Place.ML);
    PLACES.put("middle-middle", Place.MM);
    PLACES.put("middle-right", Place.MR);
    PLACES.put("bottom-left", Place.BL);
    PLACES.put("bottom-middle", Place.BM);
    PLACES.put("bottom-right", Place.BR);
    PLACES.put("base-links", Place.BAL);
    PLACES.put("base-mitte", Place.BAM);
    PLACES.put("base-rechts", Place.BAR);
  }

  public Pin(String horizontalPlace, String verticalPlace, Graphic graphic) {
    super(new PinPointImpl(makePlace(horizontalPlace, verticalPlace), ((AbstractGraphic)graphic).getImplementation()));
  }

  private static Place makePlace(String horizontalePosition, String vertikalePosition) {
    Place place = PLACES.get(vertikalePosition + "-" + horizontalePosition);
    if (place == null) {
      throw new IllegalArgumentException("Unknown place: " + horizontalePosition + ", " + vertikalePosition);
    } else {
      return place;
    }
  }

}
