(ns com.lessgame.ai.thinker
  (:require [com.lessgame.display.logger :as log]
            [com.lessgame.display.display-state :as p]
            [com.lessgame.reader.move-reader :as mr]))

(defn- x-distance-between [start-x end-x board-size]
  (mod (- end-x start-x) board-size))

(defn- y-distance-between [start-y end-y board-size]
  (int (/ (- end-y start-y) board-size)))

(defn- move-right [pos]
  {:pos   pos
   :move  :right
   :value 1
   :cost  1})

(defn- move-down [pos]
  {:pos   pos
   :move  :down
   :value 1
   :cost  1})

(defn- find-next-move [pos end-game-0 board-size]
  (let [x-distance (x-distance-between pos end-game-0 board-size)
        y-distance (y-distance-between pos end-game-0 board-size)]

    ;(log/debug (str "distance from end-game " x-distance " " y-distance))

    (cond
      (= end-game-0 pos) nil
      (> y-distance x-distance) (move-down pos)
      :default (move-right pos))))

(defn- game-ended? [pos end-game]
  (= pos end-game))

(defn- update-position [pos {:keys [move value]} board-size]
  (cond
    (= move :right) (inc pos)
    (= move :down) (+ pos board-size))
  )

(defn- consider-piece [piece-position end-game board-size]
  (let [end-game-0 (end-game 0)]

    (loop [moves-remaining  3
           pos              piece-position
           instructions     []]

      (if (or (= moves-remaining 0)
              (game-ended? pos end-game-0))
        instructions
        (let [next-move (find-next-move pos end-game-0 board-size)]
          (when-not (nil? next-move)
            (recur (dec moves-remaining)
                   (update-position pos next-move board-size)
                   (conj instructions next-move))))))
    ))

(defn pick-furthest-piece [current-pos end-game board-size]
  (current-pos 0))

(defn- update-pieces [board-size position destination]
  (let [x-distance (x-distance-between position destination board-size)
        y-distance (y-distance-between position destination board-size)]
    {:pos position :distance (+ x-distance y-distance)}))

(defn create-thinker [ai-colour state]
  (let [current-turn (:current-turn state)
        current-pos (current-turn state)
        end-game (current-turn (:end-game state))]
    (assoc {:ai-colour ai-colour :ai-state  state} :pieces (map (partial update-pieces (:size state)) current-pos end-game))))

(defn think [ai new-state]
  (let [current-turn (:current-turn new-state)
        current-pos (current-turn new-state)
        end-game (current-turn (:end-game new-state))]
    (assoc (assoc ai :pieces (map (partial update-pieces (:size new-state)) current-pos end-game)) :ai-state new-state)))

(defn play-turn [{:keys [ai-state]}]
  (let [current-turn (:current-turn ai-state)
        current-pos (current-turn ai-state)
        end-game (current-turn (:end-game ai-state))]

    (consider-piece (pick-furthest-piece current-pos end-game (:size ai-state)) end-game (:size ai-state))))

;-----
;"h1h3:g1g3:h2f2"
;"h3f3:f2f4:g2g4"
;"g3g5:f3f5:g4e4"
;"g5e5:f4d4:f5d5"

;"a1c1:b1d1:a2a1"
;"b2b1:c1e1:a1c1"
;"d1f1:b1d1:e1g1"
;"c1e1:f1f2"


;"h7f7:g8g6:h8h7"
;"g7e7:g6g7:f7d7"
;"h7f7:e7c7:c7a7" (r at b7)
;"g7e7:e7c7:f7e7"

;"a8a6:b7b6:a7a5"
;"b8b7:a6a4"
;"b6b5:b7b6:a5a3"
;"b5b4:b6b5"