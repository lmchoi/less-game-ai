(ns com.lessgame.io.caia-less-io
  (:require [com.lessgame.io.input-reader :as in]
            [com.lessgame.game-engine :as game]
            [com.lessgame.display.display-state :as d]
            [com.lessgame.ai.thinker :as ai]
            [com.lessgame.display.logger :as log]
            [com.lessgame.reader.player-reader :as pr]
            [com.lessgame.reader.move-reader :as mr]))

(defn- get-next-move [state {:keys [ai-colour] :as ai-state}]
  (if (= (:current-turn state) ai-colour)
    (mr/translate-instruction (ai/play-turn ai-state))
    (in/prompt-move))
  )

(defn start []
  (let [board-str "0100000010000020000101001211000110000010010000101000000000010001101010001001000010000000000000100000101011001010"
        state     (game/create-game board-str)
        ai-colour (pr/parse-player (in/prompt-ai))
        ai        (ai/create-thinker ai-colour state)
        command   (get-next-move state ai)]

    (loop [updated-state  state
           move           command]
      (if-not (nil? move)
        (let [new-state     (game/take-turn updated-state move)
              new-ai-state  (ai/think ai new-state)]
          (d/print-board new-state)
          (recur new-state (get-next-move new-state new-ai-state)))))))

