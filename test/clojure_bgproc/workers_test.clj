(ns clojure-bgproc.workers-test
  (:require [clojure-bgproc.workers :as workers]
            [clojure.test :as t]
            [clojure.string :as str])
  (:import java.io.File))

(defn- tmpfile []
  (let [f (java.io.File/createTempFile "clojure-bgproc-worker" ".txt")]
    (.getAbsolutePath f)))

(def -basic-data (constantly (->> 10 range (map inc) (into []))))

(defn -filtered-data [_db params]
  (let [from (:from params 0)]
    (filter #(>= % from) (-basic-data)))
  )

(defn -basic-write-data [data writer]
  (.write writer (str/join " " data)))

(t/deftest run-basic
  (let [tmpfile-path (tmpfile)
        worker-info {:get-data -basic-data :write-data -basic-write-data}]
    (workers/run worker-info {} tmpfile-path)
    (t/is (= (slurp tmpfile-path) "1 2 3 4 5 6 7 8 9 10"))))

(t/deftest run-with-params
  (let [tmpfile-path (tmpfile)
        worker-info {:get-data -filtered-data :write-data -basic-write-data}]
    (workers/run worker-info {:from 3} tmpfile-path)
    (t/is (= (slurp tmpfile-path) "3 4 5 6 7 8 9 10"))))
