package jtamaro.de;


/**
 * Eine Grafik mit einer Fixierposition.
 * Die Fixierposition wird von den folgenden Operationen verwendet:
 * - drehe (das Rotationszentrum ist die Fixierposition)
 * - kombiniere (die zwei Grafiken werden so ausgerichtet, dass ihre Fixierpositionen übereinanderliegen).
 */
public interface Grafik {
  
  public double gibBreite();
  public double gibHoehe();

}
