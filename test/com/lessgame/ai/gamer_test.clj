(ns com.lessgame.ai.gamer-test
  (:require [com.lessgame.ai.gamer :as g]
            [midje.sweet :refer :all]))

(fact "yellow makes the first move of the game"
      (g/start-playing) => nil
      (provided
        (read-line) =streams=> ["0000" "Yellow" "Quit"] :times 3))

(fact "black makes the second move of the game"
      (g/start-playing) => nil
      (provided
        (read-line) =streams=> ["0000" "Black" anything "Quit"] :times 4))

(fact "white makes the third move of the game"
      (g/start-playing) => nil
      (provided
        (read-line) =streams=> ["0000" "White" anything anything "Quit"] :times 5))

(fact "white makes the third move of the game"
      (g/start-playing) => nil
      (provided
        (read-line) =streams=> ["0000" "Red" anything anything anything "Quit"] :times 6))
