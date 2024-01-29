package jtamaro.en.music;

import javax.sound.midi.Receiver;

import static jtamaro.en.Sequences.*;


public record TimedChord(int beats, AbsoluteChord chord) {
  
  public void play(Receiver receiver, int channel, int msPerBeat) {
    for (Note note : chord.notes()) {
      note.on(receiver, channel);
    }
    try {
      System.out.println("chord -- " + System.nanoTime() + " (" + beats + ")");
      Thread.sleep(beats * msPerBeat);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    for (Note note : chord.notes()) {
      note.off(receiver, channel);
    }
  }
  public String toString() {
    return reduce("", (a,e)->a+e, intersperse("-", map(n->n.toString(), chord.notes())));
  }

}
