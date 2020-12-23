(require '[clojure.string :as str])

(def fs (js/require "fs"))

(def file (.readFileSync fs "puzzle.input" "utf8"))

(def passports (str/split file "\n\n"))

(def passports-by-segment (map #(str/split % #"\s") passports))

(defn valid-byr? [byr]
  (let [byr-int (js/parseInt byr)]
    (and (= (count byr) 4) (>= byr-int 1920) (<= byr-int 2002))))

(defn valid-iyr? [iyr]
  (let [iyr-int (js/parseInt iyr)]
    (and (= (count iyr) 4) (>= iyr-int 2010) (<= iyr-int 2020))))

(defn valid-eyr? [eyr]
  (let [eyr-int (js/parseInt eyr)]
    (and (= (count eyr) 4) (>= eyr-int 2020) (<= eyr-int 2030))))

(defn hgt-value [hgt]
  (js/parseInt (subs hgt 0 (- (count hgt) 2))))

(defn valid-hgt? [hgt]
  (let [last-two (subs hgt (- (count hgt) 2) (count hgt))]
    (case last-two
      "cm" (and (>= (hgt-value hgt) 150) (<= (hgt-value hgt) 193))
      "in" (and (>= (hgt-value hgt) 59) (<= (hgt-value hgt) 76))
      false)))

(defn valid-hcl? [hcl]
  (let [pound (first hcl)
        color (subs hcl 1)]
    (and (= pound "#") (= 6 (count color)) (if (re-matches #"[a-f0-9]+" color) true false))))

(defn valid-ecl? [ecl]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl))

(defn valid-pid? [pid]
  (and (if (re-matches #"[0-9]+" pid) true false) (= 9 (count pid))))

(defn valid-segment? [segment]
  (case (first segment)
    "byr" (valid-byr? (second segment))
    "iyr" (valid-iyr? (second segment))
    "eyr" (valid-eyr? (second segment))
    "hgt" (valid-hgt? (second segment))
    "hcl" (valid-hcl? (second segment))
    "ecl" (valid-ecl? (second segment))
    "pid" (valid-pid? (second segment))
    "cid" true))

(defn valid-passport? [passport-by-segment]
  (let [segments (map #(str/split % ":") passport-by-segment)
        segment-names (set (map #(first (str/split % ":")) passport-by-segment))]
    (and (= (disj segment-names "cid") #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})
         (every? true? (map valid-segment? segments)))))

(println (count (filter true? (map valid-passport? passports-by-segment))))
