(ns com.lessgame.reader.move-reader)

(def BOARD_WIDTH 8)
(def ASCII_a 97)
(def ASCII_1 49)

(defn- parse-x [code]
  (- code ASCII_1))

(defn- parse-y [code]
  (- code ASCII_a))

(defn- to-ascii [value]
  (int (char value)))

(defn- calculate-pos [x y]
  (+ x (* y BOARD_WIDTH)))

(defn- calculate-direction [x-dir y-dir]
  (cond
    (pos? x-dir) :right
    (neg? x-dir) :left
    (pos? y-dir) :down
    (neg? y-dir) :up))

(defn- create-move [sx sy ex ey]
  (let [x-dir (- ex sx)
        y-dir (- ey sy)
        dir (calculate-direction x-dir y-dir)
        pos (calculate-pos sx sy)]
    {:move  dir
     :pos   pos
     :value (if (zero? x-dir) (Math/abs y-dir) (Math/abs x-dir))}))

(defn- parse-move [move]
  (let [[start-y start-x end-y end-x] (map to-ascii move)
        sx (parse-x start-x)
        sy (parse-y start-y)
        ex (parse-x end-x)
        ey (parse-y end-y)]
    (create-move sx sy ex ey)))

(defn parse-instructions [instruction]
  (map parse-move (re-seq #"\D\d\D\d" instruction)))

;TODO-MC tidy translate instruction
(defn translate-move [{:keys [pos move value]}]
  (let [x (mod pos BOARD_WIDTH)
        y (quot pos BOARD_WIDTH)
        start-x (+ x 1)
        start-y (+ y ASCII_a)
        end-x start-x
        end-y start-y]
    (cond
      (= move :right) (str (char start-y)     start-x
                           (char end-y)       (+ start-x value))
      (= move :left)  (str (char start-y)     start-x
                           (char end-y)       (- start-x value))
      (= move :up)    (str (char start-y)     start-x
                           (char (- end-y value)) end-x)
      (= move :down)  (str (char start-y)     start-x
                           (char (+ end-y value)) end-x))))

(defn translate-instruction [moves]
  (reduce #(str
            (when-not (nil? %)
              (str % ":"))
            (translate-move %2))
          nil
          moves))
