(ns cjucumber.core)

(defonce givens (atom {}))
(defonce whens (atom {}))
(defonce thens (atom {}))

(defn keyword->symbol [kw]
  (symbol (name kw)))

(defn regex->args [regex step]
  (rest (re-find regex step)))

(defn key->regex [k]
  (re-pattern (name k)))

(defn regex->key [r]
  (keyword (str r)))

(defn parse-args [args]
  (map keyword->symbol args))

(defmacro create-fn [arg & body]
  (let [args (parse-args arg)]
    `(fn [~@args] ~@body)))

(defmacro Given [regex args & body]
  `(swap! givens assoc (regex->key ~regex)
         (create-fn ~args ~@body)))

(defmacro When [regex args & body]
  `(swap! whens assoc (regex->key ~regex)
         (create-fn ~args ~@body)))

(defmacro Then [regex args & body]
  `(swap! thens assoc (regex->key ~regex)
         (create-fn ~args ~@body)))

(defn regexes [hs] (map key->regex (keys hs)))

(defn regex-and-args [step hs]
  (flatten
    (filter #(not (= nil %1))
                  (map (fn [regex]
                         (let [matches (re-find regex step)]
                           (if matches 
                             (cond 
                               (= (class matches) System.String)
                                (list regex)
                              :else 
                                (list regex (drop 1 matches)))
                             nil))) 
                       (regexes hs)))))

(defn execute-step [step hs]
  (let [fun-n-args (regex-and-args step hs)        
        funk (get hs (regex->key (first fun-n-args)))
        args (rest fun-n-args)]
    (eval (flatten `(~funk ~args)))))

(defn run-step [step]
  (let [identifier (re-find #"\w+" step)] 
    (cond (= identifier "Given")
          (execute-step step @givens)
          (= identifier "When")
          (execute-step step @whens)
          (= identifier "Then")
          (execute-step step @thens))))

