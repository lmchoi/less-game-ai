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

(defn- draw-row [walls num-of-vertical-walls]
  (loop [i 0
         r "."]
    (if (< i num-of-vertical-walls)
      (recur (inc i) (str r " " (VERTICAL_SEPARATOR (nth walls i)) " ."))
      (str r "\n"))))

(defn print-board [{:keys [board size] :or {size 8} }]
  (let [demo-board
        (str "b   b | .   .   .   .   r   r\n"
             "    -                       =\n"
             "b   b   .   .   . | .   r | r\n"
             "        -   =   -   -        \n"
             ".   . | . | .   .   .   .   .\n"
             "    -           -            \n"
             ".   . | .   . | .   .   .   .\n"
             "                            -\n"
             ".   .   .   . | . | .   . | .\n"
             "    -               -        \n"
             ". | .   .   .   .   . | .   .\n"
             "                             \n"
             "y   y   .   .   . | .   w   w\n"
             "            -       -       -\n"
             "y | y   .   . | .   . | w   w\n"
             )
        board-seq (seq board)
        num-of-vertical-walls (- size 1)]

    (str
        (draw-row (take num-of-vertical-walls board-seq) num-of-vertical-walls)
        (draw-horizontal-separator (take size (drop num-of-vertical-walls board-seq)) size)
        (draw-row (take num-of-vertical-walls (drop (+ num-of-vertical-walls size) board-seq)) num-of-vertical-walls))))
