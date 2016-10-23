(ns com.lessgame.game-engine-test
  (:require [com.lessgame.game-engine :as engine]
            [midje.sweet :refer :all]))

(let [top-left        2r0001
      top-right       2r0010
      bottom-left     2r0100
      bottom-right    2r1000
      two-at-bottom   2r1100
      one-up-one-down 2r1001
      move-down {:pos  1
                 :move :down}
      move-up {:pos  2
               :move :up}
      move-right {:pos  2
                  :move :right}
      move-left {:pos  3
                 :move :left}
      state {:board  "0000"
             :player :yellow}
      pieces-at #(assoc state :y-pieces %)]
  (facts "update the state to reflect a piece has been moved"
         (tabular
           (fact "move must indicate the direction"
                 (engine/update-state (pieces-at ?starting-position) ?action) => (pieces-at ?expected-destination))
           ?starting-position ?action     ?expected-destination
           bottom-left        move-right  bottom-right
           bottom-right       move-left   bottom-left
           bottom-left        move-up     top-left
           top-right          move-down   bottom-right
           two-at-bottom      move-up     one-up-one-down)))

(fact "create 2x2 game for yellow player"
      (engine/create-game "0000" "Yellow") => {:board "0000"
                                               :player :yellow})
;
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
