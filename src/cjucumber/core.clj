(ns cjucumber.core)

(defonce fns (atom {}))
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

(defmacro given [regex args & body]
  `(swap! fns assoc (regex->key ~regex)
         (create-fn ~args ~@body)))

(defn regexes [] (map key->regex (keys @fns)))

(defn regex-and-args [step]
  (flatten
    (filter #(not (= nil %1))
                  (map (fn [regex]
                         (let [matches (re-find regex step)]
                           (if matches
                             (list regex (drop 1 matches))
                             nil))) 
                       (regexes)))))

(defn run-step [step]
  (let [fun-n-args (regex-and-args step)
        funk (get @fns (regex->key (first fun-n-args)))
        args (rest fun-n-args)]
    (eval (flatten `(~funk ~args)))))

