(ns com.lessgame.ai.gamer-test
  (:require [com.lessgame.ai.gamer :as g]
            [midje.sweet :refer :all]))

(facts ""
 (let [board "0000"
       yellow-move anything
       black-move anything
       white-move anything
       red-move anything
       quit "Quit"]
   (fact "yellow makes the first move of the game"
         (g/start-playing) => nil
         (provided
           (println "b1b2") => anything :times 1
           (read-line) =streams=> [board "Yellow"
                                   quit] :times 3))

   (fact "black makes the second move of the game"
         (g/start-playing) => nil
         (provided
           (read-line) =streams=> [board "Black"
                                   yellow-move
                                   quit] :times 4))

   (fact "white makes the third move of the game"
         (g/start-playing) => nil
         (provided
           (read-line) =streams=> [board "White"
                                   yellow-move
                                   black-move
                                   quit] :times 5))

   (fact "red makes the fourth move of the game"
         (g/start-playing) => nil
         (provided
           (read-line) =streams=> [board "Red"
                                   yellow-move
                                   black-move
                                   white-move
                                   quit] :times 6))

   (fact "is yellow's turn again after 3 moves have been played"
         (g/start-playing) => nil
         (provided
           (println "b1b2") => anything :times 2
           (read-line) =streams=> [board "Yellow"
                                   yellow-move
                                   black-move
                                   white-move
                                   red-move
                                   quit]))))
