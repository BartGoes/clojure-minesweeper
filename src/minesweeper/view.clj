(ns minesweeper.view
  (:use hiccup.form
        [hiccup.def :only [defhtml]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [html5 include-css include-js]])
  (:require [minesweeper.model :as model]))

(defhtml layout [& content]
  (html5
   [:head
    [:title "Welcome to minesweeper-luminus"]
    (include-css "/css/minesweeper.css")(include-js "/js/jquery-2.0.1.min.js") 
    (include-js "/js/script.js")]
   [:body {:onload "init()"}[:div#wrapper content]]))

(defn get-value [cell]
  (if (or (get cell :cleared) (get cell :flagged))
    (cond 
      (get cell :flagged) "F"
      (get cell :bomb) "B"
      :else (get cell :bomb-count))
    " "))

(defn make-button-name [rownum colnum]
  (str "b"
    (if (< (count (str rownum)) 2) (str 0 rownum) rownum)
    (if (< (count (str colnum)) 2) (str 0 colnum) colnum)))

(defn cell-html [rownum colnum cell with-submit?] 
  [:td 
   [:input {:name (make-button-name rownum colnum)
            :class "inputtable"
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
   [:p {:class "winner"}(str "You have won in " (model/stopwatch-stop!))]
   (board-html (model/get-board) false)
   (link-to "/" "Reset")]))

(defn loser-screen []
  (layout
    [:div {:id "youlost"}
     [:p {:class "loser"} "You have lost"]
     [:img {:src "/img/explosion.gif"}]
     (board-html (model/get-board) false)
     (link-to "/" "Reset")]))
  
