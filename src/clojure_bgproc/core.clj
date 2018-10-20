(ns clojure-bgproc.core
  (:gen-class)
  (:require [clojure-bgproc.runners :refer [rmq-init]]
            [clojure-bgproc.runners.normal :as rnn]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [rmq-data (rmq-init)
        conn (:connection rmq-data)]
    (rnn/run conn)
    (rnn/test-run conn)))
