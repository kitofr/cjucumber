(ns cjucumber.core)

(defonce givens (atom {}))
(defonce whens (atom {}))

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

(defn regexes [hs] (map key->regex (keys hs)))

(defn regex-and-args [step hs]
  (flatten
    (filter #(not (= nil %1))
                  (map (fn [regex]
                         (let [matches (re-find regex step)]
                           (if matches
                             (list regex (drop 1 matches))
                             nil))) 
                       (regexes hs)))))

(defn run-step [step]
  (let [identifier (re-find #"\w+" step)] ; Given/When/Then
    (cond (= identifier "Given")
          (do
            (let [fun-n-args (regex-and-args step @givens)        
                 funk (get @givens (regex->key (first fun-n-args)))
                 args (rest fun-n-args)]
                (eval (flatten `(~funk ~args)))))
          (= identifier "When")
          (do
            (let [fun-n-args (regex-and-args step @whens)        
                 funk (get @whens (regex->key (first fun-n-args)))
                 args (rest fun-n-args)]
                (eval (flatten `(~funk ~args))))))))

