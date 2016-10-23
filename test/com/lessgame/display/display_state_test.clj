(ns com.lessgame.display.display-state-test
  (:require [com.lessgame.display.display-state :as ds]
            [midje.sweet :refer :all]))

(fact "print empty board"
      (let [expected-board
            (str ".   .\n"
                 "     \n"
                 ".   .\n")]
        (ds/print-board {:board "0000" :size 2}) => expected-board))

(fact "print board with one vertical wall"
      (let [expected-board
            (str ". | .\n"
                 "     \n"
                 ".   .\n")]
        (ds/print-board {:board "1000" :size 2}) => expected-board))

(fact "print board with walls"
      (let [expected-board
            (str ".   .\n"
                 "    -\n"
                 ". | .\n")]
        (ds/print-board {:board "0011" :size 2}) => expected-board))

(fact "print board with double walls"
      (let [expected-board
            (str ".   .\n"
                 "-    \n"
                 ".   .\n")]
        (ds/print-board {:board "0100" :size 2}) => expected-board))

(fact "print empty board"
      (let [expected-board
            (str ".   .   .\n"
                 "         \n"
                 ".   .   .\n"
                 "         \n"
                 ".   .   .\n")
            board-str "000000000000"]
        (ds/print-board {:board board-str :size 3}) => expected-board))


(fact "print empty board"
      (let [expected-board
            (str ".   . | .   .   .   .   .   .\n"
                 "    -                       =\n"
                 ".   .   .   .   . | .   . | .\n"
                 "        -   =   -   -        \n"
                 ".   . | . | .   .   .   .   .\n"
                 "    -           -            \n"
                 ".   . | .   . | .   .   .   .\n"
                 "                            -\n"
                 ".   .   .   . | . | .   . | .\n"
                 "    -               -        \n"
                 ". | .   .   .   .   . | .   .\n"
                 "                             \n"
                 ".   .   .   .   . | .   .   .\n"
                 "            -       -       -\n"
                 ". | .   .   . | .   . | .   .\n")
            board-str "0100000010000020000101001211000110000010010000101000000000010001101010001001000010000000000000100000101011001010"]
        (ds/print-board {:board board-str :size 8}) => expected-board))

;
;(let [expected-board
;      (str "b   b | .   .   .   .   r   r\n"
;           "    -                       =\n"
;           "b   b   .   .   . | .   r | r\n"
;           "        -   =   -   -        \n"
;           ".   . | . | .   .   .   .   .\n"
;           "    -           -            \n"
;           ".   . | .   . | .   .   .   .\n"
;           "                            -\n"
;           ".   .   .   . | . | .   . | .\n"
;           "    -               -        \n"
;           ". | .   .   .   .   . | .   .\n"
;           "                             \n"
;           "y   y   .   .   . | .   w   w\n"
;           "            -       -       -\n"
;           "y | y   .   . | .   . | w   w\n")]
;  (fact "print state"
;        (ds/print {:board "0000" :yellow ""}) => expected-board
;        ))
