(ns com.lessgame.reader.input-reader
  (:require [com.lessgame.display.logger :as log]))

(defn prompt-board []
  (log/debug "Please enter board string: ")
  (read-line))

(defn prompt-move []
  (log/debug "Please enter move: ")
  (let [command (read-line)]
    (when-not (= "Quit" command)
      command)))

