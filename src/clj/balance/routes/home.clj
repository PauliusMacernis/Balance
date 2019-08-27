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
    st/string
    {:message  "datetime must contain 19 characters, for example: 2019-08-27 21:56:00"
     :validate #(= (count %) 19)}]

   [:amount
    st/required
    st/string
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
