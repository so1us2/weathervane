#!/bin/bash
# Copyright 2017-2019 VMware, Inc.
# SPDX-License-Identifier: BSD-2-Clause

sigterm()
{
   echo "signal TERM received pid = $pid."
   rm -f /fifo
   nodetool decommission
   nodetool stopdaemon
   exit 0
}

trap 'sigterm' TERM

echo "Update resolv.conf"
perl /updateResolveConf.pl

if [ $# -gt 0 ]; then
	eval "$* &"
else
	echo "starting cassandra"
	setsid /cassandra-init.sh &
fi

pid="$!"

sleep 15

tail -f /var/log/cassandra/* &

if [ ! -e "/fifo" ]; then
	mkfifo /fifo || exit
fi
chmod 400 /fifo

# wait indefinitely
while true;
do
  echo "Waiting for child to exit."
  read < /fifo
  echo "Child Exited"
done
