(ns clojure-bgproc.runners.normal
  (:require [langohr.core :as rmq]
            [langohr.channel :as rmq-ch]
            [langohr.queue :as rmq-q]
            [langohr.consumers :as rmq-cs]
            [langohr.basic :as rmq-b]))

(defn message-handler [ch {:keys [headers delivery-tag]} ^bytes payload]
  (println payload)
  (println headers)
  (println (String. payload))
  (rmq-b/ack ch delivery-tag))


(defn run [conn]
  (let [channel (rmq-ch/open conn)
        qname "clever-mule:report-tasks"
        queue (rmq-q/declare channel qname {:auto-delete false})]
    (println (format "Connected. Channel id: %d" (.getChannelNumber channel)))
    (rmq-cs/subscribe channel qname message-handler)
    {:channel channel}))
