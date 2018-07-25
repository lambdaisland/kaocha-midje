(ns kaocha.type.midje
  (:require [clojure.spec.alpha :as s]
            [kaocha.testable :as testable]))

(defmethod testable/-load :kaocha.type/midje [testable]
  testable)

(defmethod testable/-run :kaocha.type/midje [testable test-plan]
  testable)


(s/def :kaocha.type/midje any?)
