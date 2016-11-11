(ns com.lessgame.ai.thinker
  (:require [com.lessgame.display.logger :as log]))

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

(defn- right-of
  ([pos num]
   (+ pos num))
  ([pos]
   (right-of pos 1)))

(defn- bottom-of
  ([pos board-size num]
   (+ pos (* board-size num)))
  ([pos board-size]
   (bottom-of pos board-size 1)))

(defn- jump-right [pos]
  (assoc (move-right pos) :value 2))

(defn- jump-down [pos]
  (assoc (move-down pos) :value 2))

(defn- occupied? [pos all-pieces]
  (some #(= pos %) all-pieces))

(defn- jump-right? [pos all-pieces]
  (and (occupied? (right-of pos) all-pieces)
       (not (occupied? (right-of pos 2) all-pieces))))

(defn- jump-down? [pos all-piece board-size]
  (and (occupied? (bottom-of pos board-size) all-piece)
       (not (occupied? (bottom-of pos board-size 2) all-piece))))

(defn move-right? [pos all-pieces]
  (not (occupied? (right-of pos) all-pieces)))

(defn move-down? [pos all-pieces board-size]
  (not (occupied? (bottom-of pos board-size) all-pieces)))

(defn- consider-piece [{:keys [pos]} ai-state]
  (let [current-turn (:current-turn ai-state)
        end-game-0 ((current-turn (:end-game ai-state)) 0)
        board-size (:size ai-state)
        x-distance (x-distance-between pos end-game-0 board-size)
        y-distance (y-distance-between pos end-game-0 board-size)
        all-pieces (concat (:yellow ai-state) (:black ai-state))
        ]

    (cond
      (and (> x-distance 1)
           (jump-right? pos all-pieces))
        (jump-right pos)

      (and (> y-distance 1)
           (jump-down? pos all-pieces board-size))
        (jump-down pos)

      (and (> y-distance 0)
           (> y-distance x-distance)
           (move-down? pos all-pieces board-size))
        (move-down pos)

      (and (> x-distance 0)
           (move-right? pos all-pieces))
        (move-right pos)

      :default
        nil
      )))

(defn- pick-furthest-piece [pieces]
  (nth (sort-by :distance > pieces) 0))

(defn- at-destination? [piece]
  (nil? (:distance piece)))

(defn- game-ended? [{:keys [pieces]}]
  (every? at-destination? pieces))

(defn- update-pieces [board-size position destination]
  (let [x-distance (x-distance-between position destination board-size)
        y-distance (y-distance-between position destination board-size)]
    {:pos position :distance (+ x-distance y-distance)}))

(defn think [ai state]
  (let [current-turn (:current-turn state)
        current-pos (current-turn state)
        end-game (current-turn (:end-game state))]
    (assoc ai
      :ai-state state
      :pieces   (map (partial update-pieces (:size state)) current-pos end-game))))

(defn create-thinker [ai-colour state]
  (think {:ai-colour ai-colour} state))

(defn- update-position [{:keys [move pos value]}
                        board-size
                        {:keys [distance] :as piece}]
  (if (= pos (:pos piece))
    (if (= move :down)
      (assoc piece
        :pos (+ pos (* value board-size))
        :distance (- distance value))
      (assoc piece
        :pos (+ pos value)
        :distance (- distance value)))
    piece))

(defn- move-pieces [pieces next-move board-size]
  (map (partial update-position next-move board-size) pieces))

(defn play-turn [{:keys [ai-state pieces] :as ai}]
  (let [board-size (:size ai-state)]

    (loop [moves-remaining  3
           ps                pieces
           instructions     []]

      (if (or (= moves-remaining 0)
              (game-ended? ai))
        instructions
        (let [piece-to-move (pick-furthest-piece ps)        ; FIXME-MC consider the value of the move before making the move
              next-move     (consider-piece piece-to-move
                                            ai-state)]
          (prn next-move)
          (if (nil? next-move)
            instructions ; FIXME-MC consider other pieces before returning???
            (recur (dec moves-remaining)
                   (move-pieces ps next-move board-size)
                   (conj instructions next-move))))))))

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