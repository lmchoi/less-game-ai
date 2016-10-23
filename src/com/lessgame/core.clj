(ns com.lessgame.core
  (:require [com.lessgame.game-engine :as game]
            [com.lessgame.display.logger :as log]))

(defn -main "Play LESS" [& args]
  (log/debug "Please enter board string: ")
  (let [board-str (read-line)]
    (game/create-game board-str)))
