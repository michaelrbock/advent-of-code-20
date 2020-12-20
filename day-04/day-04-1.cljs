(require '[clojure.string :as str])

(def fs (js/require "fs"))

(def file (.readFileSync fs "puzzle.input" "utf8"))

(def passports (str/split file "\n\n"))

(def passports-by-segment (map #(str/split % #"\s") passports))

(defn valid? [passport-by-segment]
  (let [segments (set (map #(first (str/split % ":")) passport-by-segment))]
    (= (disj segments "cid") #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})))

(println (count (filter true? (map valid? passports-by-segment))))
