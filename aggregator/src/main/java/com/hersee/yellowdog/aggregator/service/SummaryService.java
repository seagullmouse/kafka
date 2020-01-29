package com.hersee.yellowdog.aggregator.service;

import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import com.hersee.yellowdog.common.model.CommodityTradeSummary;
import com.hersee.yellowdog.common.model.Flow;
import com.hersee.yellowdog.aggregator.kafka.CommodityTradeSummarySender;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class SummaryService {
	final CommodityTradeSummarySender commodityTradeSummarySender;

	Map<String, CommodityTradeSummary> countryToCommodityTradeSummary = new TreeMap<>();

	public SummaryService(CommodityTradeSummarySender commodityTradeSummarySender) {
		this.commodityTradeSummarySender = commodityTradeSummarySender;
	}

	public void collect(CommodityTradeRecord commodityTradeRecord) {
		CommodityTradeSummary commodityTradeSummary = countryToCommodityTradeSummary.get(commodityTradeRecord.getCountryOrArea());

		if (commodityTradeSummary == null) {
			if (commodityTradeRecord.getFlow() == Flow.IMPORT) {
				countryToCommodityTradeSummary
						.put(commodityTradeRecord.getCountryOrArea(),
								new CommodityTradeSummary(commodityTradeRecord.getCountryOrArea(), commodityTradeRecord.getTradeUSD(), 0L));
			} else {
				countryToCommodityTradeSummary
						.put(commodityTradeRecord.getCountryOrArea(),
								new CommodityTradeSummary(commodityTradeRecord.getCountryOrArea(), 0L, commodityTradeRecord.getTradeUSD()));
			}

		} else {
			if (commodityTradeRecord.getFlow() == Flow.IMPORT) {
				commodityTradeSummary.setImportsUSD(commodityTradeSummary.getImportsUSD() + commodityTradeRecord.getTradeUSD());
			} else {
				commodityTradeSummary.setExportsUSD(commodityTradeSummary.getExportsUSD() + commodityTradeRecord.getTradeUSD());
			}
		}
	}

	public void sendSummaries() {
		countryToCommodityTradeSummary
				.values()
				.forEach(commodityTradeSummarySender::sendMessage);
	}

	public void clear() {
		countryToCommodityTradeSummary = new TreeMap<>();
	}
}
