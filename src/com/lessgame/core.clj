(ns com.lessgame.core
  (:require [com.lessgame.ai.gamer :as gamer]))

(defn -main "Play LESS" [& args]
  (gamer/start-playing))
