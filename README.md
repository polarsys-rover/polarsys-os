PolarSys Operating System
=========================

[PolarSys](https://www.polarsys.org/) is an Eclipse Industry Working Group created by large industry players and by tools providers to collaborate on the creation and support of Open Source tools for the development of embedded systems.

This repository contains the Yocto configuration to build the operating system for the rover robot based on the Raspberry Pi. The image includes libraries and tools for demonstration purposes and has a preempt-rt kernel by default.

## Initial setup

The Yocto configuration requires a recent Linux build host and about 70GB of storage. The build requires a file system supporting long file names, such as EXT4. It is not compatible with FUSE ecryptfs, a popular encrypted file system for home directories.

The build uses Yocto layers from other repositories. To simplify the setup, the external layers are referenced as git submodules. The first step after checkout is to initialize the submodules:

```shell
cd polarsys-os/
git submodule init
git submodule update
```

Each time a submodule changes, re-run `git submodule update` to synchronize its content.

## Set the Raspberry Pi version

The config file `build/conf/local.conf` is added to the git repository and specifies the Raspberry Pi 2 by default. To build for a different version of the Raspberry Pi, specify it in the file `build/conf/local.inc`

## Build the image

Building the image follow the standard Yocto procedure:

```shell
source poky/oe-init-build-env
bitbake rpi-basic-image
```

The image will be under `build/tmp/deploy/images/raspberrypi3`, and the latest image can be written to an sdcard with dd:
```shell
dd if=build/tmp/deploy/images/raspberrypi3/polarsys-img-raspberrypi3.rpi-sdimg of=/dev/sdX bs=1M
```

Replace the `raspberrypi3` with your current machine name (i.e. raspberrypi2).

Reboot the Raspberry Pi with the sdcard. The default user is root without password.

## Contributing

* Add package to the image in `meta-polarsys/recipes-core/images/polarsys-img.bb`
* Add any required layer in `build/conf/bblayers.conf`
* Add any external repository as git submodule inside the top directory

