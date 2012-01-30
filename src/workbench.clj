(ns cjucumber.workbench
  (:use [cjucumber.core]))

(given #"arg (\w+)" (:a) 
       (assert (< 5 a)))
(run-step "Given arg 50")

(given #"args (\d+) (\d+)" (:a :b) (println (str "args: [" a "|" b "]")))
(run-step "Given args 5 102")

