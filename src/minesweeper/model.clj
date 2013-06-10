(ns minesweeper.model
  (:require [noir.session :as session])
  (:require [clojure.set :as sets]))

(defn print-board [b f]
     (doseq [row b]
       (println (map (fn [x] (if (f x) 1 0)) row))))

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

(defn get-cell-value-by-key [board r c key]
  (get (get-board-cell board r c) key))

(defn is-cleared? 
  ([cell] (get cell :cleared))
  ([board c r] (get-cell-value-by-key board r c :cleared)))

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

(defn is-out-of-board [board row col]
  ;at the end of the board the count returns one digit to high so >= is used to counter
  (if (or 
        (or (< row 0) (< col 0)) 
        (or (>= row (count board)) (>= col (count (get-in board [0])))))
    true 
    false))

(defn clear-cell-set-bombs [board row col]
  (if (not (is-out-of-board board row col))
  (update-value-in-board 
    (update-value-in-board board row col :cleared true)
      row col :bomb-count 
       (get-bombs-around board  row col)) board))

(defn check-cleared-add-list [board x y collec]
  (if (and (not(is-cleared? board x y))
           (not (is-out-of-board board y x))) 
    (conj collec [x y]) collec))

(defn list-add-surrounding-coords-not-cleared [board collec x y]
  (check-cleared-add-list board (- x 1) (- y 1)
      (check-cleared-add-list board (- x 1) y
        (check-cleared-add-list board (- x 1) (+ y 1)
          (check-cleared-add-list board x (- y 1)
            (check-cleared-add-list board x (+ y 1)
              (check-cleared-add-list board (+ x 1) (- y 1)
                (check-cleared-add-list board (+ x 1) y
                  (check-cleared-add-list board (+ x 1) (+ y 1) collec)))))))))

(defn remove-first-element [collec]
  (pop (into [] (reverse collec))))
  
(defn clear-surroundings2 [board collec]
;for every item in list => [[x y] [x y] .....]  || Alternative only on first item
;let [new-board set board cleared on x y coords & calc bomb-count])
;if bomb-count = 0 
;  list.add !cleared [x-1 y-1] [x-1 y] [x-1 y+1] [x y-1] [x y+1] [x+1 y-1] [x+1 y] [x+1 y+1]
;(recur new-board list) ||alternative remove first item then give list back
  (if (<= (count collec) 0)
    board
    (let [x (first (first collec)) y (second (first collec))
          new-board (clear-cell-set-bombs board y x)
          new-collec 
          (if (= (get-cell-value-by-key new-board y x :bomb-count) 0)
            (remove-first-element (list-add-surrounding-coords-not-cleared new-board collec x y))
            (remove-first-element collec))]
      (recur new-board new-collec))))

(defn clear-cell [row col board]
  (let [new-board 
        (clear-cell-set-bombs board row col)]
    (if (= (get-cell-value-by-key new-board row col :bomb-count) 0)
      (clear-surroundings2 new-board [[col row]])
      new-board)))
  
(defn new-state [button row col old-state]
  (if (= (first button) \b) {:board (clear-cell row col (:board old-state))}
    (if (get-cell-value-by-key (:board old-state) row col :flagged)
      {:board (update-value-in-board (:board old-state) row col :flagged false)}
      {:board (update-value-in-board (:board old-state) row col :flagged true)})))

(defn play! [button row col]
  (session/swap! (fn [session-map]
                   (assoc session-map :game-state 
                          (new-state button row col (:game-state session-map))))))
