package main;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

public class SampleProducer {
    public static void main(String[] args) {
        java.security.Security.setProperty(Constants.JVM_TTL_SETTING , "3");
        String topicName = "test-topic";
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-bootstrap-1:9092");
        props.put(ProducerConfig.CLIENT_DNS_LOOKUP_CONFIG, "use_all_dns_ips");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getCanonicalName());

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getCanonicalName());

        Producer<String, String> producer = new KafkaProducer
                <String, String>(props);
        AtomicLong sequence = new AtomicLong(0L);
        while(true) {
            try {
                String key = String.valueOf(sequence.addAndGet(1));
                String value = String.valueOf(sequence.addAndGet(1));
                System.out.println("Producing " + key + " => " + value);
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName,
                        key, value);
                try {
                    RecordMetadata sentData = producer.send(record).get();
                    System.out.printf("Sent to partition %d, offset %d\n", sentData.partition(), sentData.offset());
                } catch(Exception exe) {
                    System.out.println("Produce literally failed: " + exe.getMessage());
                }
                Thread.sleep(1000);
            } catch(Exception ex) {
                System.out.println("Produce error: " + ex.getMessage());
            }
        }

    }
}
