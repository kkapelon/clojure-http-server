FROM clojure:lein-2.9.1 AS LEIN_TOOL_CHAIN
COPY . /tmp/app-src/
WORKDIR /tmp/app-src/
RUN lein uberjar

FROM eclipse-temurin:11-jre-alpine

EXPOSE 8080

RUN apk add --no-cache ca-certificates bash

RUN mkdir /app

COPY index.html /app
COPY --from=LEIN_TOOL_CHAIN /tmp/app-src/target/uberjar/clojure-http-server.jar /app/clojure-http-server.jar

WORKDIR /app

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./clojure-http-server.jar"]