(ns com.lessgame.display.display-state
  (:require [com.lessgame.display.logger :as log]))

(def HORIZONTAL_SEPARATOR {\0 " "
                           \1 "-"
                           \2 "="})

(def VERTICAL_SEPARATOR {\0 " "
                         \1 "|"
                         \2 "#"})

(defn- draw-horizontal-separator [num-of-columns walls]
  (loop [i 1
         r (HORIZONTAL_SEPARATOR (nth walls 0))]
    (if (< i num-of-columns)
      (recur (inc i) (str r "   " (HORIZONTAL_SEPARATOR (nth walls i))))
      (str r "\n"))))

(defn- get-piece [{:keys [yellow red black white]} index]
  (let [i #{index}]
    (cond
      (some i black)  "b"
      (some i yellow) "y"
      (some i red)    "r"
      (some i white)  "w"
      :default ".")))

(defn- draw-row [state index num-of-vertical-walls walls]
  (loop [i    0
         pos  index
         r    (get-piece state pos)]
    (if (< i num-of-vertical-walls)
      (let [next-i (inc i)
            next-pos (inc pos)]
        (recur next-i
               next-pos
               (str r " " (VERTICAL_SEPARATOR (nth walls i)) " " (get-piece state next-pos))))
      (str r "\n"))))

(defn- generate-board-string [{:keys [board size] :or {size 8} :as state}]
  (let [board-seq             (seq board)
        num-of-vertical-walls (- size 1)]
    (loop [is-separator?    false
           board-string     ""
           remaining-cells  board-seq
           index            0]
      (if (empty? remaining-cells)
        board-string
        (let [draw-fn (if is-separator?
                        draw-horizontal-separator
                        (partial draw-row state index))
              length  (if is-separator? size num-of-vertical-walls)
              updated-index (if is-separator? index (+ index size))]
          (recur (not is-separator?)
                 (str board-string
                      (draw-fn length (take length remaining-cells)))
                 (drop length remaining-cells)
                 updated-index))))))

(defn print-board [state]
  (log/debug "Displaying board: ")
  (let [board-string (generate-board-string state)]
    (log/debug board-string)
    board-string))
