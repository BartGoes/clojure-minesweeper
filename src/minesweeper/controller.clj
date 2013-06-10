(ns minesweeper.controller
  (:use compojure.core)
  (:require [compojure.core :as compojure]
            [minesweeper.view :as view]
            [minesweeper.model :as model]))

(defn start-page []
  (model/reset-game!)
  (view/play-screen))

(defn turn-page [button-pressed]
  (let [button-id (name (first (keys button-pressed)))
        rownr (Integer/parseInt (str (nth button-id 1) (nth button-id 2)))
        colnr (Integer/parseInt (str (nth button-id 3) (nth button-id 4)))]
    (model/play! button-id rownr colnr)
    (cond
      (model/loser?) (view/loser-screen)
      (model/winner?) (view/winner-screen)
      :else (view/play-screen))))

(defroutes minesweeper-routes
  (GET "/" [] (start-page))
  (POST "/" {button-pressed :params} (turn-page button-pressed)))
