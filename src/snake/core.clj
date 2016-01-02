(ns snake.core
  (:import [java.awt.event KeyEvent])
  (:use [quil.core :as q]))
;*****************************************************
;**************** Helper functions *******************
;*****************************************************

; add two vectors
; (v+ [1 2] [1 1]) => [2 3]
(def v+ (partial mapv +))

; given a percent chance, decide if a event happens
; (askoracle 0.5) => true
; (askoracle 0.5) => false
(defn askoracle [n]
  (< (rand) n))

;*****************************************************
;****************** Game functions *******************
;*****************************************************

; food (as set of points)
(def food (atom #{}))

; snake (as vector of points because we want to append)
; => obviously needs a start position
(def snake (atom [[50 50]]))

; snake movement direction
(def snake-dir (atom [1 0]))

; let the snake eat food
(add-watch snake :key
           (fn [k r os ns]
             ; any meal under snake head?
             (let [meal (get @food (first ns))]
               (when meal
                 (swap! food disj meal)
                 (swap! snake #(vec (cons meal %))))))) ; add to snake
;*****************************************************
;****************** Core functions *******************
;*****************************************************

;update
(defn update []
  ;update snake
  (let [new (v+ (first @snake) @snake-dir)] ; new head
    (swap! snake #(vec (cons new (pop %))))) ; head+rest
  ;spwan food
  (when (askoracle 0.005)
    (swap! food conj [(rand-int 450) (rand-int 200)]))
  )

;draw
(defn draw []
  ; set background color to dark grey, draw color to with
  (q/background-float 0x20)
  ;draw snake
  (q/stroke 0xff)
  (doseq [[x y] @snake] (q/point x y))
  ;draw food
  (q/stroke 0x00 0xff 0xff)
  ; we draw each meal by running through @food with doseq,
  ; where we use q/point to draw each point.
  (doseq [[x y] @food] (q/point x y)))

; input
(defn key-pressed []
  (cond ; case doesn't work with the KeyEvents
    (= (q/key-code) KeyEvent/VK_UP)
    (reset! snake-dir [0 -1])
    (= (q/key-code) KeyEvent/VK_DOWN)
    (reset! snake-dir [0 1])
    (= (q/key-code) KeyEvent/VK_LEFT)
    (reset! snake-dir [-1 0])
    (= (q/key-code) KeyEvent/VK_RIGHT)
    (reset! snake-dir [1 0])))

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