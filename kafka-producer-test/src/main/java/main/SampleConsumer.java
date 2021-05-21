package main;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class SampleConsumer {
    public static void main(String[] args) {
        java.security.Security.setProperty(Constants.JVM_TTL_SETTING , "3");
        String topicName = "test-topic";
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getCanonicalName());
        props.put(ConsumerConfig.CLIENT_DNS_LOOKUP_CONFIG, "use_all_dns_ips");

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getCanonicalName());

        Consumer<String, String> consumer = new KafkaConsumer
                <String, String>(props);
        consumer.subscribe(Arrays.asList(topicName));

        while (true) {
            try {
                @SuppressWarnings("deprecation")
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {

                    // print the offset,key and value for the consumer records.
                    System.out.printf("partition = %d, offset = %d, key = %s, value = %s\n",
                            record.partition(), record.offset(), record.key(), record.value());
                }
            } catch(Exception ex) {
                System.out.println("Consumer error: " + ex.getMessage());
            }
        }

    }
}
