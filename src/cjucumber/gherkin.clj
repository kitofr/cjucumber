(ns cjucumber.gherkin)

(defn split-lines [s]
  (seq (.split #"\r?\n" s)))

(defn remove-ws [s]
  (let [stripped (map second (re-seq #"(\w+\,*\!*)\r*\n*\t*\s*" s))
        words (map #(str %1 " ") (drop-last stripped))]
    (apply str (flatten `(~words ~(last stripped))))))

(defn strip[regex input]
  (remove-ws (second (re-find regex input))))

(defn gherkin-parse [input]
  (let [feature (strip #"Feature: ([A-Za-z\s+]+)Scenario:" input)
        scenario (strip #"Scenario: ([A-Za-z\s+]+)Given" input)
        steps [(strip #"(Given [A-Za-z\s+]+)When" input)
               (strip #"(When [A-Za-z\s+]+)Then" input)
               (strip #"(Then [A-Za-z\s\/\\0-9+]+)" input)]]
    { :feature feature,
      :scenario { :title scenario, :steps steps }}))


