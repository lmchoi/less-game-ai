(ns com.lessgame.game-engine
  (:require [com.lessgame.reader.move-reader :as mr]
            [com.lessgame.display.display-state :as d]))

(def ORDER_OF_PLAY [:yellow :black :white :red])
(def NUMBER_OF_PLAYERS (count ORDER_OF_PLAY))

(def BOARD_WIDTH 8)

(def TL_CORNER [0 1 8 9])
(def TR_CORNER [7 6 15 14])
(def BL_CORNER [56 57 49 48])
(def BR_CORNER [63 62 55 54])

(def END_GAME {:yellow  TR_CORNER
               :black   BR_CORNER
               :red     BL_CORNER
               :white   TL_CORNER})

(defn- find-piece [pieces-for-player pos]
  (.indexOf pieces-for-player pos))

(defn- calculation-destination-pos [{:keys [move pos value]}]
  (cond
    (= move :down)  (+ pos (* value BOARD_WIDTH))
    (= move :up)    (- pos (* value BOARD_WIDTH))
    (= move :right) (+ pos value)
    (= move :left)  (- pos value)))

(defn- apply-instruction [state instruction player]
  (let [pos (:pos instruction)]
    (assoc-in state
              [player (find-piece (player state) pos)]
              (calculation-destination-pos instruction))))

(defn create-game [board-str]
  (let [state {:board  board-str
               :size   8
               :yellow BL_CORNER
               :black  TL_CORNER
               :red    TR_CORNER
               :white  BR_CORNER
               :turn   0
               :current-turn :yellow
               :end-game  END_GAME}]
    (d/print-board state)
    state))

(defn take-turn
  ([state instructions] (take-turn state instructions (nth ORDER_OF_PLAY (mod (:turn state) NUMBER_OF_PLAYERS))))
  ([state instructions player]
   (let [turn-number (inc (:turn state))]
     (assoc
       (reduce #(apply-instruction %1 %2 player) state (mr/parse-instructions instructions))
       :turn         turn-number
       :current-turn (nth ORDER_OF_PLAY (mod turn-number NUMBER_OF_PLAYERS))))))
