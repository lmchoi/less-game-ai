(ns com.lessgame.reader.move-reader-test
  (:require [com.lessgame.reader.move-reader :as mr]
            [midje.sweet :refer :all]))

;TODO-MC tidy tests in move reader
(fact "parse one instruction to move to the right"
      (mr/parse-instructions "b1b2") => [{:pos  8
                                          :move :right
                                          :value 1}])

(fact "parse one instruction to move to the left"
      (mr/parse-instructions "a2a1") => [{:pos  1
                                          :move :left
                                          :value -1}])

(fact "parse one instruction to move up"
      (mr/parse-instructions "b1a1") => [{:pos  8
                                          :move :up
                                          :value -1}])

(fact "parse one instruction to move down"
      (mr/parse-instructions "a1b1") => [{:pos  0
                                          :move :down
                                          :value 1}])

(fact "parse one instruction to jump down"
      (mr/parse-instructions "a1c1") => [{:pos  0
                                          :move :down
                                          :value 2}])

(fact "parse one instruction to jump to the right"
      (mr/parse-instructions "a1a3") => [{:pos  0
                                          :move :right
                                          :value 2}])

(fact "parse one instruction to jump to the left"
      (mr/parse-instructions "a3a1") => [{:pos  2
                                          :move :left
                                          :value -2}])

(fact "parse one instruction to jump up"
      (mr/parse-instructions "c2a2") => [{:pos  17
                                          :move :up
                                          :value -2}])

(fact "parse one instruction with 3 moves"
      (mr/parse-instructions "b1b2:a1a2:a2b2") => [{:pos  8
                                                    :move :right
                                                    :value 1}
                                                   {:pos  0
                                                    :move :right
                                                    :value 1}
                                                   {:pos  1
                                                    :move :down
                                                    :value 1}])

(fact "translate move right to instruction"
      (mr/translate-move {:pos 8
                                 :move :right
                                 :value 1}) => "b1b2")

(fact "translate move left to instruction"
      (mr/translate-move {:pos 9
                                 :move :left
                                 :value -1}) => "b2b1")

(fact "translate move up to instruction"
      (mr/translate-move {:pos 8
                                 :move :up
                                 :value -1}) => "b1a1")

(fact "translate move down to instruction"
      (mr/translate-move {:pos 0
                                 :move :down
                                 :value 1}) => "a1b1")

(fact "translate instruction"
      (fact "no move"
            (mr/translate-instruction []) => nil)

      (fact "one move"
            (mr/translate-instruction [{:pos 0
                                        :move :down
                                        :value 1}]) => "a1b1")

      (fact "two moves"
            (mr/translate-instruction [{:pos 0
                                        :move :right
                                        :value 1}
                                       {:pos 1
                                        :move :right
                                        :value 1}]) => "a1a2:a2a3")
      (fact "two moves"
            (mr/translate-instruction [{:cost 1 :move :down :pos 1 :value 1}
                                       {:cost 1 :move :right :pos 9 :value 1}
                                       {:cost 1 :move :down :pos 10 :value 1}]) => "a2b2:b2b3:b3c3"))

