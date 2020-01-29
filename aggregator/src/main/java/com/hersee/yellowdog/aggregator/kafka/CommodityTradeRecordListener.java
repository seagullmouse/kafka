package com.hersee.yellowdog.aggregator.kafka;

import com.hersee.yellowdog.aggregator.service.SummaryService;
import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listens for {@link CommodityTradeRecord} messages.
 *
 */
@Component
public class CommodityTradeRecordListener {
	private final SummaryService summaryService;

	public CommodityTradeRecordListener(SummaryService summaryService) {
		this.summaryService = summaryService;
	}

	/**
	 * Listen for {@link CommodityTradeRecord} messages.
	 *
	 * @param commodityTradeRecord The {@link CommodityTradeRecord}.
	 */
	@KafkaListener(topics = "${topic.record}", groupId = "${group}", containerFactory = "commodityTradeRecordKafkaListenerContainerFactory")
	public void listen(CommodityTradeRecord commodityTradeRecord) {
		if (commodityTradeRecord.isComplete()) {
			summaryService.sendSummaries();
			summaryService.clear();
		} else {
			summaryService.collect(commodityTradeRecord);
		}
	}
}
