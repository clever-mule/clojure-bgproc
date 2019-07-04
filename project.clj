(defproject clojure-bgproc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [com.novemberain/langohr "5.0.0"]
                 [org.slf4j/slf4j-simple "1.8.0-beta2"]]
  :main ^:skip-aot clojure-bgproc.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
