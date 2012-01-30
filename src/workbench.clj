(ns cjucumber.workbench
  (:use [cjucumber]))

(given #"arg (\w+)" (:a) (println a))
(given #"args (\d+) (\d+)" (:a :b) (println (str "args: [" a "|" b "]")))

(run-step "Given args 5 102")
(run-step "Given arg 50")
