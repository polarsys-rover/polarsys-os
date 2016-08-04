#!/bin/bash

MAKE="make -C linux ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf-"

# linux-defconfig was generated using the kernel's "make savedefconfig"
cp linux-defconfig linux/.config

$MAKE olddefconfig
$MAKE zImage modules dtbs
