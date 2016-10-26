(ns com.lessgame.io.caia-less-io
  (:require [com.lessgame.io.input-reader :as in]
            [com.lessgame.game-engine :as game]
            [com.lessgame.display.display-state :as d]
            [com.lessgame.ai.thinker :as ai]
            [com.lessgame.display.logger :as log]
            [com.lessgame.reader.player-reader :as pr]))

(defn- get-next-move [state ai-colour]
  (if (= (:current-turn state) ai-colour)
    (ai/play-move state)
    (in/prompt-move)))

(defn start []
  (let [board-str "0100000010000020000101001211000110000010010000101000000000010001101010001001000010000000000000100000101011001010"
        ai-colour (pr/parse-player (in/prompt-ai))
        state     (game/create-game board-str)
        command   (get-next-move state ai-colour)]

    (loop [updated-state  state
           move           command]
      (if-not (nil? move)
        (let [new-state (game/take-turn updated-state move)]
          (d/print-board new-state)
          (recur new-state (get-next-move new-state ai-colour)))))))
