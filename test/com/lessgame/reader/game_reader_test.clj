(ns com.lessgame.reader.game-reader-test
  (:require [com.lessgame.reader.game-reader :as gr]
            [midje.sweet :refer :all]))

(fact "read empty board"
      (gr/read-game "0000" "Yellow") => {:board "0000" :player :yellow})
