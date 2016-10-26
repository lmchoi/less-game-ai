(ns com.lessgame.game-engine
  (:require [com.lessgame.reader.move-reader :as mr]
            [com.lessgame.display.display-state :as d]))

(def ORDER_OF_PLAY [:yellow :black :white :red])

(def BOARD_WIDTH 8)

(defn- find-piece [pieces-for-player pos]
  (.indexOf pieces-for-player pos))

(defn- calculation-destination-pos [{:keys [move pos value]}]
  (cond
    (or (= move :up) (= move :down))    (+ pos (* value BOARD_WIDTH))
    (or (= move :left) (= move :right)) (+ pos value)))

(defn- apply-instruction [state instruction player]
  (let [pos (:pos instruction)]
    (assoc-in state
              [player (find-piece (player state) pos)]
              (calculation-destination-pos instruction))))

(defn create-game [board-str]
  (let [state {:board  board-str
               :size   8
               :yellow [48 49 56 57]
               :black  [0 1 8 9]
               :red    [6 7 14 15]
               :white  [54 55 62 63]
               :turn   0
               :current-turn :yellow}]
    (d/print-board state)
    state))

(defn take-turn
  ([state instructions] (take-turn state instructions (nth ORDER_OF_PLAY (mod (:turn state) 4))))
  ([state instructions player]
   (let [turn-number (inc (:turn state))]
     (assoc
       (reduce #(apply-instruction %1 %2 player) state (mr/parse-instructions instructions))
       :turn         turn-number
       :current-turn (nth ORDER_OF_PLAY (mod turn-number 4))))))
