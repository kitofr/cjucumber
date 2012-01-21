(ns cjucumber)
;(defonce givens (atom {}))

;(defmacro given [regex args & body]
;  `(let [call# '(~args ~@body)]
;    (swap! givens assoc ~(keyword (str regex)) call#)))

;(given #"hej" {:a :b} (println "hej"))
;(given #"bu" {:a :b} (println "bu"))
;(given #"bu" {:a :b} (println "bu"))
;(println (str "Givens count: " (count (keys @givens))))

;(defn run-step [step]
;  (let [fun (get @givens (keyword step))]
;    (if fun
;      (eval (second (second fun)))
;      (println (str "[pending] Given " step)))))

;(run-step "bu")
;(run-step "hej")
;(run-step "kek")

(defn keyword->symbol [kw]
  (symbol (name kw)))

(defn parse-args [args]
  (map keyword->symbol args))

(defmacro create-fn [arg & body]
  (let [args (parse-args arg)]
    `(fn [~@args] ~@body)))

(macroexpand-1 '(create-fn (:a :b) (println a b)))

(defn regex->args [regex step]
  (rest (re-find regex step)))

(defonce fns (atom {}))

(defmacro given [regex args & body]
  `(swap! fns assoc (keyword (str ~regex))
         (create-fn ~args ~@body)))

(macroexpand-1 '(given #"arg1" (:a) (println a)))

(given #"arg" (:a) (println a))

(defn run-step [step]
  ((get @fns step) "Hej du glade!"))

