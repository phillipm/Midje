(ns midje.scratch
  (:require [clj-async-profiler.core :as prof]
            [midje.sweet :refer :all]))


(unfinished child-of-wealthy-alumnus?)
(defn gpa [student courses]
  (let [credits (map :credit-hours courses)
        grades (map :grade courses)
        weighted (map * credits grades)
        true-gpa (/ (reduce + 0.0 weighted) (reduce + 0 credits))
        adjustment (if (child-of-wealthy-alumnus? student) 0.5 0)]
    (+ true-gpa adjustment)))



(defn run-fact []
  (fact
    (let [correct-gpa 3.66
          tolerance 0.01
          coursework [{:credit-hours 1, :grade 5}
                      {:credit-hours 2, :grade 3}]]

      (gpa ..student.. coursework) => (roughly correct-gpa tolerance)
      (provided (child-of-wealthy-alumnus? ..student..) => false)

      (gpa ..student.. coursework) => (roughly (+ correct-gpa 0.5) tolerance)
      (provided (child-of-wealthy-alumnus? ..student..) => true))))

(defn expand-fact []
  (macroexpand `(fact
                  (let [correct-gpa 3.66
                        tolerance 0.01
                        coursework [{:credit-hours 1, :grade 5}
                                    {:credit-hours 2, :grade 3}]]

                    (gpa ..student.. coursework) => (roughly correct-gpa tolerance)
                    (provided (child-of-wealthy-alumnus? ..student..) => false)

                    (gpa ..student.. coursework) => (roughly (+ correct-gpa 0.5) tolerance)
                    (provided (child-of-wealthy-alumnus? ..student..) => true)))))


(defn burn-cpu [secs]
  (let [start (System/nanoTime)]
    (while (< (/ (- (System/nanoTime) start) 1e9) secs)
      (run-fact))))

(defn expand-cpu [secs]
  (let [start (System/nanoTime)]
    (while (< (/ (- (System/nanoTime) start) 1e9) secs)
      (expand-fact))))

(defn run-it [times]
  (doseq [_ (range times)]
    ;(run-fact)
    (expand-fact)
    ))

(defn profile []
  (prof/start {})
  (expand-cpu 50)
  (prof/stop {}))

(println (profile))



;(time (run-it 10000))
;(run-fact)

