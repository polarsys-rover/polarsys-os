#!/bin/bash

cd rpi23-gen-image

PROXY=http://127.0.0.1:3142

RELEASE="stretch" \
RPI_MODEL=2 \
APT_INCLUDES="
    autoconf
    automake
    curl
    libtool
    build-essential
    libmosquittopp-dev
    libmosquitto-dev
    mosquitto
    mosquitto-clients
    pkg-config
    protobuf-compiler
    libprotobuf-dev
    libboost-program-options-dev
    libelf-dev
    git
    libglib2.0-dev
    libpopt-dev
    libdw-dev
    liburcu-dev
    uuid-dev
    libxml2-dev
    bison
    flex
    python3
    python
    vim
    lttng-tools
    liblttng-ust-dev
    " \
APT_PROXY=$PROXY \
BUILD_KERNEL=true \
KERNELSRC_DIR=../linux \
KERNELSRC_PREBUILT=true \
KERNEL_REMOVESRC=false \
HOSTNAME="rover" \
CHROOT_SCRIPTS="../scripts" \
./rpi23-gen-image.sh
