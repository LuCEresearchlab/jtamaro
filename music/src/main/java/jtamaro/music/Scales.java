package jtamaro.music;

import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

import static jtamaro.music.Notes.A4;
import static jtamaro.music.Notes.C4;

public class Scales {

  public static Scale scale(Note root, Sequence<Integer> scaleType) {
    return new Scale(root, scaleType);
  }

  public static final Sequence<Integer> MAJOR = Sequences.of(0, 2, 4, 5, 7, 9, 11);

  public static Scale C_MAJOR = new Scale(C4, MAJOR);

  public static Scale A_MAJOR = new Scale(A4, MAJOR);

  public static final Sequence<Integer> MINOR = Sequences.of(0, 2, 3, 5, 7, 8, 10);

  public static Scale C_MINOR = new Scale(C4, MINOR);

  public static Scale A_MINOR = new Scale(A4, MINOR);

  public static final Sequence<Integer> MAJOR_PENTATONIC = Sequences.of(0, 2, 4, 7, 9);

  public static Scale C_MAJOR_PENTATONIC = new Scale(C4, MAJOR_PENTATONIC);

  public static Scale A_MAJOR_PENTATONIC = new Scale(A4, MAJOR_PENTATONIC);

  private Scales() {
  }
}
