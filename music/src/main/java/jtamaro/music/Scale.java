package jtamaro.music;

import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

public record Scale(Note rootNote, Sequence<Integer> transpositions) {

  public Note get(int index) {
    return rootNote.transpose(get(transpositions, index - 1));
  }

  public Sequence<Note> notes() {
    return transpositions.map(rootNote::transpose);
  }

  public AbsoluteChord in(RelativeChord chord) {
    return chord.chord().on(get(chord.scaleStep()));
  }

  public AbsoluteChord triad() {
    return new AbsoluteChord(Sequences.of(get(1), get(3), get(5)));
  }

  public AbsoluteChord triad(int degree) {
    // if degree is 1, give 1-3-5
    return new AbsoluteChord(Sequences.of(get(degree), get(degree + 2), get(degree + 4)));
  }

  private int get(Sequence<Integer> notes, int index) {
    return index == 0
        ? notes.first()
        : get(notes.rest(), index - 1);
  }
}
