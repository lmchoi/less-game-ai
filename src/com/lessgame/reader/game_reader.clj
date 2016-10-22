(ns com.lessgame.reader.game-reader
  (:require [com.lessgame.reader.board-reader :as br]
            [com.lessgame.reader.player-reader :as pr]))

(defn read-game [board-str player-str]
  {:board  (br/parse-board board-str)
   :player (pr/parse-player player-str)})
