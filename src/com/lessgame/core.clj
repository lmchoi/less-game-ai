(ns com.lessgame.core
  (:require [com.lessgame.game-engine :as game]
            [com.lessgame.display.logger :as log]
            [com.lessgame.display.display-state :as d]))

(defn- prompt-board []
  (log/debug "Please enter board string: ")
  (read-line))

(defn- prompt-move []
  (log/debug "Please enter move: ")
  (read-line))

(defn -main "Play LESS" [& args]
  (let [board-str (prompt-board)
        state (game/create-game board-str)
        value (game/take-turn state (prompt-move))]
    (d/print-board value)))
