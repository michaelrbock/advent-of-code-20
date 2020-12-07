(ns day-01.core
  (:require [clojure.string]))

(def fs (js/require "fs"))

(def entries
  (set
   (map js/parseInt
        (clojure.string/split-lines
         (.readFileSync fs "puzzle.input" "utf8")))))

(def match
  (first
   (filter #(= 2020 (reduce + %))
           (for [x day-01.core/entries y day-01.core/entries z day-01.core/entries] [x y z]))))

(println (reduce * match))
