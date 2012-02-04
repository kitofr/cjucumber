(ns cjucumber.runner
  (:use cjucumber.helpers
        cjucumber.core))

(defn execute-step [step hs]
  (let [fun-n-args (regex-and-args step hs)        
        funk (get hs (regex->key (first fun-n-args)))
        args (rest fun-n-args)]
    (println (str "[ " step " ]")) 
    (eval (flatten `(~funk ~args)))))

(defn run-step [step]
  (let [identifier (re-find #"\w+" step)] 
    (cond (= identifier "Given")
          (execute-step step @givens)
          (= identifier "When")
          (execute-step step @whens)
          (= identifier "Then")
          (execute-step step @thens))))
