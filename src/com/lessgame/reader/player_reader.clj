(ns com.lessgame.reader.player-reader)

(defn parse-player [player-str]
  (keyword (clojure.string/lower-case player-str)))
