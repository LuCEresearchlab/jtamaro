package jtamaro.example.sequence;

import jtamaro.data.Sequence;

import static jtamaro.data.Sequences.concat;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.rangeClosed;
import static jtamaro.io.IO.println;

public class Digits {

  public static void main(String[] args) {
    final Sequence<Character> binaryDigits = of('0', '1');
    println(binaryDigits);
    println();

    final Sequence<Character> octalDigits = rangeClosed('0', '7');
    println(octalDigits);
    println();

    final Sequence<Character> decimalDigits = rangeClosed('0', '9');
    println(decimalDigits);
    println();

    final Sequence<Character> hexadecimalDigits = concat(
        rangeClosed('0', '9'),
        rangeClosed('A', 'F')
    );
    println(hexadecimalDigits);
    println();

    final Sequence<Character> rotaryPhoneDigits = concat(
        rangeClosed('1', '9'),
        of('0')
    );
    println(rotaryPhoneDigits);
    println();
  }

}
