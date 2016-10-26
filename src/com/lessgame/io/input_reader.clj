(ns com.lessgame.io.input-reader
  (:require [com.lessgame.display.logger :as log]))

(defn prompt-board []
  (log/debug "Please enter board string: ")
  (read-line))

(defn prompt-ai []
  (log/debug "Please enter colour for AI: ")
  (read-line))

(defn prompt-move []
  (log/debug "Please enter move: ")
  (let [command (read-line)]
    (when-not (= "Quit" command)
      command)))

