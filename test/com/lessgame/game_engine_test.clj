(ns com.lessgame.game-engine-test
  (:require [com.lessgame.game-engine :as engine]
            [midje.sweet :refer :all]))

(let [board-str "0100000010000020000101001211000110000010010000101000000000010001101010001001000010000000000000100000101011001010"]
  (fact "create game given a string that describes the board"
        (engine/create-game board-str) => {:board  board-str
                                           :size   8
                                           :black  [0 1 8 9]
                                           :red    [6 7 14 15]
                                           :white  [54 55 62 63]
                                           :yellow [48 49 56 57]
                                           :turn   0
                                           :current-turn :yellow
                                           :end-game engine/END_GAME})

  (fact "take turn to move yellow up one space"
        (engine/take-turn (engine/create-game board-str) "g1f1") => (contains {:yellow [40 49 56 57]}))

  (fact "take turn to move black one space to the left"
        (engine/take-turn (engine/create-game board-str) "a2a3" :black) => (contains {:black [0 2 8 9]}))

  (fact "take turn to move white one space to the right"
        (engine/take-turn (engine/create-game board-str) "h7h6" :white) => (contains {:white [54 55 61 63]}))

  (fact "take turn to move red down one space"
        (engine/take-turn (engine/create-game board-str) "b8c8" :red) => (contains {:red [6 7 14 23]}))

  (fact "take turn to move yellow pieces"
        (engine/take-turn (engine/create-game board-str) "h2h3:g1f1" :yellow) => (contains {:yellow [40 49 56 58]}))

  ; Note the ordering in the vector...
  (fact "take turn to move yellow pieces"
        (engine/take-turn (engine/create-game board-str) "h2h3:g1f1:h1g1" :yellow) => (contains {:yellow [40 49 48 58]}))

  (fact "take turn to jump over another piece"
        (engine/take-turn (engine/create-game board-str) "h1f1" :yellow) => (contains {:yellow [48 49 40 57]}))
  )
