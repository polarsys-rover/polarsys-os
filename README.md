PolarSys Operating System
=========================

[PolarSys](https://www.polarsys.org/) is an Eclipse Industry Working
Group created by large industry players and by tools providers to
collaborate on the creation and support of Open Source tools for the
development of embedded systems.

This repository contains scripts and documentation to build the
operating system for the rover robot based on the Raspberry Pi.  It is
currently based on the Debian Linux distribution (Raspbian).  The image
includes libraries and tools for demonstration purposes and has a
preempt-rt kernel by default.

The build procedure was tested on Ubuntu 16.04, but could probably be
adapted to other version/distros.

## Initial setup

The build scripts have a few prerequisites:

    $ sudo apt-get install \
        debootstrap \
        qemu-user-static \
        binfmt-support \
        bmap-tools \
        whois \
        crossbuild-essential-armhf \
        bc

This repository references other repositories through git submodules.
You can checkout all of them using the `--recursive` flag when cloning:

    $ git clone --recursive https://github.com/polarsys-rover/polarsys-os.git

or, if you have the `polarsys-os` repository already cloned:

    $ git submodule update
    $ git submodule init


## Building

The image is built in two steps: building the kernel, and assembling the
actual SD card image.

### Building the kernel

To build the kernel, simply run the `polarsys-build-kernel.sh` script:

    $ ./polarsys-build-kernel.sh

The build artifacts will be in the `linux` subdirectory, and will
automatically be picked up by the next step.

### Building the image

To build the image, run the `polarsys-build-image.sh` as root:

    $ sudo ./polarsys-build-image.sh

If everything goes right, the resulting image will be found in the
`rpi23-gen-image/images/jessie` subfolder.

### Flashing the image

To copy the image on an SD card, you can use either dd or bmaptool.

    $ sudo bmaptool copy rpi23-gen-image/images/jessie/<date>-debian-jessie.img <SD card device>

or

    $ sudo dd if=rpi23-gen-image/images/jessie/<date>-debian-jessie.img of=<SD card device>

## Booting the image

The default username is `pi` and the default password is `raspberry`.
