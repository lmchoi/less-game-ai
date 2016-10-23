(ns com.lessgame.core
  (:require [com.lessgame.game-engine :as game]
            [com.lessgame.reader.input-reader :as in]
            [com.lessgame.display.display-state :as d]))

(defn -main "Play LESS" [& args]
  (let [board-str (in/prompt-board)
        state     (game/create-game board-str)]
    (loop [updated-state  state
           move           (in/prompt-move)]
      (if-not (nil? move)
        (let [new-state (game/take-turn updated-state move)]
          (d/print-board new-state)
          (recur new-state (in/prompt-move)))))))
