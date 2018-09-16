(ns clojure-bgproc.workers.org-month-summary
  (require [clojure.java.jdbc :as jdbc]
           [clojure-bgproc.settings :refer [db]])
  )

(def query "SELECT organizations.id, name, short_name,
    strftime('%m', start_date) AS month, strftime('%Y', start_date) AS year,
    COUNT(DISTINCT pr.rcn) AS projects_count
  FROM organizations
  JOIN participations p ON organizations.id = p.organization_id
  JOIN projects pr ON p.project_rcn = pr.rcn AND p.project_id = pr.id
  WHERE start_date BETWEEN ? AND ?
  GROUP BY strftime('%m-%Y', start_date), name
  ")

(defn -get-data [params]
  (let [start-year (params :start_year 2016)
        end-year (params :end_year 2020)
        start-date (str start-year "-01-01")
        end-date (str end-year "-12-31")]
    (jdbc/query db [query start-date end-date])
    )
  )

(def worker-info {:get-data -get-data})
