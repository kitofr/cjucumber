(ns cjucumber.test.gherkin
  (:use [cjucumber.gherkin])
  (:use [clojure.test]))

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

