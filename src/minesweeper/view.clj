(ns minesweeper.view
  (:use hiccup.form
        [hiccup.def :only [defhtml]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [html5 include-css]])
  (:require [minesweeper.model :as model]))

(defhtml layout [& content]
  (html5
   [:head
    [:title "Welcome to minesweeper-luminus"]
    (include-css "/css/minesweeper.css")]
   [:body [:div#wrapper content]]))

(defn get-value [cell]
  (if (get cell :cleared)
    (cond 
     (get cell :bomb) "B"
     (get cell :flagged) "F"
     :else (get cell :bomb-count))
    " "))

(defn make-button-name [rownum colnum]
  (str "b"
    (if (< (count (str rownum)) 2) (str 0 rownum) rownum)
    (if (< (count (str colnum)) 2) (str 0 colnum) colnum)))

(defn cell-html [rownum colnum cell with-submit?] 
  [:td 
   [:input {:name (make-button-name rownum colnum) 
            :value (str (get-value cell))
            :type (if with-submit? 
                    "submit" 
                    "button")}]])
  
(defn row-html [rownum row with-submit?]
  [:tr (map-indexed (fn [colnum cell]
                      (cell-html rownum colnum cell with-submit?))
                    row)])
  
(defn board-html [board with-submit?]
  (form-to [:post "/"]
           [:table 
            (map-indexed (fn [rownum row]
                           (row-html rownum row with-submit?)) 
                         board)]))

(defn play-screen []
  (layout
    [:div 
     (board-html (model/get-board) true)]))

(defn winner-screen []
  (layout
    [:div 
   [:p (str "You have won in " (model/stopwatch-stop!))]
   (board-html (model/get-board) false)
   (link-to "/" "Reset")]))

(defn loser-screen []
  (layout
    [:div
     [:p "You have lost"]
     (board-html (model/get-board) false)
     (link-to "/" "Reset")]))
  
