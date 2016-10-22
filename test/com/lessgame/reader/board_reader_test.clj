(ns com.lessgame.reader.board-reader-test
  (:require [com.lessgame.reader.board-reader :as br]
            [midje.sweet :refer :all]))

(fact "read empty board"
      (br/parse-board "0000") => "0000")
