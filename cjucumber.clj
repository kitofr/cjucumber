(defonce givens (atom {}))

(defmacro given [regex args & body]
  `(let [call# '(~args ~@body)]
    (swap! givens assoc ~(keyword (str regex)) call#)))

(given #"hej" {:a :b} (println "hej"))
(given #"bu" {:a :b} (println "bu"))
(given #"bu" {:a :b} (println "bu"))
(println (str "Givens count: " (count (keys @givens))))

(defn run-step [step]
  (let [fun (get @givens (keyword step))]
    (if fun
      (eval (second (second fun)))
      (println (str "[pending] Given " step)))))

(run-step "bu")
(run-step "hej")
(run-step "kek")
