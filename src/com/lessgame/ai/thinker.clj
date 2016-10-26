(ns com.lessgame.ai.thinker
  (:require [com.lessgame.display.logger :as log]))

(defn play-turn []
  {:pos 2
   :move :right})

(defn process-turn [input]
  input)


(defn isValid [board move]
  true
  )

(defn play-move [state]
  ; FIXME hardcode move
  (log/debug "AI is thinking...")
  "h2h3:g1f1:h1g1"
  )
