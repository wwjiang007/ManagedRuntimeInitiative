#!/bin/sh

#
# Copyright 2002 Sun Microsystems, Inc.  All Rights Reserved.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# This code is free software; you can redistribute it and/or modify it
# under the terms of the GNU General Public License version 2 only, as
# published by the Free Software Foundation.
#
# This code is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
# version 2 for more details (a copy is included in the LICENSE file that
# accompanied this code).
#
# You should have received a copy of the GNU General Public License version
# 2 along with this work; if not, write to the Free Software Foundation,
# Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
#
# Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
# CA 95054 USA or visit www.sun.com if you need additional information or
# have any questions.
#


# @test
# @bug 4773521
# @library ../../../../
# @build Lookup TestEnv
# @run shell lookup.sh
# @summary Test that reverse lookups of IPv4 addresses work when IPv6
#          is enabled. 

# The host that we try to resolve

CLASSPATH=${TESTCLASSES}
export CLASSPATH
JAVA="${TESTJAVA}/bin/java"

HOST=`$JAVA TestEnv -get host`

# First check that host resolves to IPv4 address.

echo ''
ADDR=`$JAVA -Djava.net.preferIPv4Stack=true Lookup -q=A $HOST`
if [ $? != 0 ]; then
    echo "$HOST can't be resolved - test skipped."
    exit 0
fi
echo "$HOST --> $ADDR"


# IPv4 reverse lookup
echo ''
OUT1=`$JAVA -Djava.net.preferIPv4Stack=true Lookup -q=PTR $ADDR`
echo "(IPv4) $ADDR --> $OUT1"


# reverse lookup (default)
echo ''
OUT2=`$JAVA Lookup -q=PTR $ADDR`
echo "(default) $ADDR --> $OUT2"


# Compare results
if [ "$OUT1" != "$OUT2" ]; then
    echo ''
    echo "Mistmatch between default and java.net.preferIPv4Stack=true results"
    exit 1
fi
