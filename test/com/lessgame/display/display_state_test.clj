(ns com.lessgame.display.display-state-test
  (:require [com.lessgame.display.display-state :as ds]
            [midje.sweet :refer :all]))

(fact "print empty 2x2 board"
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
            (str ". # .\n"
                 "=    \n"
                 ".   .\n")]
        (ds/print-board {:board "2200" :size 2}) => expected-board))

(fact "print empty 3x3 board"
      (let [expected-board
            (str ".   .   .\n"
                 "         \n"
                 ".   .   .\n"
                 "         \n"
                 ".   .   .\n")
            board-str "000000000000"]
        (ds/print-board {:board board-str :size 3}) => expected-board))

(fact "print empty 8x8 board with walls"
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

(fact "print 2x2 board with one black at the top-left corner"
      (let [expected-board
            (str "b   .\n"
                 "     \n"
                 ".   .\n")]
        (ds/print-board {:board "0000"
                         :size 2
                         :black  [0]}) => expected-board))

(fact "print 2x2 board with one yellow at the bottom-left corner"
      (let [expected-board
            (str ".   .\n"
                 "     \n"
                 "y   .\n")]
        (ds/print-board {:board "0000"
                         :size 2
                         :yellow [2]}) => expected-board))

(fact "print 2x2 board with one red at the top-right corner"
      (let [expected-board
            (str ".   r\n"
                 "     \n"
                 ".   .\n")]
        (ds/print-board {:board "0000"
                         :size 2
                         :red [1]}) => expected-board))

(fact "print 2x2 board with one white at the bottom-right corner"
      (let [expected-board
            (str ".   .\n"
                 "     \n"
                 ".   w\n")]
        (ds/print-board {:board "0000"
                         :size 2
                         :white [3]}) => expected-board))

(let [expected-board
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
           "y | y   .   . | .   . | w   w\n")]
  (fact "print state"
        (ds/print-board {:board "0100000010000020000101001211000110000010010000101000000000010001101010001001000010000000000000100000101011001010"
                         :size   8
                         :yellow [48 49 56 57]
                         :black  [0 1 8 9]
                         :red    [6 7 14 15]
                         :white  [54 55 62 63]}) => expected-board
        ))
