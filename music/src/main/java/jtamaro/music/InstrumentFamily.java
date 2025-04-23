package jtamaro.music;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum InstrumentFamily {

  PIANO(1, 8),
  CHROMATIC_PERCUSSION(9, 16),
  ORGAN(17, 24),
  GUITAR(25, 32),
  BASS(33, 40),
  STRINGS(41, 48),
  ENSEMBLE(49, 56),
  BRASS(57, 64),
  REED(65, 72),
  PIPE(73, 80),
  SYNTH_LEAD(81, 88),
  SYNTH_PAD(89, 96),
  SYNTH_EFFECTS(97, 104),
  ETHNIC(105, 112),
  PERCUSSIVE(113, 120),
  SOUND_EFFECTS(121, 128),
  ;

  private final int minPcNumber;

  private final int maxPcNumber;

  InstrumentFamily(int minPcNumber, int maxPcNumber) {
    this.minPcNumber = minPcNumber;
    this.maxPcNumber = maxPcNumber;
  }

  public static InstrumentFamily familyForPcNumber(int pcNumber) {
    for (InstrumentFamily family : values()) {
      if (pcNumber >= family.minPcNumber && pcNumber <= family.maxPcNumber) {
        return family;
      }
    }
    return null;
  }

  public String capitalizedName() {
    return capitalizedName(name());
  }

  // convert XXX_XXX_XXX into Xxx Xxx Xxx
  public static String capitalizedName(String name) {
    return Arrays
        .stream(name.split("_"))
        .map(word -> word.isEmpty()
            ? word
            : Character.toTitleCase(word.charAt(0)) + word
                .substring(1)
                .toLowerCase())
        .collect(Collectors.joining(" "));
  }
}
