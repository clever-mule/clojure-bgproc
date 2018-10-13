(ns clojure-bgproc.workers.org-month-summary
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.xml :as xml])
  (:import (java.time LocalDate)))

(def query "SELECT organizations.id,
       name,
       short_name,
       EXTRACT('month' FROM date_trunc('month', start_date)) AS month,
       EXTRACT('year' FROM date_trunc('month', start_date))  AS year,
       COUNT(DISTINCT pr.rcn)                                AS projects_count
  FROM organizations
         JOIN participations p ON organizations.id = p.organization_id
         JOIN projects pr ON p.project_rcn = pr.rcn AND p.project_id = pr.id
  WHERE start_date BETWEEN ? AND ?
  GROUP BY date_trunc('month', start_date), organizations.id, name")

(defn- make-sql-date [year month day]
  (java.sql.Date/valueOf (LocalDate/of year month day)))

(defn- get-data [db params]
  (let [start-year (params :start_year 2016)
        end-year (params :end_year 2020)
        start-date (make-sql-date start-year 1 1)
        end-date (make-sql-date end-year 12 31)]
    (jdbc/query db [query start-date end-date])))

(defn- generate-xml-row [row]
  (xml/element :row {}
               (xml/element :organization_id {} (:id row))
               (xml/element :organization_name {} (:name row))
               (xml/element :projects_count {} (:projects_count row))))

(defn- generate-xml [data]
  (xml/element :report {} (map generate-xml-row data)))

(defn- write-xml [data writer]
  (xml/indent (generate-xml data) writer))


(def worker-info {:get-data get-data :write-data write-xml})
