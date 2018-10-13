(ns clojure-bgproc.settings
  (:require [environ.core :refer [env]]
            [clojure.edn :as edn]))

(def ^:dynamic *app-env* (env :app-env "production"))

(defn- config-path- []
  (if (= *app-env* "test") "settings_test.edn" "settings.edn"))
(def ^:private config-path (memoize config-path-))

(defn- config- [] (-> (config-path) slurp edn/read-string))
(def config (memoize config-))
(defn db-config [] (:db (config)))
