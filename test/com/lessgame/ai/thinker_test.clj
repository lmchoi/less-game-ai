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
               (ai/play-turn ai-state)) => nil))

(facts "make one move to end the game"
       (fact "move right to end the game"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [1]}
                                         :current-turn :yellow}
                             :ai-colour :yellow}]
               (ai/play-turn ai-state)) => "a1a2")

       (fact "move down to end the game"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [2]}
                                         :current-turn :yellow}
                             :ai-colour :yellow}]
               (ai/play-turn ai-state)) => "a1b1"))

#_(facts "make two moves to end the game"
       (fact "move right then down"
             (let [ai-state {:ai-state  {:board        empty-board
                                         :size         2
                                         :yellow       [0]
                                         :end-game     {:yellow [3]}
                                         :current-turn :yellow}
                             :ai-colour :yellow}]
               (ai/play-turn ai-state)) => "a1a2:a2b2"))
