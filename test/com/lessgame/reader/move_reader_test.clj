(ns com.lessgame.reader.move-reader-test
  (:require [com.lessgame.reader.move-reader :as mr]
            [midje.sweet :refer :all]))

(fact "parse one instruction to move to the right for 2x2 board"
      (mr/parse-instructions "b1b2") => [{:pos  2
                                          :move :right}])

(fact "parse one instruction to move to the left for 2x2 board"
      (mr/parse-instructions "a2a1") => [{:pos  1
                                          :move :left}])

(fact "parse one instruction to move up for 2x2 board"
      (mr/parse-instructions "b1a1") => [{:pos  2
                                          :move :up}])

(fact "parse one instruction to move down for 2x2 board"
      (mr/parse-instructions "a1b1") => [{:pos  0
                                          :move :down}])

(fact "parse one instruction with 3 moves for 2x2 board"
      (mr/parse-instructions "b1b2:a1a2:a2b2") => [{:pos  2
                                                    :move :right}
                                                   {:pos  0
                                                    :move :right}
                                                   {:pos  1
                                                    :move :down}])

(fact "translate move right to instruction"
      (mr/translate-instruction {:pos 2
                                 :move :right}) => "b1b2")

(fact "translate move left to instruction"
      (mr/translate-instruction {:pos 3
                                 :move :left}) => "b2b1")

(fact "translate move up to instruction"
      (mr/translate-instruction {:pos 2
                                 :move :up}) => "b1a1")

(fact "translate move down to instruction"
      (mr/translate-instruction {:pos 0
                                 :move :down}) => "a1b1")