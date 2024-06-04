package jtamaro.example.music;

import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import jtamaro.data.Sequence;

import static jtamaro.data.Sequences.of;
import static jtamaro.io.IO.println;


interface Event {

  void perform(Receiver receiver, int msPerTick);
}

record NoteOn(int noteNumber, int velocity) implements Event {

  public void perform(Receiver receiver, int msPerTick) {
    try {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_ON, 0, noteNumber, velocity);
      long timeStamp = -1;
      receiver.send(message, timeStamp);
    } catch (InvalidMidiDataException ex) {
      ex.printStackTrace();
    }
  }
}

record NoteOff(int noteNumber) implements Event {

  public void perform(Receiver receiver, int msPerTick) {
    try {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_OFF, 0, noteNumber);
      long timeStamp = -1;
      receiver.send(message, timeStamp);
    } catch (InvalidMidiDataException ex) {
      ex.printStackTrace();
    }
  }
}

record Tick() implements Event {

  public void perform(Receiver receiver, int msPerTick) {
    try {
      println("tick -- " + System.nanoTime());
      Thread.sleep(msPerTick);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }
}

public class EventDemo {

  public static Event noteOn(int noteNumber, int velocity) {
    assert noteNumber >= 0 && noteNumber <= 127;
    assert velocity >= 0;
    return new NoteOn(noteNumber, velocity);
  }

  public static Event noteOff(int noteNumber) {
    assert noteNumber >= 0 && noteNumber <= 127;
    return new NoteOff(noteNumber);
  }

  public static Event tick() {
    return new Tick();
  }

  public static void play(Sequence<Event> song, int bpm) {
    int msPerTick = 60 * 1000 / bpm;
    try (Receiver receiver = MidiSystem.getReceiver()) {
      for (Event event : song) {
        event.perform(receiver, msPerTick);
        //Thread.sleep(1000);
      }
    } catch (MidiUnavailableException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Sequence<Event> song = of(
        noteOn(60, 127),
        tick(),
        noteOff(60),
        noteOn(60 + 12, 127),
        tick(),
        noteOff(60 + 12),
        noteOn(60 + 12 + 12, 127),
        tick(),
        noteOff(60 + 12 + 12),
        tick(),
        noteOn(60, 127),
        noteOn(60 + 12, 127),
        noteOn(60 + 12 + 12, 127),
        tick()
    );
    play(song, 120);
  }

  public static void m() {
    try {
      Receiver receiver = MidiSystem.getReceiver();
      ShortMessage message = new ShortMessage();
      // Start playing the note Middle C (60), moderately loud (velocity = 93).
      message.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
      long timeStamp = -1;
      receiver.send(message, timeStamp);
      Thread.sleep(1000);
    } catch (MidiUnavailableException ex) {
      ex.printStackTrace();
    } catch (InvalidMidiDataException ex) {
      ex.printStackTrace();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  public static void find() {
    ArrayList<MidiDevice.Info> synthInfos = new ArrayList<>();
    for (MidiDevice.Info deviceInfo : MidiSystem.getMidiDeviceInfo()) {
      println("Device: " + deviceInfo);
      try {
        final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
        if (device instanceof Synthesizer) {
          println("  Synthesizer");
          synthInfos.add(deviceInfo);
        }
      } catch (MidiUnavailableException ex) {
        ex.printStackTrace();
      }
    }
    if (!synthInfos.isEmpty()) {
      final MidiDevice.Info deviceInfo = synthInfos.get(0);
      try {
        final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
        if (!(device.isOpen())) {
          device.open();
          println("First Synthesizer opened.");
        }
      } catch (MidiUnavailableException ex) {
        ex.printStackTrace();
      }
    }
  }

}
