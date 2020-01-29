package com.hersee.yellowdog.reader.kafka;

import com.hersee.yellowdog.common.model.CommodityTradeSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listens for {@link CommodityTradeSummary} messages.
 */
@Component
public class CommodityTradeSummaryListener {
	final Logger logger = LoggerFactory.getLogger(CommodityTradeSummaryListener.class);

	/**
	 * Listen for {@link CommodityTradeSummary} messages.
	 *
	 * @param commodityTradeSummary The {@link CommodityTradeSummary}.
	 */
	@KafkaListener(topics = "${topic.summary}", groupId = "${group}", containerFactory = "commodityTradeSummaryKafkaListenerContainerFactory")
	public void listen(CommodityTradeSummary commodityTradeSummary) {
		logger.info("{}", commodityTradeSummary);
	}
}
