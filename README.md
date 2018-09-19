# kaocha-midje

[Midje](https://github.com/marick/Midje) integration for [Kaocha](https://github.com/lambdaisland/kaocha).

## Links

- [Github](https://github.com/lambdaisland/kaocha-midje)
- [Clojars](https://clojars.org/lambdaisland/kaocha-midje)
- [cljdoc](https://cljdoc.xyz/d/lambdaisland/kaocha-midje/CURRENT)

## Usage

- add `lambdaisland/kaocha-midje {:mvn/version "0.0"}` to your dependencies
- add a test suite with `:type :kaocha.type/midje`

``` clojure
;; tests.edn
#kaocha/v1
{:tests [{:type :kaocha.type/midje
          :source-paths ["src"]
          :test-paths ["test"]}]}
```

## License

&copy; Arne Brasseur 2018
Available under the terms of the Eclipse Public License 1.0, see LICENSE.txt
