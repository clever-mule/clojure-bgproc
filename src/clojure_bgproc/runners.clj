(ns clojure-bgproc.runners
  (:require [langohr.core :as rmq]
            [langohr.channel :as rmq-ch]
            [langohr.exchange :as rmq-ex]
            [langohr.queue :as rmq-q]))

(def exchanges {:normal "rmq-workers.normal"
                :schedule "rmq-workers.schedule"
                :error "rmq-workers.error"})

(defn rmq-init []
  (let [conn (rmq/connect)
        ch (rmq-ch/open conn)]
    (rmq-ex/direct ch "rmq-workers.normal")
    (rmq-ex/direct ch "rmq-workers.schedule")
    (rmq-ex/direct ch "rmq-workers.error")
    (rmq/close ch)
    {:connection conn}))

