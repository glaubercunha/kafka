package br.com.testes.kafka.aplication;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class FraudDetectorService {

	public static void main(String[] args) {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties());
		consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER2"));
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			if (!records.isEmpty()) {
				System.out.println("Encontrei " + records.count() + " registros");
				for (ConsumerRecord<String, String> record : records) {
					System.out.println("----------------------");
					System.out.println("Cheking for fraud");
					System.out.println(record.key());
					System.out.println(record.value());
					System.out.println(record.partition());
					System.out.println(record.offset());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("Order processed");
			}
		}
	}

	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getName());
		return properties;
	}
}