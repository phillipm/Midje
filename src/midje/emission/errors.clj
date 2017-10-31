(ns ^{:doc ""}
  midje.emission.errors
  (:require [midje.emission.plugins.pretty-error-emitter :as pretty-error-emitter]))

;; a default emitter must be set here to enable compilation
(defonce error-emitter pretty-error-emitter/error-emitter)
(defn set-error-emitter-plugin!
  [emitter]
  (alter-var-root #'error-emitter (constantly emitter))
  (println "setting emitter" error-emitter))
