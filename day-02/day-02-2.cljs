(require '[clojure.string :as str])

(def fs (js/require "fs"))

(def lines
  (str/split-lines
   (.readFileSync fs "puzzle.input" "utf8")))

(def split-lines (map #(str/split % #" ") lines))

(defn valid? [index-1 index-2 letter pass]
  (let [spot-1 (subs pass (- index-1 1) index-1)
        spot-2 (subs pass (- index-2 1) index-2)]
    (or
     (and (= spot-1 letter) (not= spot-2 letter))
     (and (not= spot-1 letter) (= spot-2 letter)))))

(defn process-line [line]
  (let [[nums letter pass] line
        [index-1 index-2] (map js/parseInt (str/split nums #"-"))]
    (valid? index-1 index-2 (subs letter 0 1) pass)))

(println (count (filter true? (map process-line split-lines))))
