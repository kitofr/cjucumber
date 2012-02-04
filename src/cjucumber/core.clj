(ns cjucumber.core
  (:use cjucumber.helpers))

(defmacro create-fn [arg & body]
  (let [args (parse-args arg)]
    `(fn [~@args] ~@body)))

(defonce givens (atom {}))
(defmacro Given [regex args & body]
  `(swap! givens assoc (regex->key ~regex)
         (create-fn ~args ~@body)))

(defonce whens (atom {}))
(defmacro When [regex args & body]
  `(swap! whens assoc (regex->key ~regex)
         (create-fn ~args ~@body)))

(defonce thens (atom {}))
(defmacro Then [regex args & body]
  `(swap! thens assoc (regex->key ~regex)
         (create-fn ~args ~@body)))

