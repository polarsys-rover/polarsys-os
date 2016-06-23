SUMMARY = "Open source MQTT v3.1 implemention"
DESCRIPTION = "Mosquitto is an open source message broker that implements the MQ Telemetry Transport protocol version 3.1. MQTT provides a lightweight method of carrying out messaging using a publish/subscribe model."
HOMEPAGE = "http://mosquitto.org/"
SECTION = "console/network"

LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=62ddc846179e908dc0c8efec4a42ef20"

PR = "r0"

SRC_URI = "http://mosquitto.org/files/source/mosquitto-${PV}.tar.gz \
           file://mosquitto-server \
"

SRC_URI[md5sum] = "67943e2c5afebf7329628616eb2c41c5"
SRC_URI[sha256sum] = "1df3ae07de40b80a74cd37a7b026895c544cdd3b42c9e0719ae91623aa98c58b"

DEPENDS = "openssl util-linux python"

inherit cmake

do_compile() {
    oe_runmake PREFIX=/usr
}

do_install() {
    oe_runmake install DESTDIR=${D}
    install -d ${D}/${libdir}

    # delete the rpath added by mosquitto build
    # prevent "probably-redundant RPATH /usr/lib [useless-rpaths]"
    chrpath -d ${D}${libdir}/libmosquitto*.so
    chrpath -d ${D}${bindir}/mosquitto*
    chrpath -d ${D}${sbindir}/mosquitto

    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/mosquitto-server ${D}${sysconfdir}/init.d/

    # see below
    # install -m 0644 lib/libmosquitto.a ${D}${libdir}/
}

PACKAGES += "libmosquitto1 libmosquittopp1 ${PN}-clients ${PN}-python"

FILES_${PN} = "${sbindir}/mosquitto \
               ${bindir}/mosquitto_passwd \
               ${sysconfdir}/init.d/mosquitto-server \
               ${sysconfdir}/mosquitto/* \
"

FILES_libmosquitto1 = "${libdir}/libmosquitto.so.1*"

FILES_libmosquittopp1 = "${libdir}/libmosquittopp.so.1*"

FILES_${PN}-clients = "${bindir}/mosquitto_pub \
                       ${bindir}/mosquitto_sub \
"

# disabled, because not supported in cmake
# FILES_${PN}-staticdev += "${libdir}/libmosquitto.a"

FILES_${PN}-python = "/usr/lib/python2.7/site-packages"

inherit useradd update-rc.d
# mosquitto-server requires the mosquitto user
USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "mosquitto"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "mosquitto-server"
INITSCRIPT_PARAMS_${PN} = "defaults 9"


