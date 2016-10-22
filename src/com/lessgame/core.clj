(ns com.lessgame.core
  (:require [com.lessgame.game-engine :as game]))

(defn -main "Play LESS" [& args]
  (-> game/init))
