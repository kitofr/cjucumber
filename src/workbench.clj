(ns cjucumber.workbench
  (:use [cjucumber.core]))

(Given #"a = (\d+) and b = (\d+)" (:a1 :b1) 
       (do 
         (def a a1)
         (def b b1)))

(When #"I execute a \+ b" ()
  (def result (+ a b)))

(Then #"the result is = (\d+)" (:x)
      (assert (= (int x) result)))

(run-step "Given a = 1 and b = 2")
(run-step "When I execute a + b")
(run-step "Then the result is = 3")
