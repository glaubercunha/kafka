package br.com.testes.kafka.aplication;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class NewOrderMain {
    

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties());
        String chave = UUID.randomUUID().toString();
		String value = chave + ",44412323,123,123GGGuuuuu";
		Callback callback = new Callback() {

			@Override
			public void onCompletion(RecordMetadata data, Exception e) {
				if (e != null) {
					e.printStackTrace();
				}	
				System.out.println("#######sucesso enviando " + data.topic() 
				+ " partition " + data.partition() + " offset " + data.offset());
		}};
        ProducerRecord<String, String> record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", chave, value);
        producer.send(record, callback).get();

        String email= "Compra realizada com sucesso!!";
        ProducerRecord<String, String> emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", email, email);
        producer.send(emailRecord, callback).get();
    }

	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}
}