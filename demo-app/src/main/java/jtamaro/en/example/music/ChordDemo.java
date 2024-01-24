package jtamaro.en.example.music;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import static jtamaro.en.Music.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.music.Notes.*;
import static jtamaro.en.music.Scales.*;
import static jtamaro.en.IO.*;


public class ChordDemo {

  public static void main(String[] args) {
    playChords(map(n->chord(of(n)), scale(D4, MINOR).notes()), 120);
    /*
    play(of(C_MAJOR.triad()), 400);
    play(of(C_MAJOR.triad(1)), 400);
    play(of(C_MAJOR.triad(2)), 400);
    play(of(C_MAJOR.triad(3)), 400);
    play(of(C_MAJOR.triad(4)), 400);
    play(of(C_MAJOR.triad(5)), 400);
    Note C4 = note(60);
    play(map(n->chord(1, of(n)), majorScale(C4)), 400);
    play(map(n->chord(1, of(n)), majorScale(transpose(C4, 1))), 400);
    play(map(n->chord(1, of(n)), majorScale(transpose(C4, 2))), 400);
    */
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
      System.out.println("Device: " + deviceInfo);
      try {
        final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
        if (device instanceof Synthesizer) {
          System.out.println("  Synthesizer");
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
          System.out.println("First Synthesizer opened.");
        }
      } catch (MidiUnavailableException ex) {
        ex.printStackTrace();
      }
    }
  }

}
