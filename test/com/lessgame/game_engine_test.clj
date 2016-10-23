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
                                           :yellow [48 49 56 57]})

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

  ; TODO note the ordering in the vector...
  (fact "take turn to move yellow pieces"
        (engine/take-turn (engine/create-game board-str) "h2h3:g1f1:h1g1" :yellow) => (contains {:yellow [40 49 48 58]}))
  )

; TODO test for 8x8
;(let [top-left        2r0001
;      top-right       2r0010
;      bottom-left     2r0100
;      bottom-right    2r1000
;      two-at-bottom   2r1100
;      one-up-one-down 2r1001
;      move-down {:pos  1
;                 :move :down}
;      move-up {:pos  2
;               :move :up}
;      move-right {:pos  2
;                  :move :right}
;      move-left {:pos  3
;                 :move :left}
;      state {:board  "0000"
;             :player :yellow}
;      pieces-at #(assoc state :yellow %)]
;  (facts "update the state to reflect a piece has been moved"
;         (tabular
;           (fact "move must indicate the direction"
;                 (engine/update-state (pieces-at ?starting-position) ?action) => (pieces-at ?expected-destination))
;           ?starting-position ?action     ?expected-destination
;           bottom-left        move-right  bottom-right
;           bottom-right       move-left   bottom-left
;           bottom-left        move-up     top-left
;           top-right          move-down   bottom-right
;           two-at-bottom      move-up     one-up-one-down)))

; TODO need pieces info in test data!
;(facts "the starting order is based on the colour"
;       (let [board "0000"
;             yellow-move "b1a1"
;             black-move "a1a2"
;             white-move "a2b2"
;             red-move "b2b1"
;             quit "Quit"]
;         (fact "yellow makes the first move of the game"
;               (engine/start-playing {:board board
;                                      :player :yellow}) => anything
;               (provided
;                 (println "b1b2") => anything :times 1
;                 (read-line) =streams=> [quit] :times 1))
;
;         (fact "black makes the second move of the game"
;               (engine/start-playing {:board board
;                                      :player :black}) => anything
;               (provided
;                 (read-line) =streams=> [yellow-move
;                                         quit] :times 2))
;
;         (fact "white makes the third move of the game"
;               (engine/start-playing {:board board
;                                      :player :white}) => anything
;               (provided
;                 (read-line) =streams=> [yellow-move
;                                         black-move
;                                         quit] :times 3))
;
;         (fact "red makes the fourth move of the game"
;               (engine/start-playing {:board board
;                                      :player :red}) => anything
;               (provided
;                 (read-line) =streams=> [yellow-move
;                                         black-move
;                                         white-move
;                                         quit] :times 4))
;
;         (fact "is yellow's turn again after 3 moves have been played"
;               (engine/start-playing {:board board
;                                      :player :yellow}) => anything
;               (provided
;                 (println "b1b2") => anything :times 2
;                 (read-line) =streams=> [yellow-move
;                                         black-move
;                                         white-move
;                                         red-move
;                                         quit]))))
