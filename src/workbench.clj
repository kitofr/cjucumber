(ns workbench
  (:use cjucumber.core 
        cjucumber.runner))

(Given #"a = (\d+) and b = (\d+)" (:x :y) 
       (do 
         (def a x)
         (def b y)))

(When #"I execute a \+ b" ()
  (def result (+ a b)))

(Then #"the result is = (\d+)" (:x)
      (assert (= (int x) result)))

(run-step "Given a = 1 and b = 2")
(run-step "When I execute a + b")
(run-step "Then the result is = 3")
