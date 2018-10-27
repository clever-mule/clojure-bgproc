(ns clojure-bgproc.core
  (:gen-class)
  (:require [clojure-bgproc.settings :as settings]
            [clojure-bgproc.runners :refer [rmq-init]]
            [clojure-bgproc.runners.normal :as rnn]))

(defn- run-normal-worker [conn i]
  (println (str "Starting worker " (inc i)))
  (rnn/run conn))

(defn- run-normal-workers [conn]
  (let [rg (range (settings/workers-count))]
    (into [] (map (partial run-normal-worker conn) rg))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [rmq-data (rmq-init)
        conn (:connection rmq-data)
        normal-workers (run-normal-workers conn)]
    (println "System started")))
