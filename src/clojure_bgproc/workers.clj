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
