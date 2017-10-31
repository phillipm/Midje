(ns ^{:doc "Pretty prints exceptions"}
  midje.emission.plugins.pretty-error-emitter
  (:require [clojure.string :as str]
            [midje.emission.protocol.error-emitter :as protocol.error-emitter]
            [midje.util.ecosystem :as ecosystem]
            [midje.util.exceptions :as exceptions]
            [io.aviso.exception :as aviso.exception]
            [io.aviso.ansi :as aviso.ansi]))

(defn format-exception [throwable]
  (binding [aviso.exception/*traditional* true
            aviso.exception/*fonts*       (merge aviso.exception/*fonts*
                                                 {:message       aviso.ansi/white-font
                                                  :clojure-frame aviso.ansi/white-font
                                                  :function-name aviso.ansi/white-font})]
    (aviso.exception/format-exception throwable)))

(defrecord PrettyErrorEmitter []
  protocol.error-emitter/ErrorEmitter
  (load-failure [this throwable] (format-exception throwable))
  (test-error   [this throwable]  "foo"
    ;; TODO PLM: eventually replace with aviso stacktrace formatting
    #_(let [exception-lines (exceptions/friendly-exception-lines throwable "              ")]
      (str/join ecosystem/line-separator exception-lines))))

(def error-emitter (map->PrettyErrorEmitter {}))
