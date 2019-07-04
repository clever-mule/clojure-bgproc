(ns clojure-bgproc.runners
  (:require [langohr.core :as rmq]
            [langohr.channel :as rmq-ch]
            [langohr.exchange :as rmq-ex]
            [langohr.queue :as rmq-q]))

(defn rmq-init []
  (let [conn (rmq/connect)
        ch (rmq-ch/open conn)]
    (rmq/close ch)
    {:connection conn}))

