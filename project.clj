(defproject clojure-http-server "1.0.0"
  :description "Simple Http Server in Clojure"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot clojure-http-server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
