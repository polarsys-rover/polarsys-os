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

The config file `build/conf/local.conf` is added to the git repository and specifies the Raspberry Pi 2 by default. To build for a different version of the Raspberry Pi, specify it in the file `build/conf/local.inc`. This file should not be committed to the repository. You can base your local configuration on the file `local.inc.example`.

## Build the image

Building the image follow the standard Yocto procedure:

```shell
source poky/oe-init-build-env
bitbake polarsys-img
```

The image will be under `build/tmp/deploy/images/raspberrypi3`, and the latest image can be written to an sdcard with dd:
```shell
dd if=build/tmp/deploy/images/raspberrypi3/polarsys-img-raspberrypi3.rpi-sdimg of=/dev/sdX bs=1M
```

Replace the `raspberrypi3` with your current machine name (i.e. raspberrypi2) and /dev/sdX with your sdcard device. __WARNING__: double check your sdcard device to avoid formatting your main drive. Depending on the type of reader, the sddard may appear as /dev/sdX or /dev/mmc0blk. After inserting the sdcard, check dmesg to find the detected device.

Reboot the Raspberry Pi with the sdcard. The default user is root without password.

## Populate the SDK

Once you have the image running on your Raspberry Pi, you'll probably want to start writing awesome C/C++ applications that will run on it.  For this, you need the appropriate cross-compiler and setup with the root filesystem of the target.  It turns out that Yocto can generate an SDK tailor-made for our system.  This command will generate an installer for that SDK:

    $ bitbake polarsys-img -c populate_sdk

and this command will install it on our development machine.

    $ sudo tmp/deploy/sdk/poky-glibc-x86_64-polarsys-img-cortexa7hf-neon-vfpv4-toolchain-2.1+snapshot.sh

This will install the SDK by default in `/opt/poky/2.1+snapshot`.  You can also omit `sudo` and install it in a location to which your non-root user has write access.

As stated when it finishes installing, you need to source the environment setup script in order to start using the SDK:

    $ . /opt/poky/2.1+snapshot/environment-setup-cortexa7hf-neon-vfpv4-poky-linux-gnueabi

You can then happily do:

```
$ cat <<EOF > helloworld.c
#include <stdio.h>

int main(void) {
  printf("Hello, world!\n");
  return 0;
}
EOF
$ $CC $CFLAGS $LDFLAGS helloworld.c -o helloworld
$ file helloworld
helloworld: ELF 32-bit LSB executable, ARM, EABI5 version 1 (SYSV), dynamically linked, interpreter /lib/ld-linux-armhf.so.3, for GNU/Linux 2.6.32, BuildID[sha1]=7d90cff202bd28b61bb36cf2921257f0ee75a8d0, not stripped
```
CC, CFLAGS, LDFLAGS and a bunch of other environment variables are automatically set when sourcing the environment setup script.

## Contributing

* Add package to the image in `meta-polarsys/recipes-core/images/polarsys-img.bb`
* Add any required layer in `build/conf/bblayers.conf`
* Add any external repository as git submodule inside the top directory

