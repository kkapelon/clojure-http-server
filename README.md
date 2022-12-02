# Simple HTTP server written in Clojure

A simple file based HTTP server in Clojure created for my [Clojure blog post](https://blog.codepipes.com/containers/clojure-lein-docker.html)


It exposes 8080 and serves all file requests for files in the same
directory as the executable. Currently only an example index.html is provided


## How to run locally

`lein run`

then run in a different terminal `curl localhost:8080/index.html`

## How to build and run container

Run

 *  `docker build . -t my-app` to create a container image 
 *  `docker run -p 8080:8080 my-app` to run it

then run in a different terminal `curl localhost:8080/index.html`



