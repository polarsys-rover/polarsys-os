# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# Include modules in rootfs
IMAGE_INSTALL += " \
    dash \
    file \
    ldd \
    libstdc++ \
    screen \
    kernel-modules \
    rt-tests \
    stress \
    python-subprocess \
    python-argparse \
    python-json \
    mosquitto \
    libmosquitto1 \
    libmosquittopp1 \
    mosquitto-clients \
    userland \
    rtimulib \
    protobuf \
    i2c-tools \
"

TOOLCHAIN_HOST_TASK += "nativesdk-protobuf-compiler"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += " \
    ssh-server-dropbear \
    package-management \
    splash \
    eclipse-debug \
    tools-debug \
    tools-profile \
"

