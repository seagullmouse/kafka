package com.hersee.yellowdog.aggregator.kafka;

import com.hersee.yellowdog.common.model.CommodityTradeSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Sends {@link CommodityTradeSummary} messages.
 *
 */
@Component
public class CommodityTradeSummarySender {
	private final KafkaTemplate<String, CommodityTradeSummary> commodityTradeSummaryKafkaTemplate;

	@Value(value = "${topic.summary}")
	private String topicName;

	public CommodityTradeSummarySender(KafkaTemplate<String, CommodityTradeSummary> commodityTradeSummaryKafkaTemplate) {
		this.commodityTradeSummaryKafkaTemplate = commodityTradeSummaryKafkaTemplate;
	}

	/**
	 * Send a {@link CommodityTradeSummary} message.
	 *
	 * @param commodityTradeSummary The {@link CommodityTradeSummary}.
	 */
	public void sendMessage(CommodityTradeSummary commodityTradeSummary) {
		commodityTradeSummaryKafkaTemplate.send(topicName, commodityTradeSummary);
	}
}
