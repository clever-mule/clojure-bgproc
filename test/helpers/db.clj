(ns helpers.db
  (require [clojure-bgproc.settings :refer [*db*]]
           [clojure.java.jdbc :as jdbc])
  (import [java.io.File]))

(def schema
  ["CREATE TABLE `projects` (`rcn` Bigint NOT NULL, `id` Bigint NOT NULL, `acronym` varchar(127), `status` varchar(127), `title` varchar(2047), `start_date` date, `end_date` date, PRIMARY KEY (`rcn`, `id`));"
   "CREATE TABLE `organizations` (`id` Bigint NOT NULL PRIMARY KEY, `name` text, `short_name` varchar(255), `activity_type` varchar(11), `country` varchar(5));"
   "CREATE TABLE `participations` (`project_rcn` Bigint, `project_id` Bigint, `role` varchar(127), `organization_id` integer REFERENCES `organizations`, FOREIGN KEY (`project_rcn`, `project_id`) REFERENCES `projects`);"])

(defn with-db [test-fn]
  (let [dbf (java.io.File/createTempFile "clojure-bgproc-testdb" ".db")
        dbpath (.getAbsolutePath dbf)]
    (binding [*db* {:dbtype "sqlite" :dbname dbpath}]
      (doseq [stmt schema] (jdbc/execute! *db* [stmt] {:transaction? false}))
      (test-fn))))

