(ns com.lessgame.ai.gamer
  (:require [com.lessgame.reader.game-reader :as gr]))

(def ORDER_OF_PLAY [:yellow :black :white :red])

(defn- play-turn []
  (println "b1b2")
  "b1b2")

(defn- watch []
  (let [input (read-line)]
    (when-not (= "Quit" input)
      input)))

(defn my-turn? [{:keys [player]} current-turn]
  (= player current-turn))

(defn- process-turn [state player]
  (if (my-turn? state player)
    (play-turn)
    (watch)))

(defn start-playing []
  (let [board-str   (read-line)
        player-str  (read-line)
        state       (gr/read-game board-str player-str)]

    (loop [turns  (cycle ORDER_OF_PLAY)]
      (when (process-turn state (first turns))
        (recur (rest turns))))))
