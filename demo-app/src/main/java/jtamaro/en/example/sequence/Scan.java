package jtamaro.en.example.sequence;

import static jtamaro.en.IO.println;
import static jtamaro.en.Sequences.*;

import jtamaro.en.Function2;
import jtamaro.en.Sequence;


public class Scan {
    
    // scanl (+) 0 [3,5,2,1] = [0,3,8,10,11]
    // http://learnyouahaskell.com/higher-order-functions#folds
    public static <S,T> Sequence<T> scan(Function2<S,T,T> combine, T acc, Sequence<S> sequence) {
        return isEmpty(sequence)
            ? cons(acc, empty())
            : cons(
                acc,
                scan(combine, combine.apply(first(sequence), acc), rest(sequence))
            );
    }
    

    public static void main(String[] args) {
      println(scan((a, b) -> a + b, 0, of(1, 3, 2, 9, 2, 1, 8, 7)));
    }

}
