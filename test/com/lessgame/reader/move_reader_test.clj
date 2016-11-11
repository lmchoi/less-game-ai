(ns com.lessgame.reader.move-reader-test
  (:require [com.lessgame.reader.move-reader :as mr]
            [midje.sweet :refer :all]))

(facts "parse instruction with one move"
       (fact "move right"
             (mr/parse-instructions "b1b2") => [{:pos   8
                                                 :move  :right
                                                 :value 1}])

       (fact "left"
             (mr/parse-instructions "a2a1") => [{:pos   1
                                                 :move  :left
                                                 :value 1}])

       (fact "up"
             (mr/parse-instructions "b1a1") => [{:pos   8
                                                 :move  :up
                                                 :value 1}])

       (fact "down"
             (mr/parse-instructions "a1b1") => [{:pos   0
                                                 :move  :down
                                                 :value 1}])

       (fact "jump down"
             (mr/parse-instructions "a1c1") => [{:pos   0
                                                 :move  :down
                                                 :value 2}])

       (fact "jump right"
             (mr/parse-instructions "a1a3") => [{:pos   0
                                                 :move  :right
                                                 :value 2}])

       (fact "jump left"
             (mr/parse-instructions "a3a1") => [{:pos   2
                                                 :move  :left
                                                 :value 2}])

       (fact "jump up"
             (mr/parse-instructions "c2a2") => [{:pos   17
                                                 :move  :up
                                                 :value 2}]))

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

(facts "translate one move to instruction"
       (fact "right"
             (mr/translate-move {:pos   8
                                 :move  :right
                                 :value 1}) => "b1b2")

       (fact "left"
             (mr/translate-move {:pos   9
                                 :move  :left
                                 :value 1}) => "b2b1")

       (fact "up"
             (mr/translate-move {:pos   8
                                 :move  :up
                                 :value 1}) => "b1a1")

       (fact "down"
             (mr/translate-move {:pos   0
                                 :move  :down
                                 :value 1}) => "a1b1")

       (fact "jump down"
             (mr/translate-move {:pos   0
                                 :move  :down
                                 :value 2}) => "a1c1")

       (fact "jump up"
             (mr/translate-move {:pos   16
                                 :move  :up
                                 :value 2}) => "c1a1")

       (fact "jump right"
             (mr/translate-move {:pos   0
                                 :move  :right
                                 :value 2}) => "a1a3")

       (fact "jump left"
             (mr/translate-move {:pos   2
                                 :move  :left
                                 :value 2}) => "a3a1"))

(facts "translate a set of moves to instruction"
      (fact "no move"
            (mr/translate-instruction []) => nil)

      (fact "two moves"
            (mr/translate-instruction [{:pos   0
                                        :move  :right
                                        :value 1}
                                       {:pos   1
                                        :move  :right
                                        :value 1}]) => "a1a2:a2a3")

      (fact "two moves"
            (mr/translate-instruction [{:cost 1 :move :down :pos 1 :value 1}
                                       {:cost 1 :move :right :pos 9 :value 1}
                                       {:cost 1 :move :down :pos 10 :value 1}]) => "a2b2:b2b3:b3c3"))

