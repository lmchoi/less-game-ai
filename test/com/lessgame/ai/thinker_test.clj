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
                             :ai-colour :yellow
                             :pieces    [{:pos 0 :distance 0}]}]
               (ai/play-turn ai-state)) => []))

(facts "make one move to end the game"
       (fact "move right to end the game"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [1]}
                                         :current-turn :yellow}
                             :ai-colour :yellow
                             :pieces    [{:pos 0 :distance 1}]}]
               (ai/play-turn ai-state)) => [{:cost 1 :move :right :pos 0 :value 1}])

       (fact "move down to end the game"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [2]}
                                         :current-turn :yellow}
                             :ai-colour :yellow
                             :pieces    [{:pos 0 :distance 1}]}]
               (ai/play-turn ai-state)) => [{:cost 1 :move :down :pos 0 :value 1}]))

(facts "make two moves to end the game"
       (fact "move right then down"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [3]}
                                         :current-turn :yellow}
                             :ai-colour :yellow
                             :pieces    [{:pos 0 :distance 2}]}]
               (ai/play-turn ai-state))
             => [{:cost 1 :move :right :pos 0 :value 1}
                 {:cost 1 :move :down :pos 1 :value 1}]))


(facts "make three moves"
       (fact "max of 3 moves"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         3
                                         :yellow       [0]
                                         :end-game     {:yellow [8]}
                                         :current-turn :yellow}
                             :ai-colour :yellow
                             :pieces    [{:pos 0 :distance 4}]}]
               (ai/play-turn ai-state))
             => [{:cost 1 :move :right :pos 0 :value 1}
                 {:cost 1 :move :down :pos 1 :value 1}
                 {:cost 1 :move :right :pos 4 :value 1}])

       (fact "to end the game"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         3
                                         :yellow       [1]
                                         :end-game     {:yellow [8]}
                                         :current-turn :yellow}
                             :ai-colour :yellow
                             :pieces    [{:pos 1 :distance 3}]}]
               (ai/play-turn ai-state))
             => [{:cost 1 :move :down :pos 1 :value 1}
                 {:cost 1 :move :right :pos 4 :value 1}
                 {:cost 1 :move :down :pos 5 :value 1}]))

(facts "ai has 2 pieces"
       (let [state {:board        empty-board
                    :size         3
                    :yellow       [0 2]
                    :end-game     {:yellow [8 7]}
                    :current-turn :yellow}]

         (fact "move the piece furthest away first"
               (ai/play-turn (ai/create-thinker :yellow state)) => [{:cost 1 :move :right :pos 0 :value 1}
                                                                    {:cost 1 :move :down  :pos 1 :value 1}
                                                                    {:cost 1 :move :down  :pos 2 :value 1}])

         (fact "jump to the right"
               (ai/play-turn (ai/create-thinker :yellow (assoc state :yellow [0 1])))
               => [{:cost 1 :move :right :pos 0 :value 2}
                   {:cost 1 :move :down  :pos 2 :value 1}
                   {:cost 1 :move :down  :pos 1 :value 1}])
       ))


(facts "ai has 4 pieces"
       (let [state {:board        empty-board
                    :size         3
                    :yellow       [0 1 3 4]
                    :end-game     {:yellow [8 7 5 4]}
                    :current-turn :yellow}
             ai-state {:ai-state  state
                       :ai-colour :yellow
                       :pieces    [{:pos 0 :distance 4} 
                                   {:pos 1 :distance 2}
                                   {:pos 3 :distance 2}
                                   {:pos 4 :distance 0}]}]

         (fact "create ai"
               (ai/create-thinker :yellow state) => ai-state)

         (fact "think"
               (ai/think ai-state (assoc state :yellow [2 7 5 4])) => {:ai-state  (assoc state :yellow [2 7 5 4])
                                                                       :ai-colour :yellow
                                                                       :pieces    [{:pos 2 :distance 2}
                                                                                   {:pos 7 :distance 0}
                                                                                   {:pos 5 :distance 0}
                                                                                   {:pos 4 :distance 0}]})
         #_(fact "move the piece furthest away first"
               (ai/play-turn ai-state) => [{:cost 1 :move :right :pos 0 :value 2}
                                           {:cost 1 :move :down  :pos 1 :value 2}
                                           {:cost 1 :move :right :pos 3 :value 2}]))
       )
