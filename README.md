# kaocha-midje

<!-- badges -->
[![CircleCI](https://circleci.com/gh/lambdaisland/kaocha-midje.svg?style=svg)](https://circleci.com/gh/lambdaisland/kaocha-midje) [![cljdoc badge](https://cljdoc.org/badge/lambdaisland/kaocha-midje)](https://cljdoc.org/d/lambdaisland/kaocha-midje) [![Clojars Project](https://img.shields.io/clojars/v/lambdaisland/kaocha-midje.svg)](https://clojars.org/lambdaisland/kaocha-midje) [![codecov](https://codecov.io/gh/lambdaisland/kaocha-midje/branch/master/graph/badge.svg)](https://codecov.io/gh/lambdaisland/kaocha-midje)
<!-- /badges -->

[Midje](https://github.com/marick/Midje) integration for [Kaocha](https://github.com/lambdaisland/kaocha).

## Links

- [Github](https://github.com/lambdaisland/kaocha-midje)
- [Clojars](https://clojars.org/lambdaisland/kaocha-midje)
- [cljdoc](https://cljdoc.xyz/d/lambdaisland/kaocha-midje/CURRENT)

## Usage

- add `lambdaisland/kaocha-midje {:mvn/version "0.0-5"}` to your dependencies
- add a test suite with `:type :kaocha.type/midje`

``` clojure
;; tests.edn
#kaocha/v1
{:tests [{:type :kaocha.type/midje
          :source-paths ["src"]
          :test-paths ["test"]}]}
```

<!-- license-epl -->
## License
&nbsp;
Copyright &copy; 2018-2019 Arne Brasseur
&nbsp;
Available under the terms of the Eclipse Public License 1.0, see LICENSE.txt
<!-- /license-epl -->
