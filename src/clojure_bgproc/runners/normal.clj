(ns clojure-bgproc.runners.normal
  (:require [langohr.core :as rmq]
            [langohr.channel :as rmq-ch]
            [langohr.queue :as rmq-q]
            [langohr.consumers :as rmq-cs]
            [langohr.basic :as rmq-b]
            [clojure-bgproc.runners :refer [exchanges]]))

(defn- msgh [ch {:keys [headers delivery-tag]} ^bytes payload]
  (println payload)
  (println headers)
  (println (String. payload))
  (rmq-b/ack ch delivery-tag))


(defn message-handler [ch meta ^bytes payload]
  (future (msgh ch meta payload)))


(defn run [conn]
  (let [channel (rmq-ch/open conn)
        qname "rmq-workers-normal"
        queue (rmq-q/declare channel qname)]
    (println (format "Connected. Channel id: %d" (.getChannelNumber channel)))
    (rmq-q/bind channel qname (:normal exchanges))
    (rmq-cs/subscribe channel qname message-handler)
    {:channel channel}))

(defn test-run [conn]
  (let [channel (rmq-ch/open conn)
        qname "rmq-workers-TEST"
        queue (rmq-q/declare channel qname)]
    (println (format "Connected. Channel id: %d" (.getChannelNumber channel)))
    (rmq-q/bind channel qname (:normal exchanges))
    (rmq-cs/subscribe channel qname message-handler)
    {:channel channel}))
