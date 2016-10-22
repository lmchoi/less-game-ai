(ns com.lessgame.reader.move-reader)

; for debugging
(defn- doNothing [_]
  _)

(def ASCII_a 97)
(def ASCII_1 49)

(defn- parse-x [code]
  (- code ASCII_1))

(defn- parse-y [code]
  (- code ASCII_a))

(defn- to-ascii [value]
  (int (char value)))

(defn to-coordintes [[start-y start-x
                      end-y end-x]]
  [(parse-x start-x) (parse-y start-y)
   (parse-x end-x) (parse-y end-y)])

(defn- parse-move [move]
  (to-coordintes (map to-ascii move)))

(defn parse-instructions [instruction]
  (map parse-move (re-seq #"\D\d\D\d" instruction)))

(defn translate [move]
  "b1b2"
  )
