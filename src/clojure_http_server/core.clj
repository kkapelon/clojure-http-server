(ns clojure-http-server.core
 (:require [clojure.string])
 (:import (java.net ServerSocket SocketException )
  (java.util Date)
  (java.io PrintWriter BufferedReader InputStreamReader BufferedOutputStream)))

(defn send-http-response 
 "Send data to client"
 [client-socket status content-type byte-content]
 (let
  [out-stream (.getOutputStream client-socket) out (new BufferedOutputStream out-stream) print-out (new PrintWriter out)]
  (do
   (.println print-out status)
   (.println print-out "Server: Clojure HTTP Server 1.0")
   (.println print-out (new Date))
   (.println print-out (str "Content-type: " content-type))
   (.println print-out (str "Content-length " (count byte-content)))
   (.println print-out)
   (.flush print-out)
   (.write out byte-content)
   (.flush out)
   (.close out-stream)
  )))

(defn send-html-response
 "Html response"
 [client-socket status title body]
 (let [html (str "<HTML><HEAD><TITLE>" title "</TITLE></HEAD><BODY>" body "</BODY></HTML>")]
  (send-http-response client-socket status "text/html" (.getBytes html "UTF-8"))
 ))


(defn get-reader
 "Create a Java reader from the input stream of the client socket"
 [client-socket]
 (new BufferedReader (new InputStreamReader (.getInputStream client-socket))))

(defn send-file
"Reads a file from the file system and writes it to the socket"
[client-socket http-method]
(println "correct"))

(defn process-request
 "Parse the HTTP request and decide what to do"
 [client-socket]
 (let [reader (get-reader client-socket) first-line (.readLine reader) tokens (clojure.string/split first-line #"\s+")]
  (let [http-method (clojure.string/upper-case (get tokens 0 "unknown"))]
   (if (or (= http-method "GET") (= http-method "HEAD"))
   (let [file-requested (get tokens 1 "not-existing")]
    (send-file client-socket http-method)
    )

    (do
     (send-html-response client-socket "HTTP/1.1 501 Not Implemented" "Not Implemented" (str "<h2>501 Not Implemented: " http-method " method.</h2>"))
     (println (str "501 Not Implemented: " http-method " method")))
   )

  )))

(defn respond-to-client 
 "A new http client is connected. Process the request" [client-socket]
 (do (println "A client has connected" (new Date))
  (process-request client-socket)
  (.close client-socket)
  (println "Connection closed")))


(defn -main
 "The main method that runs via lein run"
 []
 (let [port 8080 server-socket (new ServerSocket port)]
  (do 
   (println (str "Listening for connections on port " port))
   (while true (respond-to-client (.accept server-socket))))))


