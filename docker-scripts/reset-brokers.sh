#!/bin/sh
echo Resetting broker to new ip address, all at once!
docker-compose stop kafka-1 kafka-2 kafka-3
rm .env
cp .env.new .env
echo "Using env:"
cat .env

docker-compose up -d
echo Waiting for servers to be up...

for i in 1 2 3
do
    docker-compose logs -f kafka-$i | sed '/INFO.*KafkaServer id=.* started.*kafka.server.KafkaServer/ q' > /dev/null
    echo "kafka-$i up."
done
