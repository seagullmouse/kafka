package com.hersee.yellowdog.reader.kafka;

import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import com.hersee.yellowdog.common.model.CommodityTradeSummary;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {
	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${group}")
	private String groupId;

	public ConsumerFactory<String, CommodityTradeSummary> commodityTradeSummaryConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CommodityTradeSummary.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CommodityTradeSummary> commodityTradeSummaryKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, CommodityTradeSummary> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(commodityTradeSummaryConsumerFactory());
		return factory;
	}

	@Bean
	public ProducerFactory<String, CommodityTradeRecord> commodityTradeRecordProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, CommodityTradeRecord> commodityTradeRecordKafkaTemplate() {
		return new KafkaTemplate<>(commodityTradeRecordProducerFactory());
	}
}
