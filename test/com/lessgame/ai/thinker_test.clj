(ns com.lessgame.ai.thinker-test
  (:require [com.lessgame.ai.thinker :as t]
            [midje.sweet :refer :all]))

(fact ""
      )

(fact "4x4 board with no walls and only yellow at the bottom left"
      (let [state {:board "0000"
                   :yellow "--y-"}
            move {:yellow [2 3]}]
        (t/isValid state move) => true))

;
;(fact "4x4 board with no walls, invalid move when yellow tries to move on to another piece"
;      (let [state {:board "0000"
;                   :yellow "--yw"}
;            move {:yellow [2 3]}]
;        (t/isValid state move) => false))
;
