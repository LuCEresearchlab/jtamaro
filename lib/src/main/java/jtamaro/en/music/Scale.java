package jtamaro.en.music;

import static jtamaro.en.Sequences.map;
import static jtamaro.en.Sequences.of;

import jtamaro.en.Sequence;


public record Scale(Note rootNote, Sequence<Integer> transpositions) {

  public Note get(int index) {
    return rootNote.transpose(transpositions.get(index - 1));
  }

  public Sequence<Note> notes() {
    return map(t -> rootNote.transpose(t), transpositions);
  }

  public AbsoluteChord in(RelativeChord chord) {
    return chord.chord().on(get(chord.scaleStep()));
  }

  public AbsoluteChord triad() {
    return new AbsoluteChord(of(get(1), get(3), get(5)));
  }

  public AbsoluteChord triad(int degree) {
    // if degree is 1, give 1-3-5
    return new AbsoluteChord(of(get(degree), get(degree+2), get(degree+4)));
  }
  
}
