(ns clojure-bgproc.workers
  (:require [clojure.java.io :refer [writer]])
  )

(defn run
  "Run a worker that gets some data and writes it into a file"
  [worker-info params filename]
  (let [get-data (:get-data worker-info)
        write-data (:write-data worker-info)
        data (get-data params)]
    (with-open [w (writer filename)]
      (write-data data w))
    )
  )

(defn run-async [worker-info params filename]
  (future (run worker-info params filename)))

(defn run-in [seconds worker-info params filename]
  (future
    (Thread/sleep (* 1000 seconds))
    (run worker-info params filename)))
