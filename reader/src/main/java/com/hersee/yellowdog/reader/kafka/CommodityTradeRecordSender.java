package com.hersee.yellowdog.reader.kafka;

import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Sends {@link CommodityTradeRecord} messages.
 */
@Component
public class CommodityTradeRecordSender {
	private final KafkaTemplate<String, CommodityTradeRecord> commodityTradeRecordKafkaTemplate;

	@Value(value = "${topic.record}")
	private String topicName;

	public CommodityTradeRecordSender(KafkaTemplate<String, CommodityTradeRecord> commodityTradeRecordKafkaTemplate) {
		this.commodityTradeRecordKafkaTemplate = commodityTradeRecordKafkaTemplate;
	}

	/**
	 * Send a {@link CommodityTradeRecord} message.
	 *
	 * @param commodityTradeRecord The {@link CommodityTradeRecord}.
	 */
	public void sendMessage(CommodityTradeRecord commodityTradeRecord) {
		commodityTradeRecordKafkaTemplate.send(topicName, commodityTradeRecord);
	}
}
