(ns com.lessgame.game-engine
  (:require [com.lessgame.reader.board-reader :as br]
            [com.lessgame.reader.player-reader :as pr]
            [com.lessgame.reader.move-reader :as mr]
            [com.lessgame.ai.thinker :as player]))

(def ORDER_OF_PLAY [:yellow :black :white :red])

(defn play-turn? [{:keys [player]} current-turn]
  (= player current-turn))

(defn- read-instruction []
  (let [instruction (read-line)]
    (when-not (= "Quit" instruction)
      instruction)))

(defn- write-instruction [move]
  (let [instruction (mr/translate move)]
    (println instruction)
    instruction))

(defn- process-turn [state player]
  (if (play-turn? state player)
    (write-instruction (player/play-turn))
    (read-instruction)))

(defn- update-state [state instruction]
  state)

(defn start-playing [state]
  (loop [s      state
         turns  (cycle ORDER_OF_PLAY)]
    (let [instruction (process-turn s (first turns))]
      (when instruction
        (recur (update-state s instruction) (rest turns))))))

(defn create-game [board-str player-str]
  {:board  (br/parse-board board-str)
   :player (pr/parse-player player-str)})

(defn init []
  (let [board-str (read-line)
        player-str (read-line)
        state (create-game board-str player-str)]
    (start-playing state)))
