(ns helpers.db
  (:require [clojure.java.jdbc :as j]
            [clojure-bgproc.settings :refer [db-config]]))

(def ^:dynamic *db* nil)

(defn with-db [f]
  (j/with-db-transaction [tr-conn (db-config)]
    (with-redefs [*db* tr-conn]
      (f)))
  )
