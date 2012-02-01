(ns cjucumber.workbench
  (:use [cjucumber.core]))

(Given #"arg (\w+)" (:a) 
       (assert (< 5 a)))

(Given #"args (\d+) (\d+)" (:a :b) (println (str "args: [" a "|" b "]")))

;should not clash with given above!
(When #"arg (\d+)" (:x)
      (println x))

(run-step "Given arg 50")
(run-step "Given args 5 102")
(run-step "When arg 1234")
