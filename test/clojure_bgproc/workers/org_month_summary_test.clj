(ns clojure-bgproc.workers.org-month-summary-test
  (:require [clojure-bgproc.workers.org-month-summary :as woms]
            [clojure.test :as t]
            [helpers.db :as hdb]))

(def worker-get-data (:get-data woms/worker-info))

(t/use-fixtures :once hdb/with-db)

(t/deftest get-data
  (t/is (= [] (worker-get-data {}))))
