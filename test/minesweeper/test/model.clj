(ns minesweeper.test.model
  (:use minesweeper.model)
  (:use clojure.test)
  (:require [minesweeper.test.testdata :as td]))

(deftest create-board-no-bombs
  (testing "Create a board with a 0 and 1 fill ratio"
    (is (= td/board-with-no-bombs (create-board 5 5 0.0)))
    (is (= td/board-with-all-bombs (create-board 5 5 1.0)))))

(deftest get-board-cell-test
  (testing "Get cell from a board"
     (is (= td/cell1 (get-board-cell td/test-cell-board 0 0)))
     (is (= td/cell2 (get-board-cell td/test-cell-board 1 0)))))

(deftest check-winner
  (testing "Checks the winner function with all four options"
     (is (= (winner? td/board-2by2-nobombs-allclear) true))
     (is (= (winner? td/board-2by2-allbombs-noclear) true))
     (is (= (winner? td/board-2by2-2bombs-2clear) true))
     (is (= (winner? td/board-2by2-nobombs-noclear) false))
     (is (= (winner? td/board-2by2-allbombs-allclear) false))
     (is (= (winner? td/board-2by2-1bomb-1bombclear) false))
     (is (= (winner? td/board-2by2-1bomb-1nonbombclear) false))
     ))

(deftest check-loser
  (testing "Checks the winner function with all four options"
     (is (= (loser? td/board-2by2-nobombs-allclear) false))
     (is (= (loser? td/board-2by2-allbombs-noclear) false))
     (is (= (loser? td/board-2by2-2bombs-2clear) false))
     (is (= (loser? td/board-2by2-nobombs-noclear) false))
     (is (= (loser? td/board-2by2-allbombs-allclear) true))
     (is (= (loser? td/board-2by2-1bomb-1bombclear) true))
     (is (= (loser? td/board-2by2-1bomb-1nonbombclear) false))
     ))

(deftest board-5by5-bombs-around-test
  (testing "checks for a semi-random board if the get-bombs-around returns the correct value"
     (is (= (get-bombs-around td/board-random-5by5 0 0) 0))
     (is (= (get-bombs-around td/board-random-5by5 0 1) 2))
     (is (= (get-bombs-around td/board-random-5by5 0 2) 1)) ;;this is bomb
     (is (= (get-bombs-around td/board-random-5by5 0 3) 3))
     (is (= (get-bombs-around td/board-random-5by5 0 4) 1))
     (is (= (get-bombs-around td/board-random-5by5 1 0) 1))
     (is (= (get-bombs-around td/board-random-5by5 1 1) 4))
     (is (= (get-bombs-around td/board-random-5by5 1 2) 3)) ;;this is bomb
     (is (= (get-bombs-around td/board-random-5by5 1 3) 4))
     (is (= (get-bombs-around td/board-random-5by5 1 4) 0)) ;;this is bomb
     (is (= (get-bombs-around td/board-random-5by5 2 0) 1))
     (is (= (get-bombs-around td/board-random-5by5 2 1) 2)) ;;this is bomb
     (is (= (get-bombs-around td/board-random-5by5 2 2) 2)) ;;this is bomb
     (is (= (get-bombs-around td/board-random-5by5 2 3) 3))
     (is (= (get-bombs-around td/board-random-5by5 2 4) 1))
     (is (= (get-bombs-around td/board-random-5by5 3 0) 2))
     (is (= (get-bombs-around td/board-random-5by5 3 1) 3))
     (is (= (get-bombs-around td/board-random-5by5 3 2) 3))
     (is (= (get-bombs-around td/board-random-5by5 3 3) 2))
     (is (= (get-bombs-around td/board-random-5by5 3 4) 1))
     (is (= (get-bombs-around td/board-random-5by5 4 0) 0)) ;;this is bomb
     (is (= (get-bombs-around td/board-random-5by5 4 1) 1))
     (is (= (get-bombs-around td/board-random-5by5 4 2) 1))
     (is (= (get-bombs-around td/board-random-5by5 4 3) 0)) ;;this is bomb
     (is (= (get-bombs-around td/board-random-5by5 4 4) 1))
     ))

(deftest new-state-test
  (testing "Check if the state that is returned from new-state is as expected"
     (is (= (new-state 0 0 td/board-2by2-clear-0by0) td/board-2by2-cleared-0by0))
     (is (= (new-state 0 1 td/board-2by2-clear-0by1) td/board-2by2-cleared-0by1))
     (is (= (new-state 1 0 td/board-2by2-clear-1by0-already-cleared) 
            td/board-2by2-clear-1by0-already-cleared))))