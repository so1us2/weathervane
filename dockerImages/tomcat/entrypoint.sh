#!/bin/bash
# Copyright 2017-2019 VMware, Inc.
# SPDX-License-Identifier: BSD-2-Clause

sigterm()
{
   echo "signal TERM received."
   rm -f /fifo
   /opt/apache-tomcat/bin/shutdown.sh -force
   cat /opt/apache-tomcat-auction1/logs/*
   rm -f /opt/apache-tomcat-auction1/logs/*
   exit 0
}

sigusr1()
{
    echo "signal USR1 received. start stats collection"
    cp /opt/apache-tomcat-auction1/logs/gc.log /opt/apache-tomcat-auction1/logs/gc_rampup.log
}

trap 'sigterm' TERM
trap 'sigusr1' USR1

perl /updateResolveConf.pl

# Generate a keystore for tomcat
./generateCert.sh

rm -f /opt/apache-tomcat-auction1/logs/* 
perl /configure.pl

if [ $# -gt 0 ]; then
	eval "$* &"
else
    setsid /opt/apache-tomcat/bin/startup.sh  &
fi

if [ ! -e "/fifo" ]; then
	mkfifo /fifo || exit
fi
chmod 400 /fifo

sleep 30;
tail -f /opt/apache-tomcat-auction1/logs/* &

# wait indefinitely
while true;
do
  echo "Waiting for child to exit."
  read < /fifo
  echo "Child Exited"
done
