(ns com.lessgame.ai.thinker
  (:require [com.lessgame.display.logger :as log]))

(defn play-move [state]
  ; FIXME hardcode move
  (log/debug "AI is thinking...")
  "h2h3:g1f1:h1g1"
  )

(defn create-thinker [ai-colour state]
  {:ai-colour ai-colour
   :ai-state state}
  )

(defn update [ai-colour new-state]
  {:ai-colour ai-colour
   :ai-state new-state}
  )
