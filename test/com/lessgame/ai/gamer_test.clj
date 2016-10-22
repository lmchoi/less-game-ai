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
         (g/start-playing {:board board
                           :player :yellow}) => anything
         (provided
           (println "b1b2") => anything :times 1
           (read-line) =streams=> [quit] :times 1))

   (fact "black makes the second move of the game"
         (g/start-playing {:board board
                           :player :black}) => anything
         (provided
           (read-line) =streams=> [yellow-move
                                   quit] :times 2))

   (fact "white makes the third move of the game"
         (g/start-playing {:board board
                           :player :white}) => anything
         (provided
           (read-line) =streams=> [yellow-move
                                   black-move
                                   quit] :times 3))

   (fact "red makes the fourth move of the game"
         (g/start-playing {:board board
                           :player :red}) => anything
         (provided
           (read-line) =streams=> [yellow-move
                                   black-move
                                   white-move
                                   quit] :times 4))

   (fact "is yellow's turn again after 3 moves have been played"
         (g/start-playing {:board board
                           :player :yellow}) => anything
         (provided
           (println "b1b2") => anything :times 2
           (read-line) =streams=> [yellow-move
                                   black-move
                                   white-move
                                   red-move
                                   quit]))))
