(ns com.lessgame.reader.player-reader-test
  (:require [com.lessgame.reader.player-reader :as pr]
            [midje.sweet :refer :all]))

(fact "parse player"
      (pr/parse-player "Yellow") => :yellow)
