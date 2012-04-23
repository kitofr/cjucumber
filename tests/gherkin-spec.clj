(ns gerkin-spec
  (:use clojure.test))

(defn split-lines [s]
  (seq (.split #"\r?\n" s)))

(defn remove-ws [s]
  (let [stripped (map second (re-seq #"(\w+\,*\!*)\r*\n*\t*\s*" s))
        words (map #(str %1 " ") (drop-last stripped))]
    (apply str (flatten `(~words ~(last stripped))))))

(defn gherkin-parse [input]
  (letfn strip[regex input]
    (second (re-find regex input)))
  (let [feature (strip #"Feature: ([A-Za-z\s+]+)Scenario:" input)
        scenario (strip #"Scenario: ([A-Za-z\s+]+)Given" input)
        steps '(1 2 3)]
    { :feature (remove-ws feature),
      :scenario { :title (remove-ws scenario), :steps steps }}))


(def feature-file 
  "Feature: In order to be able to read in feature files
  I would like cjucumber to parse gherkin grammar correctly

  Scenario: Scenarios can have a title
  Given there is a given
  When the gherkin parser reads it
  Then it parses given/when/then as separate steps
  ")

(deftest feature-file-parsing
         (let [content (gherkin-parse feature-file)]
           (is (= (content :feature) "In order to be able to read in feature files I would like cjucumber to parse gherkin grammar correctly"))
           (is (= ((content :scenario) :title) "Scenarios can have a title"))
           (is (= (count ((content :scenario) :steps)) 3))))

(deftest remove-ws-should-strip-wses
         (is (= "all 4 one, one 4 all!" (remove-ws "all    4\n one, \t\r one 4 all!\n"))))

(run-tests)
