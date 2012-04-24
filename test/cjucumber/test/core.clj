(ns cjucumber.test.core
  (:use [cjucumber.core])
  (:use [clojure.test]))

(deftest step-creation
         (let [given (Given #"foo" () (:body))]
           (is (= (count @givens) 1))))
