(ns com.lessgame.game-engine
  (:require [com.lessgame.reader.board-reader :as br]
            [com.lessgame.reader.player-reader :as pr]
            [com.lessgame.reader.move-reader :as mr]
            [com.lessgame.display.display-state :as d]
            [com.lessgame.ai.thinker :as player]
            [com.lessgame.display.logger :as log]))

(def ORDER_OF_PLAY [:yellow :black :white :red])

(def BOARD_WIDTH 8)
(def ACTION_FOR_MOVE {:left   #(bit-shift-right % 1)
                      :right  #(bit-shift-left % 1)
                      :up     #(bit-shift-right % BOARD_WIDTH)
                      :down   #(bit-shift-left % BOARD_WIDTH)
                      :get    #(bit-shift-left 1 %)})

(defn- play-turn? [{:keys [player]} current-turn]
  (= player current-turn))

(defn- read-instruction []
  (log/debug "Waiting for instructions...")
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

(defn- find-piece [pieces-for-player pos]
  (.indexOf pieces-for-player pos))

(defn- calculation-destination-pos [{:keys [move pos value]}]
  (cond
    (or (= move :up) (= move :down))    (+ pos (* value BOARD_WIDTH))
    (or (= move :left) (= move :right)) (+ pos value)))

(defn- apply-instruction [state instruction player]
  (let [pos (:pos instruction)
        updated-state (assoc-in state
                        [player (find-piece (player state) pos)]
                        (calculation-destination-pos instruction))]
    updated-state))

; --- Public for testing only... yiks... ---

(defn update-state [{:keys [yellow] :as state}
                    {:keys [pos move]}]
  (let [action    (ACTION_FOR_MOVE move)
        start-pos ((ACTION_FOR_MOVE :get) pos)
        end-pos   (action start-pos)]
    (assoc state
      :yellow (bit-or (bit-xor yellow start-pos) end-pos))))

(defn start-playing [s]
  (loop [state s
         turns (cycle ORDER_OF_PLAY)]
    (let [instruction (process-turn state (first turns))]
      (when instruction
        (recur (update-state state instruction) (rest turns))))))

; --- Pubic methods below ---

(defn create-game [board-str]
  (let [state {:board  (br/parse-board board-str)
               :size   8
               :yellow [48 49 56 57]
               :black  [0 1 8 9]
               :red    [6 7 14 15]
               :white  [54 55 62 63]}]
    (d/print-board state)
    state))

(defn take-turn
  ([state instructions] (take-turn state instructions :yellow))
  ([state instructions player]
   (reduce #(apply-instruction %1 %2 player) state (mr/parse-instructions instructions))))
