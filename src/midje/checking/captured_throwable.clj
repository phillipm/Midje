(ns ^{:doc ""}
  midje.checking.captured-throwable
  (:require [midje.emission.errors :as emission.errors]
            [midje.emission.protocol.error-emitter :as protocol.error-emitter]))

;; When a fact throws an Exception or Error it gets wrapped
;; in this deftype

(defprotocol ICapturedThrowable
  (throwable [this])
  (friendly-stacktrace [this]))

(deftype CapturedThrowable [ex]
  ICapturedThrowable
  (throwable [this] ex)
  (friendly-stacktrace [this]
    (println emission.errors/error-emitter )
    (println (throwable this))
    (println protocol.error-emitter/ErrorEmitter)

    (protocol.error-emitter/load-failure emission.errors/error-emitter ex)
    #_(protocol.error-emitter/test-error emission.errors/error-emitter (throwable this))))

(defn captured-throwable [ex]
  (CapturedThrowable. ex))

(defn captured-throwable? [x]
  (instance? CapturedThrowable x))

(defn captured-message [ex]
  (.getMessage ^Throwable (throwable ex)))
