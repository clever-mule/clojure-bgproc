(ns clojure-bgproc.workers.org-month-summary-test
  (:require [clojure-bgproc.workers.org-month-summary :as woms]
            [clojure.test :as t]
            [clojure.java.jdbc :as j]
            [helpers.db :as hdb]
            [helpers.config :refer :all])
  (:import [java.time LocalDate]))

(defn- seed-db [f]
  (j/insert-multi!
   hdb/*db*
   :organizations
   [{:id 1 :name "Organization 1" :short_name "Org 1"
     :activity_type "PUB" :country "FI"}
    {:id 2 :name "Organization 2" :short_name "Org 2"
     :activity_type "PUB" :country "DK"}])
  (j/insert-multi!
   hdb/*db*
   :projects
   [{:rcn 100000 :id 1 :title "Caesar 1" :acronym "CS1" :status "signed"
     :start_date (LocalDate/of 2017 1 1)}
    {:rcn 100100 :id 2 :title "Caesar 2" :acronym "CS2" :status "closed"
     :start_date (LocalDate/of 2017 6 1) :end_date (LocalDate/of 2017 10 4)}])
  (j/insert-multi!
   hdb/*db*
   :participations
   [{:project_rcn 100000 :project_id 1 :organization_id 1 :role "participant"}
    {:project_rcn 100000 :project_id 1 :organization_id 2 :role "partner"}
    {:project_rcn 100100 :project_id 2 :organization_id 2 :role "coordinator"}])
  (f)
  )

(def worker-get-data (:get-data woms/worker-info))

(t/use-fixtures :each hdb/with-db seed-db)
(t/use-fixtures :once with-test-config)

(t/deftest get-data
  (t/is (= [{:id 1 :name "Organization 1" :short_name "Org 1"
             :month 1 :year 2017 :projects_count 1}
            {:id 2 :name "Organization 2" :short_name "Org 2"
             :month 1 :year 2017 :projects_count 1}
            {:id 2 :name "Organization 2" :short_name "Org 2"
             :month 6 :year 2017 :projects_count 1}]
           (worker-get-data hdb/*db* {}))))
