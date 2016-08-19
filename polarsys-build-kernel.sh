#!/bin/bash

polarsys_basepath=$(readlink -f "$BASH_SOURCE[0]")
polarsys_basepath=$(dirname $polarsys_basepath)
PATH="$polarsys_basepath/raspberrypi-tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin/:$PATH"

MAKE="make V=1 -C linux ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf-"

echo "Using compiler $(which arm-linux-gnueabihf-gcc)"

# linux-defconfig was generated using the kernel's "make savedefconfig"
cp linux-defconfig linux/.config

$MAKE olddefconfig
$MAKE zImage modules dtbs
