;; There are two kinds of functions now. Helper functions about records are in the `specific`
;; namespace. But the generic functions come from the `generic` namespace.

(ns as-documentation.about-defrecord.using-refer--provided-tests.test
  (:require [midje.sweet :refer :all])
  (:require [as-documentation.about-defrecord.generic
             :refer [bump twice]])
  (:require [as-documentation.about-defrecord.using-refer--provided-tests.specific
             :refer [->Record]]))

(facts "in separate test namespace"
  (let [rec (->Record 3)]
    (bump rec) => 4
    (bump rec 5) => 8
    (twice rec) => 6))

(fact "you can test in terms of a record's methods"
  (let [rec (->Record 3)]
    (+ 1 (bump rec)) => 0
    (provided
      (bump rec) => -1)))


(defprotocol Caller
  (call [caller x]))

(defrecord ACaller [func]
  Caller
  (call [_ x] (func x)))

(defn fack-func [args] 'shouldnt-return)

(facts "Mock a func call inside a inline record"
  (against-background [(fack-func 1) => 100]
    (fact "against-background"
      (call (->ACaller fack-func) 1) => 100))

  (fact "provided"
    (call (->ACaller fack-func) 1) => 100
    (provided (fack-func 1) => 100)))

(def caller-instance (->ACaller fack-func))
(facts "Mock a func call inside a record reference"
  (against-background [(fack-func 1) => 100]
    (fact "against-background"
      (call caller-instance 1) => 100))

  (fact "provided"
    (call caller-instance 1) => 100
    (provided (fack-func 1) => 100)))
