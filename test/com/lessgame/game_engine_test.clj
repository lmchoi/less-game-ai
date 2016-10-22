(ns com.lessgame.game-engine-test
  (:require [com.lessgame.game-engine :as gr]
            [midje.sweet :refer :all]))

(fact "create 2x2 game for yellow player"
      (gr/create-game "0000" "Yellow") => {:board "0000"
                                           :player :yellow})
