(require '[clojure.string :as str])

(def fs (js/require "fs"))

(def lines
  (str/split-lines
   (.readFileSync fs "puzzle.input" "utf8")))

(def split-lines (map #(str/split % #" ") lines))

(defn valid? [mini maxi freqs letter]
  (let [occurances (get freqs letter 0)]
    (and (>= occurances mini) (<= occurances maxi))))

(defn process-line [line]
  (let [[nums letter pass] line
        [mini maxi] (map js/parseInt (str/split nums #"-"))]
    (valid? mini maxi (frequencies pass) (subs letter 0 1))))

(println (count (filter true? (map process-line split-lines))))
