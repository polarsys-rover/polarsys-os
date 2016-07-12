DESCRIPTION = "WebSocket Library"
HOMEPAGE = "http://nohomepage.org"
SECTION = "System/Libraries"
LICENSE = "LGPL-2.1"

SRC_URI = "https://github.com/warmcat/libwebsockets/archive/v${PV}.tar.gz"

LIC_FILES_CHKSUM = "file://LICENSE;md5=f197d69f6bda1c450e2173a161286269"

DEPENDS += "zlib"
DEPENDS += "openssl"

FILES_${PN}-dev += "${libdir}/cmake"
FILES_${PN}-doc += "${datadir}/libwebsockets-test-server"

inherit cmake
