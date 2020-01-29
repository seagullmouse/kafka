package com.hersee.yellowdog.reader;

import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import com.hersee.yellowdog.common.model.CommodityTradeSummary;
import com.hersee.yellowdog.reader.kafka.CommodityTradeRecordSender;
import com.hersee.yellowdog.reader.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * CSV Reader Application (i.e. the producer application).
 *
 * <ol>
 *     <li>Reads a given CSV file containing Commodity Trade Statistics.</li>
 *     <li>Sends each line to the Aggregator Application via Kafka as a {@link CommodityTradeRecord} message.</li>
 *     <li>Sends a 'complete' {@link CommodityTradeRecord} message to the Aggregator Application via Kafka.</li>
 *     <li>Receives {@link CommodityTradeSummary} messages from the Aggregator Application via Kafka.</li>
 *     <li>Prints the {@link CommodityTradeSummary}s.</li>
 * </ol>
 *
 * @author seagullmouse
 */
@SpringBootApplication
@EnableKafka
public class CSVReaderApplication implements CommandLineRunner {
	final Logger logger = LoggerFactory.getLogger(CSVReaderApplication.class);
	private final FileService fileService;
	private final CommodityTradeRecordSender commodityTradeRecordSender;

	public CSVReaderApplication(FileService fileService, CommodityTradeRecordSender commodityTradeRecordSender) {
		this.fileService = fileService;
		this.commodityTradeRecordSender = commodityTradeRecordSender;
	}

	public static void main(String[] args) {
		SpringApplication.run(CSVReaderApplication.class, args);
	}

	/**
	 * Runs the application
	 *
	 * @param args The args as Strings.
	 * @throws Exception Thrown for all exceptions.
	 */
	@Override
	public void run(String... args) throws Exception {
		try {
			String fileName = args[0];
			File file = new File(fileName);

			fileService.readCSVFile(file, commodityTradeRecordSender::sendMessage);
		} catch (FileNotFoundException e) {
			logger.error("File not found");
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Missing filename argument");
		}
	}
}
