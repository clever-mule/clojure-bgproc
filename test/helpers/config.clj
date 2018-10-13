(ns helpers.config
  (:require [clojure-bgproc.settings :refer :all]))

(defn with-test-config [f]
  (with-redefs [*app-env* "test"]
    (f)))
