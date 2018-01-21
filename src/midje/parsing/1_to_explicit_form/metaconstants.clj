(ns ^{:doc "Transforming code in a way that produces metaconstants"}
  midje.parsing.1-to-explicit-form.metaconstants
  (:require [midje.data.metaconstant :as data]))

(defn- nested-tree-filter
  [branch? pred? root]
  (let [walk (fn walk [node]
               (if (branch? node)
                 (apply clojure.set/union (map walk node))
                 (when (pred? node)
                   #{node})))]
    (walk root)))

(defn predefine-metaconstants-from-form [form]
  (let [metaconstant-symbols (nested-tree-filter
                               coll? data/metaconstant-symbol? form)]
    (doseq [symbol metaconstant-symbols]
      (intern *ns* symbol (data/metaconstant symbol {} nil))))
  form)

