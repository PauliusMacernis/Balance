(ns balance.routes.home
  (:require
    [balance.layout :as layout]
    [balance.db.core :as db]
    [clojure.java.io :as io]
    [balance.middleware :as middleware]
    [ring.util.http-response :as response]
    [struct.core :as st]))

;; START. Validation

(def message-schema
  [[:datetime_action
    st/required
    st/string]

   [:amount
    st/required
    st/string
;    {:message "message must contain at least 3 characters"
;     :validate #(> (count %) 3)}
    ]])

(defn validate-message [params]
  (first (st/validate params message-schema)))

(defn save-record! [{:keys [params]}]
  (if-let [errors (validate-message params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/create-record!
       (assoc params :datetime_created (java.util.Date.) :datetime_updated (java.util.Date.)))
      (response/found "/"))))

;; END. Validation

(defn home-page [{:keys [flash] :as request}]
  (layout/render
   request
   "home.html"
   (merge {:records (db/get-records)}
          (select-keys flash [:datetime_action :amount :errors]))))

(defn about-page [request]
  (layout/render request "about.html"))

(defn demo-db [request]
  (layout/render request "demo-db.html"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page
         :post save-record!}]
   ["/about" {:get about-page}]
   ["/demo-db" {:get demo-db}]])
