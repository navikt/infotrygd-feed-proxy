FROM ghcr.io/navikt/baseimages/temurin:17

ENV APPD_ENABLED=true
ENV APP_NAME=infotrygd-feed-proxy

COPY ./target/infotrygd-feed-proxy.jar "app.jar"
