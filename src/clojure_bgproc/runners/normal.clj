(ns clojure-bgproc.runners.normal
  (:require [langohr.core :as rmq]
            [langohr.channel :as rmq-ch]
            [langohr.queue :as rmq-q]
            [langohr.consumers :as rmq-cs]
            [langohr.basic :as rmq-b]
            [clojure-bgproc.runners :refer [exchanges]]))

(defn message-handler [ch {:keys [headers delivery-tag]} ^bytes payload]
  (println payload)
  (println headers)
  (println (String. payload))
  (rmq-b/ack ch delivery-tag))


(defn run [conn]
  (let [channel (rmq-ch/open conn)
        qname "rmq-workers-normal"
        queue (rmq-q/declare channel qname)]
    (println (format "Connected. Channel id: %d" (.getChannelNumber channel)))
    (rmq-q/bind channel qname (:normal exchanges)
                {:arguments {"x-dead-letter-exchange" (:error exchanges)}})
    (rmq-cs/subscribe channel qname message-handler)
    {:channel channel}))
