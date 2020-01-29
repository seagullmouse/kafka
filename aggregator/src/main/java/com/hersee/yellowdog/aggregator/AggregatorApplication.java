package com.hersee.yellowdog.aggregator;

import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import com.hersee.yellowdog.common.model.CommodityTradeSummary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Aggregator Application  (i.e. the consumer application).
 *
 * <ol>
 *     <li>Receives {@link CommodityTradeRecord} messages from the Reader Application via Kafka.</li>
 *     <li>Receives 'complete' message {@link CommodityTradeSummary} from the Reader Application via Kafka.</li>
 *     <li>Summarises the commodity trade data.</li>
 *     <li>Sends {@link CommodityTradeSummary} messages to the Reader Application via Kafka.</li>
 * </ol>
 *
 * @author seagullmouse
 */
@SpringBootApplication
@EnableKafka
public class AggregatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(AggregatorApplication.class, args);
	}
}
