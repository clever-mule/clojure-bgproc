(ns clojure-bgproc.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [clojure-bgproc.core :refer :all]
            [clojure-bgproc.settings :refer [*app-env* db-config]]
            [helpers.config :refer [with-test-config]]))

(use-fixtures :once with-test-config)

(deftest app-env-test
  (is (= "test" *app-env*)))

(deftest app-config-test
  (is (s/ends-with? (:dbname (db-config)) "test")))
