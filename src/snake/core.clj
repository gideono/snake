(ns snake.core
  (:import [java.awt.event KeyEvent])
  (:use [quil.core :as q]))

; add two vectors
; (v+ [1 2] [1 1]) => [2 3]
(def v+ (partial mapv +))

; given a percent chance, decide if a event happens
; (askoracle 0.5) => true
; (askoracle 0.5) => false
(defn askoracle [n]
  (< (rand) n))

;update
(defn update []
  )

;draw
(defn draw []
  ; set background color to dark grey, draw color to with
  (q/background-float 0x20)
  )

;input
(defn key-pressed []
  )

;run
(q/defsketch snake
             :title "Beach Boys Sweden 2d snake game"
             :size [450 200]
             :setup (fn []
                      (q/smooth)
                      (q/no-stroke)
                      (q/frame-rate 60))
             :draw (fn []
                     (update)
                     (draw))
             :key-pressed key-pressed)
