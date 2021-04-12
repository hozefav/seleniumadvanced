FROM ubuntu:focal

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y wget software-properties-common sudo

RUN wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | apt-key add - && \
    add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/ && \
    apt-get update && \
    apt-get install -y adoptopenjdk-15-hotspot maven gradle curl git

RUN useradd -ms /bin/bash dev && \
    adduser dev sudo && \
    echo "dev ALL=(ALL) NOPASSWD:ALL" | tee /etc/sudoers

USER dev