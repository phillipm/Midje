(ns ^{:doc "Functions for Midje to deal elegantly with exceptions."}
  midje.util.exceptions
  (:require [clojure.string :as str]
            [midje.util.ecosystem :as ecosystem]))

;;; Creating

(defn user-error
  "Used when a user does something off-limits or incompatible"
  [& lines]
  (Error. (str/join ecosystem/line-separator lines)))


;;; Printing ergonomic stacktraces

(defn- ^{:testable true} stacktrace-as-strings [^Throwable ex]
  (map str (.getStackTrace ex)))

(letfn [(remove-matches [re strings]
          (remove #(re-find re %) strings))]

  (defn- ^{:testable true} without-clojure-strings [all-strings]
    (remove-matches #"^java\.|^clojure\.|^sun\.|^swank\.|^user\$eval" all-strings))

  (defn- ^{:testable true} without-midje-or-clojure-strings [all-strings]
    (remove-matches #"^java\.|^clojure\.|^sun\.|^swank\.|^user\$eval|^midje" all-strings)))

(declare caused-by-lines)

(defn- main-exception-lines [ex prefix]
  (cons (str ex)
        (map #(str prefix %)
             (without-midje-or-clojure-strings (stacktrace-as-strings ex)))))

(defn friendly-exception-lines [ex prefix]
  (concat (main-exception-lines ex prefix)
          (caused-by-lines ex prefix)))

(defn- caused-by-lines [ex prefix]
  (when-let [cause (.getCause ex)]
    (let [[message & stacktrace] (friendly-exception-lines cause prefix)]
      (concat ["" (str prefix "Caused by: " message)]
              stacktrace))))

(defn user-error-exception-lines [throwable]
  (cons (str throwable)
    (without-clojure-strings (stacktrace-as-strings throwable))))
