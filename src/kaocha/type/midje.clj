(ns kaocha.type.midje
  (:require [clojure.spec.alpha :as s]
            [kaocha.testable :as testable]
            [kaocha.load :as load]
            [clojure.test :as t]
            [midje.data.compendium]
            [midje.config :as m.config]
            [midje.emission.api :as m.e.api]
            [midje.emission.state :as m.e.state]
            [midje.repl :as m.repl]
            [kaocha.output :as out]))

(def testable-defaults {:kaocha/test-paths ["test"]
                        :kaocha/ns-patterns [".*-test"]})

(defn ->ns-testable [ns-name]
  {:kaocha.testable/type :kaocha.type.midje/ns
   :kaocha.testable/id   (keyword (str ns-name))
   ::ns-name             ns-name})

(defmethod testable/-load :kaocha.type/midje [testable]
  (load/load-test-namespaces (merge testable-defaults testable) ->ns-testable))

(defmethod testable/-run :kaocha.type/midje [testable test-plan]
  (t/do-report {:type :begin-test-suite})
  (let [results  (testable/run-testables (:kaocha.test-plan/tests testable) test-plan)
        testable (-> testable (dissoc :kaocha.test-plan/tests) (assoc :kaocha.result/tests results))]
    (t/do-report {:type :end-test-suite, :kaocha/testable testable})
    testable))

(defmethod testable/-load :kaocha.type.midje/ns [{::keys [ns-name] :as testable}]
  (let [ns-name (::ns-name testable)]
    (try
      (m.config/with-augmented-config {:check-after-creation false
                                       :print-level :print-nothing}
        (m.repl/forget-facts :all)
        (m.repl/load-facts ns-name))

      (let [ns-obj  (the-ns ns-name)
            ns-meta (meta ns-obj)
            facts (m.repl/fetch-facts ns-name)]
        (assoc testable
               :kaocha.test-plan/tests
               (for [fact facts
                     :let [fmeta (meta fact)]]
                 {:kaocha.testable/type :kaocha.type.midje/fact
                  :kaocha.testable/id   (keyword (str ns-name) (:midje/guid fmeta))
                  :kaocha.testable/meta fmeta
                  ::fact fact})
               :kaocha.testable/meta ns-meta
               #_#_::compendium
               midje.data.compendium/global))
      (catch Throwable t
        (out/warn "Failed loading " ns-name " " (.getMessage t))
        (assoc testable :kaocha.test-plan/load-error t)))))

(defmethod testable/-run :kaocha.type.midje/ns [testable test-plan]
  (t/do-report {:type :begin-test-ns})
  (if-let [load-error (:kaocha.test-plan/load-error testable)]
    (do
      (t/do-report {:type     :error
                    :message  "Failed to load namespace."
                    :expected nil
                    :actual   load-error})
      (t/do-report {:type :end-test-ns})
      (assoc testable :kaocha.result/error 1))
    (let [tests  (testable/run-testables (:kaocha.test-plan/tests testable) test-plan)
          result (assoc (dissoc testable :kaocha.test-plan/tests)
                        :kaocha.result/tests
                        tests)]
      (t/do-report {:type :end-test-ns})
      result)))

(def ^:dynamic *midje-results* nil)

(defmethod testable/-run :kaocha.type.midje/fact [{::keys [fact] :as testable} test-plan]
  (t/do-report {:type :begin-test-var})
  (binding [*midje-results* (atom {:pass 0
                                   :fail []})]
    (with-redefs [m.e.api/pass (fn []
                                 (t/do-report {:type :pass})
                                 (swap! *midje-results* update :pass inc))
                  m.e.api/fail (fn [fail-map]
                                 (t/do-report {:type :fail})
                                 (swap! *midje-results* update :fail conj fail-map))]

      (fact)
      (let [{:keys [pass fail]} @*midje-results*
            result              (assoc testable
                                       :kaocha.result/count 1
                                       :kaocha.result/pass pass
                                       :kaocha.result/fail (count fail)
                                       ::midje-failures fail)]
        (t/do-report {:type :end-test-var})
        result))))


(s/def :kaocha.type/midje any?)
(s/def :kaocha.type.midje/ns any?)
(s/def :kaocha.type.midje/fact any?)


(comment
  (keys (-> (:by-namespace @midje.data.compendium/global) vals first first meta))
  ;; => (:midje/guid
  ;;     :midje/source
  ;;     :midje/namespace
  ;;     :midje/file
  ;;     :midje/line
  ;;     :midje/description
  ;;     :midje/name
  ;;     :midje/top-level-fact?)
  )
