(ns gerkin-spec
  (:use clojure.test))

(defn split-lines [s]
  (seq (.Split #"\r?\n" s)))

(defn gherkin-parse [input]
  (let [feature (second (re-find #"Feature: ([A-Za-z\s+]+)Scenario:" feature-file))]
    { :feature feature }))


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

(run-tests)
