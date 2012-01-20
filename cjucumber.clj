
(defonce givens (atom {}))

(defmacro given [regex args & body]
  `(let [call# '(~args ~@body)]
    (swap! givens assoc ~(keyword (str regex)) (call#))))

(macroexpand-1 '(given #"hej" [:a :b] (println "hej")))

(given #"hej" [:a :b] (println "hej"))
(println (count (keys @givens)))
