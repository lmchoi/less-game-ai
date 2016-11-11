(ns com.lessgame.ai.thinker-test
  (:require [com.lessgame.ai.thinker :as ai]
            [midje.sweet :refer :all]))

(def empty-board (str "00"
                      "000"
                      "00"
                      "000"
                      "00"
                      "000"))

(facts "do not make a move"
       (fact "on end game"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         1
                                         :yellow       [0]
                                         :end-game     {:yellow [0]}
                                         :current-turn :yellow}
                             :ai-colour :yellow}]
               (ai/play-turn ai-state)) => []))

(facts "make one move to end the game"
       (fact "move right to end the game"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [1]}
                                         :current-turn :yellow}
                             :ai-colour :yellow}]
               (ai/play-turn ai-state)) => [{:cost 1 :move :right :pos 0 :value 1}])

       (fact "move down to end the game"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [2]}
                                         :current-turn :yellow}
                             :ai-colour :yellow}]
               (ai/play-turn ai-state)) => [{:cost 1 :move :down :pos 0 :value 1}]))

(facts "make two moves to end the game"
       (fact "move right then down"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [3]}
                                         :current-turn :yellow}
                             :ai-colour :yellow}]
               (ai/play-turn ai-state))
             => [{:cost 1 :move :right :pos 0 :value 1}
                 {:cost 1 :move :down :pos 1 :value 1}]))


(facts "make three moves to end the game"
       (fact "move down, right, down"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         3
                                         :yellow       [1]
                                         :end-game     {:yellow [8]}
                                         :current-turn :yellow}
                             :ai-colour :yellow}]
               (ai/play-turn ai-state))
             => [{:cost 1 :move :down :pos 1 :value 1}
                 {:cost 1 :move :right :pos 4 :value 1}
                 {:cost 1 :move :down :pos 5 :value 1}]))
