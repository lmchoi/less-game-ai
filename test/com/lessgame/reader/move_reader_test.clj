(ns com.lessgame.reader.move-reader-test
  (:require [com.lessgame.reader.move-reader :as mr]
            [midje.sweet :refer :all]))

(fact "parse for 2x2 board"
      (mr/parse-instructions "b1b2:a1a2:a2b2") => [[0 1 1 1]
                                                   [0 0 1 0]
                                                   [1 0 1 1]])
