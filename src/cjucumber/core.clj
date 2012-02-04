(ns cjucumber.core)

; --- Helpers ---
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

; --- Macros ---
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

