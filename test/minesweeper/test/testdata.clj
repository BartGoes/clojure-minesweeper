(ns minesweeper.test.testdata
  (:use clojure.set))

;;create board test
(def board-with-no-bombs
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}
    ] 
   [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}]])

(def board-with-all-bombs
  [[{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false}
    ] 
   [{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false}]])

;;get cell test
(def cell1
  {:testCell 1 :secondValue false})
(def cell2
  {:testCell 2 :secondValue true})

(def test-cell-board
  [[{:testCell 1 :secondValue false} {}]
   [{:testCell 2 :secondValue true} {}]])

;;winner & loser tests
(def board-2by2-nobombs-allclear
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true} ]])

(def board-2by2-allbombs-noclear
  [[{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false}] 
    [{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} ]])

(def board-2by2-2bombs-2clear
  [[{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true} ]])

(def board-2by2-nobombs-noclear
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} ]])

(def board-2by2-allbombs-allclear
  [[{:bomb true, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared true}] 
    [{:bomb true, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared true} ]])

(def board-2by2-1bomb-1bombclear
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared true} ]])

(def board-2by2-1bomb-1nonbombclear
  [[{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true} ]])

;;new-state tests
(def board-2by2-clear-0by0
  {:board 
  [[{:bomb false, :bomb-count 1, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 1, :flagged false, :cleared false}] 
    [{:bomb false, :bomb-count 1, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 1, :flagged false, :cleared false} ]]})

(def board-2by2-cleared-0by0
  {:board 
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true} ]]})

(def board-2by2-clear-0by1
  {:board
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true} ]]})

(def board-2by2-cleared-0by1
  {:board 
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true} ]]})

(def board-2by2-clear-1by0-already-cleared
  {:board 
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true}] 
    [{:bomb false, :bomb-count 0, :flagged false, :cleared true} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared true} ]]})

;;bombs around test
;;(0 2 B 3 1)
;;(1 4 B 4 B)
;;(1 B B 3 1)
;;(2 3 3 2 1)
;;(B 1 1 B 1)
(def board-random-5by5
  [[{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}
    ] 
   [{:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}
   ] 
   [{:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb true, :bomb-count 0, :flagged false, :cleared false} 
    {:bomb false, :bomb-count 0, :flagged false, :cleared false}]])
  