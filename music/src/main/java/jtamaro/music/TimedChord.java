package jtamaro.music;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Receiver;

public record TimedChord(int beats, AbsoluteChord chord) {

  private static final Logger LOGGER = Logger.getLogger(TimedChord.class.getSimpleName());

  public void play(Receiver receiver, int channel, int msPerBeat) {
    for (Note note : chord.notes()) {
      note.on(receiver, channel);
    }
    try {
      System.out.println("chord -- " + System.nanoTime() + " (" + beats + ")");
      Thread.sleep((long) beats * msPerBeat);
    } catch (InterruptedException ex) {
      LOGGER.log(Level.SEVERE, "Failed to play chord", ex);
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
