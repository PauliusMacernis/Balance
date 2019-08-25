(ns balance.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [balance.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[balance started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[balance has shut down successfully]=-"))
   :middleware wrap-dev})
