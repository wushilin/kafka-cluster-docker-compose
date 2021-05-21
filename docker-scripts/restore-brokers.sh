#!/bin/sh
echo Restoring brokers to original ips!
docker-compose stop kafka-1 kafka-2 kafka-3
rm .env
cp .env.old .env
echo "Using env:"
cat .env
docker-compose up -d
echo "Waiting for servers to be up..."
for i in 1 2 3
do
    docker-compose logs -f kafka-$i | tee /tmp/server-$i.log | sed '/INFO.*KafkaServer id=.* started.*kafka.server.KafkaServer/ q' > /dev/null
    echo "kafka-$i up."
done
