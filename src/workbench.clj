(ns cjucumber.workbench
  (:use [cjucumber.core]))

(defonce a (ref nil))
(defonce b (ref nil))
(defonce result (ref nil))

(Given #"a = (\d+) and b = (\d+)" (:a1 :b1) 
       (do 
         (dosync (ref-set a (int a1)))
         (dosync (ref-set b (int b1)))))

(When #"I execute a \+ b" ()
  (dosync (ref-set result (+ @a @b))))

(Then #"the result is = (\d+)" (:x)
      (assert (= (int x) @result)))

(run-step "Given a = 1 and b = 2")
(run-step "When I execute a + b")
(run-step "Then the result is = 3")
