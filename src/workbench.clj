(ns cjucumber.workbench
  (:use [cjucumber.core]))

(Given #"arg (\w+)" (:a) 
       (assert (< 5 a)))
(run-step "Given arg 50")

(Given #"args (\d+) (\d+)" (:a :b) (println (str "args: [" a "|" b "]")))
(run-step "Given args 5 102")

;should not clash with given above!
(When #"arg (\d+)" (:x)
      (println x))
