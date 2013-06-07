(ns minesweeper.model
  (:require [noir.session :as session]))

;stopwatch functions
(def stopwatch (atom {:time nil}))

(defn stopwatch-start! []
  (swap! stopwatch assoc :time (. java.lang.System (nanoTime))))

(defn stopwatch-stop! []
  (if (= (:time @stopwatch) nil) "Start stopwatch first"
    (let [elapsed (format "%.3f" (/ (- (. System (nanoTime)) (:time @stopwatch)) 1000000000.00))]
      (do
	      (swap! stopwatch assoc :time nil)
	      (str elapsed " seconds")))))

;game functions
(defn create-cell [fillratio]
  {:bomb (< (rand) fillratio) 
   :bomb-count 0 
   :flagged false 
   :cleared false})

(defn create-board [h w fillratio]
  (vec
    (for [i (range h)]
      (vec
        (for [j (range w)]
          (create-cell fillratio))))))
       
(defn reset-game! []
  (stopwatch-start!)
  (session/put! :game-state {:board (create-board 20 30 0.2)}))

(defn get-board []
  (:board (session/get :game-state)))

(defn get-board-cell 
  ([row col]
    (get-board-cell (get-board) row col))
  ([board row col]
    (get-in board [row col])))

(defn is-cleared? [cell]
  (get cell :cleared))

(defn is-bomb? [cell]
  (get cell :bomb))

(defn winner?
  ([] (winner? (get-board)))
  ([board] 
  (let [flatboard (flatten board)]
    (every? #(or (and (is-cleared? %) (not (is-bomb? %)))
                 (and (not (is-cleared? %)) (is-bomb? %)))
           flatboard))))

(defn any? [pred collection] (not (not-any? pred collection)))

(defn loser? 
  ([] (loser? (get-board)))
  ([board]
	  (let [flatboard (flatten board)]
	    (any? #(and (is-cleared? %) (is-bomb? %))
	           flatboard))))
 
(defn update-cell-in-board [board r c update-value]
    (assoc-in board [r c] update-value))

(defn update-value-in-board [board r c key new-value]
  (update-cell-in-board board r c (assoc-in (get-board-cell board r c) [key] new-value)))

(defn get-cell-value-by-key [board r c key]
  (get (get-board-cell board r c) key))

(defn has-bomb? [board r c]
  (get-cell-value-by-key board r c :bomb))

(defn get-bombs-around [board r c]
     (count
       (filter #(= true %)
         [
           (has-bomb? board r (dec c))
           (has-bomb? board r (inc c))
           (has-bomb? board (dec r) c)
           (has-bomb? board (inc r) c)
           (has-bomb? board (inc r) (inc c))
           (has-bomb? board (dec r) (inc c))
           (has-bomb? board (inc r) (dec c))
           (has-bomb? board (dec r) (dec c))
         ])))

(defn clear-surroundings [board row col]
;This functions should update all the surrounding cells when the bomb-count of a cell = 0
;  (let [transient-board (transient board)]
;      (doseq (for [y (range -1 2)]
;        (for [x (range -1 2)]
;          (assert! transient-board (update-value-in-board 
;                              (update-value-in-board transient-board  (+ row y) (+ col x) :cleared true) 
;                              row col :bomb-count (get-bombs-around transient-board  (+ row y) (+ col x)))))))
;    (persistent! transient-board)))
board
)

(defn clear-cell [row col board]
  (let [new-board 
  (update-value-in-board 
    (update-value-in-board board row col :cleared true) 
      row col :bomb-count (get-bombs-around board row col))]
    (if (= (get-cell-value-by-key new-board row col :bomb-count) 0)
      (clear-surroundings new-board row col)
      new-board)))
  
(defn new-state [row col old-state]
  {:board (clear-cell row col (:board old-state))})
;  {:board (update-value-in-board 
;            (update-value-in-board (:board old-state) row col :cleared true) 
;            row col :bomb-count (get-bombs-around (:board old-state) row col))})

(defn play! [row col]
  (session/swap! (fn [session-map]
                   (assoc session-map :game-state 
                          (new-state row col (:game-state session-map))))))