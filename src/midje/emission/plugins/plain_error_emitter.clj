(ns ^{:doc "Prints exceptions using traditional stacktrace formatting"}
  midje.emission.plugins.pretty-error-emitter
  (:require [clojure.string :as str]
            [midje.emission.protocol.error-emitter :as protocol.error-emitter]
            [midje.util.ecosystem :as ecosystem]
            [midje.util.exceptions :as exceptions]))


(defrecord PlainErrorEmitter []
  protocol.error-emitter/ErrorEmitter
  (load-failure [this throwable] (.getMessage throwable))
  (test-error   [this throwable]
    (str/join ecosystem/line-separator (exceptions/friendly-exception-lines throwable "              "))))

(defn new-error-emitter []
  (map->PlainErrorEmitter {}))
