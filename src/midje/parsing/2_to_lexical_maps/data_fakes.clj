(ns ^{:doc "=contains=> prereqisites"}
  midje.parsing.2-to-lexical-maps.data-fakes
  (:use [utilize.seq :only (separate find-first)]
        [midje.util.object-utils :only [object-name]]
        [midje.checkers :only [exactly]]
        [midje.checkers.defining :only [checker? checker-makers]]
        [midje.parsing.1-to-normal-form.expects :only [expect? up-to-full-expect-form]]
        [midje.util.form-utils :only [first-named? translate-zipper map-difference
                                      hash-map-duplicates-ok pred-cond
                                      quoted-list-form? extended-fn?]]
        [midje.checkers.extended-equality :only [extended-= extended-list-=]]
        [midje.internal-ideas.file-position :only [user-file-position]]
        [midje.util.thread-safe-var-nesting :only [namespace-values-inside-out
                                                   with-pushed-namespace-values
                                                   with-altered-roots]]
        [midje.internal-ideas.wrapping :only [with-wrapping-target]]
        [midje.util.deprecation :only [deprecate]]
        [midje.ideas.arrow-symbols]
        [clojure.tools.macro :only [macrolet]])
  (:require [midje.data.metaconstant :as metaconstant]
            [clojure.zip :as zip]
            [midje.config :as config]
            [midje.parsing.util.fnref :as fnref]
            [midje.error-handling.exceptions :as exceptions]
            [midje.parsing.lexical-maps :as lexical-maps]
            [midje.emission.api :as emit])
  (:import midje.data.metaconstant.Metaconstant))


(defn to-lexical-map-form [[metaconstant arrow contained & overrides]]
  (let [source-details `{:call-form '~metaconstant
                         :arrow '~arrow
                         :rhs '~(cons contained overrides)}]
    `(merge
      (lexical-maps/data-fake ~metaconstant ~arrow ~contained)
      ~source-details
      ~(apply hash-map-duplicates-ok overrides))))
