(ns day-01.core
  (:require [clojure.string]))

(def fs (js/require "fs"))

(def entries
  (set
   (map js/parseInt
        (clojure.string/split-lines
         (.readFileSync fs "puzzle.input" "utf8")))))

(println (reduce *
                 (filter (fn [entry]
                           (contains? entries (- 2020 entry))) entries)))
