#!/bin/bash

cd rpi2-gen-image

PROXY=http://127.0.0.1:3142

APT_PROXY=$PROXY \
BUILD_KERNEL=true \
KERNELSRC_DIR=../linux \
KERNELSRC_PREBUILT=true \
HOSTNAME="polarsys-rover" \
./rpi2-gen-image.sh