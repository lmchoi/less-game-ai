(ns com.lessgame.display.display-state)

(def HORIZONTAL_SEPARATOR {\0 " "
                           \1 "-"
                           \2 "="})

(def VERTICAL_SEPARATOR {\0 " "
                         \1 "|"
                         \2 "#"})

(defn- draw-horizontal-separator [walls num-of-columns]
  (loop [i 1
         r (HORIZONTAL_SEPARATOR (nth walls 0))]
    (if (< i num-of-columns)
      (recur (inc i) (str r "   " (HORIZONTAL_SEPARATOR (nth walls i))))
      (str r "\n"))))

; TODO-MC show pieces
(defn- draw-row [walls num-of-vertical-walls]
  (loop [i 0
         r "."]
    (if (< i num-of-vertical-walls)
      (recur (inc i) (str r " " (VERTICAL_SEPARATOR (nth walls i)) " ."))
      (str r "\n"))))

(defn draw [is-separator? board-in-progress length]
  (let [draw-fn (if is-separator?
                  draw-horizontal-separator
                  draw-row)]
    (draw-fn (take length board-in-progress) length)))

(defn print-board [{:keys [board size] :or {size 8} }]
  (let [board-seq (seq board)
        num-of-vertical-walls (- size 1)]
    (loop [is-separator?      false
           board-to-display   ""
           board-in-progress  board-seq]
      (if (empty? board-in-progress)
        board-to-display
        (let [length          (if is-separator? size num-of-vertical-walls)
              row-to-display  (draw is-separator? board-in-progress length)]
          (recur (not is-separator?)
                 (str board-to-display row-to-display)
                 (drop length board-in-progress)))))))
