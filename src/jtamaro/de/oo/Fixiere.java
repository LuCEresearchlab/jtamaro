package jtamaro.de.oo;

import java.util.HashMap;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.PinPointImpl;
import jtamaro.internal.representation.Place;


public final class Fixiere extends AbstrakteGrafik {

  private static final HashMap<String,Place> PLACES;

  static {
    PLACES = new HashMap<>();
    PLACES.put("oben-links", Place.TL);
    PLACES.put("oben-mitte", Place.TM);
    PLACES.put("oben-rechts", Place.TR);
    PLACES.put("mitte-links", Place.ML);
    PLACES.put("mitte-mitte", Place.MM);
    PLACES.put("mitte-rechts", Place.MR);
    PLACES.put("unten-links", Place.BL);
    PLACES.put("unten-mitte", Place.BM);
    PLACES.put("unten-rechts", Place.BR);
  }
  
  public Fixiere(String horizontalePosition, String vertikalePosition, Grafik grafik) {
    super(new PinPointImpl(makePlace(horizontalePosition, vertikalePosition), ((AbstrakteGrafik)grafik).getImplementation()));
  }

  private static Place makePlace(String horizontalePosition, String vertikalePosition) {
    Place place = PLACES.get(vertikalePosition + "-" + horizontalePosition);
    if (place == null) {
      throw new IllegalArgumentException("Unbekannte position: " + horizontalePosition + ", " + vertikalePosition);
    } else {
      return place;
    }
  }

}
