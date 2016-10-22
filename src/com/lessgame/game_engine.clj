(ns com.lessgame.game-engine
  (:require [com.lessgame.reader.board-reader :as br]
            [com.lessgame.reader.player-reader :as pr]))

(defn create-game [board-str player-str]
  {:board  (br/parse-board board-str)
   :player (pr/parse-player player-str)})

(defn init []
  (let [board-str (read-line)
        player-str (read-line)
        state (create-game board-str player-str)]
    state))
