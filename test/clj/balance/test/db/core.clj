(ns balance.test.db.core
  (:require
    [balance.db.core :refer [*db*] :as db]
    [luminus-migrations.core :as migrations]
    [clojure.test :refer :all]
    [clojure.java.jdbc :as jdbc]
    [balance.config :refer [env]]
    [mount.core :as mount])
  (:import (java.time LocalDateTime)))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'balance.config/env
      #'balance.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-record
  (jdbc/with-db-transaction [t-conn *db*]
                            (jdbc/db-set-rollback-only! t-conn)
                            (let [timestamp (LocalDateTime/now)]
                              ; Is record created?
                              (is (= 1 (db/create-record!
                                         t-conn
                                         {:datetime_action  timestamp
                                          :amount           123
                                          :datetime_created timestamp
                                          :datetime_updated timestamp}
                                         {:connection t-conn})))
                              ; Is record created with the data expected?
                              (is (=
                                    {:datetime_action  timestamp
                                     :amount           123
                                     :datetime_created timestamp
                                     :datetime_updated timestamp}
                                    (-> (db/get-records t-conn {})
                                        (first)
                                        (select-keys [:datetime_action :amount :datetime_created :datetime_updated])))))))