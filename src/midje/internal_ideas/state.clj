(ns ^{:doc
"This namespace contains [will eventually contain] all the
 non-configuration state of the program. That is: the workings
 that are normally invisible to any user, with the exception
 of tests and people debugging."}
  midje.internal-ideas.state)

(def top-level-fact-output-lines [])
(def fact-output-lines [])

(defmacro make-counter-atom [name & keys]
  (letfn [(make-one-incrementer [key]
            `(defn ~(symbol (str name ":inc" key "!")) []
               (swap! ~name
                      (partial merge-with +) {~key 1})))
          (make-one-getter [key]
            `(defn ~(symbol (str name key)) []
               (~key (deref ~name))))]
            
    (let [fresh-name (symbol (str "fresh-" name))
          reset-name (symbol (str "reset-" name "!"))]
      `(do
         (def ~name (atom 0))
         (.setDynamic (var ~name))
         (def ~fresh-name ~(zipmap keys (repeat 0)))
         (defn ~reset-name [] (reset! ~name ~fresh-name))
         (~reset-name)
         ~@(map make-one-getter keys) 
         ~@(map make-one-incrementer keys)))))
       

(make-counter-atom output-counters
  :midje-passes :midje-failures :clojure-test-passes :clojure-test-failures)

(def ^{:dynamic true} emission-functions nil)