(ns com.lessgame.core
  (:require [com.lessgame.io.caia-less-io :as caia-io]))

(defn -main "Play LESS" [& args]
  (caia-io/start))
