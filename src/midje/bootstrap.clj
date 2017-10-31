(ns midje.bootstrap
  (:require [midje.config :as config]
            [midje.emission.api :as emission.api]
            [midje.emission.errors :as emission.errors]
            [midje.emission.colorize :as emission.colorize]
            [midje.emission.state :as emission.state]))

(defonce bootstrapped false)

(defn bootstrap []
  (when-not bootstrapped

    (let [saved-ns (ns-name *ns*)]
      (try
        (in-ns 'midje.config)
        (config/load-config-files)
      (finally
        (in-ns saved-ns))))

    (emission.api/load-plugin (config/choice :emitter))
    (println "config emitter" (config/choice :error-emitter))
    (emission.errors/set-error-emitter-plugin! (config/choice :error-emitter))
    (println "is set" emission.errors/error-emitter)
    (emission.colorize/init!)
    (emission.state/no-more-plugin-installation)

    (alter-var-root #'bootstrapped (constantly true))))
