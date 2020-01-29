package com.hersee.yellowdog.aggregator.kafka;

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

	private ConsumerFactory<String, CommodityTradeRecord> commodityTradeRecordConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CommodityTradeRecord.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CommodityTradeRecord> commodityTradeRecordKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, CommodityTradeRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(commodityTradeRecordConsumerFactory());
		return factory;
	}

	@Bean
	public ProducerFactory<String, CommodityTradeSummary> commodityTradeSummaryProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, CommodityTradeSummary> commodityTradeSummaryKafkaTemplate() {
		return new KafkaTemplate<>(commodityTradeSummaryProducerFactory());
	}

}
