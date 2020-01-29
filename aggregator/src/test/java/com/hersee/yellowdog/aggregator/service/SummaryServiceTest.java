package com.hersee.yellowdog.aggregator.service;

import com.hersee.yellowdog.aggregator.kafka.CommodityTradeSummarySender;
import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import com.hersee.yellowdog.common.model.Flow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = TestApplicationConfiguration.class)
class SummaryServiceTest {
	@Autowired
	private SummaryService summaryService;
	@MockBean
	private CommodityTradeSummarySender commodityTradeSummarySender;

	@BeforeEach
	public void clear(){
		summaryService.clear();
	}

	@Test
	public void whenSendSummaries_withSingleCountry_thenSendSingleSummary() {
		// When
		summaryService.collect(new CommodityTradeRecord("Country 1", Flow.IMPORT, 100L));
		summaryService.sendSummaries();

		// Then
		verify(commodityTradeSummarySender, times(1)).sendMessage(any());
	}

	@Test
	public void whenSendSummaries_withMultipleCountries_thenSendMultipleSummaries() {
		// When
		summaryService.collect(new CommodityTradeRecord("Country 1", Flow.IMPORT, 100L));
		summaryService.collect(new CommodityTradeRecord("Country 2", Flow.IMPORT, 100L));
		summaryService.sendSummaries();

		// Then
		verify(commodityTradeSummarySender, times(2)).sendMessage(any());
	}

	@Test
	public void whenSendSummaries_withMultipleEntriesForCountry_thenSendSingleSummary() {
		// When
		summaryService.collect(new CommodityTradeRecord("Country 1", Flow.IMPORT, 100L));
		summaryService.collect(new CommodityTradeRecord("Country 1", Flow.IMPORT, 100L));
		summaryService.sendSummaries();

		// Then
		verify(commodityTradeSummarySender, times(1)).sendMessage(any());
	}

	@Test
	public void whenSendSummaries_withMultipleEntriesForMultipleCountries_thenSendMultipleSummaries() {
		// When
		summaryService.collect(new CommodityTradeRecord("Country 1", Flow.IMPORT, 100L));
		summaryService.collect(new CommodityTradeRecord("Country 1", Flow.IMPORT, 100L));
		summaryService.collect(new CommodityTradeRecord("Country 2", Flow.IMPORT, 100L));
		summaryService.collect(new CommodityTradeRecord("Country 2", Flow.IMPORT, 100L));
		summaryService.sendSummaries();

		// Then
		verify(commodityTradeSummarySender, times(2)).sendMessage(any());
	}
}
