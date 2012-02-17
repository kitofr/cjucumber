(ns gerkin-spec
  (:use clojure.test))

(defn split-lines [s]
  (seq (.split #"\r?\n" s)))

(defn remove-ws [s]
  (let [stripped (map second (re-seq #"(\w+\,*\!*)\r*\n*\t*\s*" s))
        words (map #(str %1 " ") (drop-last stripped))]
    (apply str (flatten `(~words ~(last stripped))))))

(defn gherkin-parse [input]
  (let [feature (second (re-find #"Feature: ([A-Za-z\s+]+)Scenario:" input))]
    { :feature (remove-ws feature) }))


(def feature-file 
  "Feature: In order to be able to read in feature files
  I would like cjucumber to parse gherkin grammar correctly

  Scenario: Scenarios can have a title
  Given there is a given
  When the gherkin parser reads it
  Then it parses given/when/then as separate steps
  ")

(deftest feature-definition-should-be-first
         (let [content (gherkin-parse feature-file)]
           (is (= (content :feature) "In order to be able to read in feature files I would like cjucumber to parse gherkin grammar correctly"))))

(deftest remove-ws-should-strip-wses
         (is (= "all 4 one, one 4 all!" (remove-ws "all    4\n one, \t\r one 4 all!\n"))))

(run-tests)
