(ns com.lessgame.core
  (:require [com.lessgame.game-engine :as game]
            [com.lessgame.ai.gamer :as player]))

(defn -main "Play LESS" [& args]
  (-> game/init
      player/start-playing))
