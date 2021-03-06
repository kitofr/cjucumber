(ns cjucumber.helpers)

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

(defn is-string[x]
  (let [klass (str (class x))]
      (or (= klass "java.lang.String")
          (= klass "System.String"))))

(defn regex-and-args [step hs]
  (flatten
    (filter #(not (= nil %1))
                  (map (fn [regex]
                         (let [matches (re-find regex step)]
                           (if matches 
                             (cond 
                               (is-string matches)
                                (list regex)
                              :else 
                                (list regex (drop 1 matches)))
                             nil))) 
                       (regexes hs)))))

