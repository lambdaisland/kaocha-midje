(ns midjetest.the-test
  (:require [midje.sweet :as m]
            [clojure.string :as str]))

(m/fact "`split` splits strings on regular expressions and returns a vector"
        (str/split "a/b/c" #"/") => ["a" "b" "c"]
        (str/split "" #"irrelevant") => ["xxx"]
        (str/split "no regexp matches" #"a+\s+[ab]") => ["no regexp matches"])


(comment
  (macroexpand-1 '
   (m/fact "this is another fact"
           (conj [1 2] 3) => [1 2 3]))

  (require '[midje.repl :as r])


  (defn remix-tx [{:keys [...] :as opts}]
    {:change (:change opts (:article/published-change remix))})

  )
