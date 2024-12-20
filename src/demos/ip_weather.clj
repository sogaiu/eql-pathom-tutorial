(ns demos.ip-weather
  (:require
   [cheshire.core :as json]
   [com.wsscode.pathom3.connect.indexes :as pci]
   [com.wsscode.pathom3.connect.operation :as pco]
   [com.wsscode.pathom3.interface.eql :as p.eql]
   [org.httpkit.client :as http]))

(pco/defresolver ip->lat-long
  [{:keys [ip]}]
  {::pco/output [:latitude :longitude]}
  (-> (slurp (str "https://get.geojs.io/v1/ip/geo/" ip ".json"))
      (json/parse-string keyword)
      (select-keys [:latitude :longitude])))

(comment

  (ip->lat-long {:ip "198.29.213.3"})
  ;; =>
  {:latitude "41.5144", :longitude "-88.0608"}

  )

(pco/defresolver latlong->temperature
  [{:keys [latitude longitude]}]
  {:temperature
   (-> @(http/request
         {:url (str "https://www.7timer.info/bin/api.pl" "?"
                    "lon=" longitude "&"
                    "lat=" latitude "&"
                    "product=" "astro" "&"
                    "output=" "json")})
       :body
       (json/parse-string keyword)
       :dataseries
       first
       :temp2m)})

(comment

  (-> (latlong->temperature {:latitude "41.5144", :longitude "-88.0608"})
      :temperature
      number?)
  ;; =>
  true

  (-> {:ip "198.29.213.3"}
      ip->lat-long
      latlong->temperature
      :temperature
      number?)
  ;; =>
  true

  )

(def env
  (pci/register [ip->lat-long
                 latlong->temperature]))

(comment

  (-> (p.eql/process env {:ip "198.29.213.3"} [:temperature])
      :temperature
      number?)
  ;; =>
  true

  )

(defn main
  [args]
  (let [temp (p.eql/process-one env args :temperature)]
    (println (str "It's currently " temp "C at " (pr-str args)))))

