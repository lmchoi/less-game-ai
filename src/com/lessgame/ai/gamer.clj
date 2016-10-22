(ns com.lessgame.ai.gamer
  (:require [com.lessgame.reader.game-reader :as gr]))

(def ORDER_OF_PLAY [:yellow :black :white :red])

(defn- play-turn []
  "b1b2")

(defn- watch []
  (let [input (read-line)]
    (when-not (= "Quit" input)
      input)))

(defn my-turn? [{:keys [player]} current-turn]
  (= player current-turn))

(defn- next-move [state player]
  (if (my-turn? state player)
    (play-turn)
    (watch)))

(defn- process-turn [state player]
  (let [move (next-move state player)]
    (when-not (nil? move)
      move)))

(defn start-playing []
  (let [board-str   (read-line)
        player-str  (read-line)
        state       (gr/read-game board-str player-str)]

    (when-not (nil? (process-turn state :yellow))
      (when-not (nil? (process-turn state :black))
        (when-not (nil? (process-turn state :white))
          (when-not (nil? (process-turn state :red))
            (process-turn state :yellow)))))))
