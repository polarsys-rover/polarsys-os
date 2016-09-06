#!/bin/bash

cd rpi23-gen-image

PROXY=http://127.0.0.1:3142

RPI_MODEL=2 \
APT_INCLUDES=" \
    build-essential \
    libmosquittopp-dev \
    libmosquitto-dev \
    pkg-config \
    protobuf-compiler \
    libprotobuf-dev \
    libboost-program-options-dev \
    " \
APT_PROXY=$PROXY \
BUILD_KERNEL=true \
KERNELSRC_DIR=../linux \
KERNELSRC_PREBUILT=true \
HOSTNAME="polarsys-rover" \
./rpi23-gen-image.sh
