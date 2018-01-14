(ns ^{:doc "Facts as a data structure"}
  midje.data.fact
  (:refer-clojure :exclude [name namespace]))

;;; This is a potential target for migrating the non-parsing,
;;; non-checking parts of ideas/facts.clj, ideas/metadata.clj. It's
;;; sketchy now, since it's only being used to keep the emission
;;; plugin code from depending on code slated to be destroyed.

(def source (comp :midje/source meta))

(def guid (comp :midje/guid meta))

(def file (comp :midje/file meta))

(def line (comp :midje/line meta))

(def namespace (comp :midje/namespace meta))

(def name (comp :midje/name meta))

(def description (comp :midje/description meta))

(def top-level? (comp :midje/top-level? meta))
