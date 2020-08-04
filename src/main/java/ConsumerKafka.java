import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;

import static java.util.Collections.singletonList;

public class ConsumerKafka {

    public static void main(String[] args) throws InterruptedException {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(singletonList("FILA2"));
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                readMessage(records);
            }
            Thread.sleep(5000);
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ConsumerKafka.class.getSimpleName());
        return properties;
    }

    private static void readMessage(ConsumerRecords<String, String> records) {
        var objectMapper = new ObjectMapper();
        records.forEach(record -> {
            try {
                var message = objectMapper.readValue(record.value(), Message.class);
                message.setKey(record.key());
                System.out.println("key: " + message.getKey() + " message: " + message.getName());
            } catch (JsonProcessingException e) {
                // ignoring
                e.printStackTrace();
            }
        });
    }
}
