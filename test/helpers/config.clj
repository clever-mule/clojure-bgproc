(ns helpers.config
  (:require [clojure-bgproc.settings :refer [*app-env*]]))

(defn with-test-config [f]
  (with-redefs [*app-env* "test"]
    (f)))
