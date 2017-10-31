(ns ^{:doc "Describes emission api for errors ecountered during testing"}
  midje.emission.protocol.error-emitter)

(println "LOADING \n LOADING \n LOADING")
(defprotocol ErrorEmitter
  (load-failure [component throwable] "String representation of errors
                                      encountered while loading namespaces")
  (test-error   [component throwable] "String representation of errors
                                      encountered while running tests"))
(println ErrorEmitter)
