(defproject clojure-bgproc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.postgresql/postgresql "42.2.5"]
                 [environ "1.1.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot clojure-bgproc.core
  :target-path "target/%s"
  :profiles {:dev {:env {:app-env "dev"}}
             :uberjar {:aot :all}})
