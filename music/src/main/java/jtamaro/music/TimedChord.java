package jtamaro.music;

import javax.sound.midi.Receiver;
import jtamaro.data.Sequences;

public record TimedChord(int beats, AbsoluteChord chord) {

  public void play(Receiver receiver, int channel, int msPerBeat) {
    for (Note note : chord.notes()) {
      note.on(receiver, channel);
    }
    try {
      System.out.println("chord -- " + System.nanoTime() + " (" + beats + ")");
      Thread.sleep((long) beats * msPerBeat);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    for (Note note : chord.notes()) {
      note.off(receiver, channel);
    }
  }

  @Override
  public String toString() {
    return chord.notes()
        .map(Note::toString)
        .intersperse("-")
        .reduce("", String::concat);
  }
}
