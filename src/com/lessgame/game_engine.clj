(ns com.lessgame.game-engine
  (:require [com.lessgame.reader.board-reader :as br]
            [com.lessgame.reader.player-reader :as pr]
            [com.lessgame.reader.move-reader :as mr]
            [com.lessgame.ai.thinker :as player]))

(def ORDER_OF_PLAY [:yellow :black :white :red])

(def BOARD_WIDTH 2)
(def ACTION_FOR_MOVE {:left   #(bit-shift-right % 1)
                      :right  #(bit-shift-left % 1)
                      :up     #(bit-shift-right % BOARD_WIDTH)
                      :down   #(bit-shift-left % BOARD_WIDTH)
                      :get    #(bit-shift-left 1 %)})

(defn- play-turn? [{:keys [player]} current-turn]
  (= player current-turn))

(defn- read-instruction []
  (let [instruction (read-line)]
    (when-not (= "Quit" instruction)
      instruction)))

(defn- write-instruction [move]
  (let [instruction (mr/translate-instruction move)]
    (println instruction)
    instruction))

(defn- process-turn [state player]
  (if (play-turn? state player)
    (write-instruction (player/play-turn))
    (mr/parse-instructions (read-instruction))))

; --- Public for testing only... yiks... ---

(defn update-state [{:keys [y-pieces] :as state}
                    {:keys [pos move]}]
  (let [action    (ACTION_FOR_MOVE move)
        start-pos ((ACTION_FOR_MOVE :get) pos)
        end-pos   (action start-pos)]
    (assoc state
      :y-pieces (bit-or (bit-xor y-pieces start-pos) end-pos))))

(defn start-playing [s]
  (loop [state s
         turns (cycle ORDER_OF_PLAY)]
    (let [instruction (process-turn state (first turns))]
      (when instruction
        (recur (update-state state instruction) (rest turns))))))

(defn create-game [board-str player-str]
  {:board  (br/parse-board board-str)
   :player (pr/parse-player player-str)})

; --- Pubic methods below ---

(defn init []
  (let [board-str (read-line)
        player-str (read-line)
        state (create-game board-str player-str)]
    (start-playing state)))
