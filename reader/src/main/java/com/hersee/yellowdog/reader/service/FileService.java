package com.hersee.yellowdog.reader.service;

import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * Service to reading files and parsing content.
 */
@Component
public class FileService {
	public static final int COUNTRY_COLUMN_INDEX = 0;
	public static final int FLOW_COLUMN_INDEX = 4;
	public static final int TRADE_COLUMN_INDEX = 5;

	/**
	 * Read a given file and call the supplied {@link Consumer} for each row.
	 *
	 * @param file         The file.
	 * @param lineConsumer The line consumer.
	 * @throws Exception Thrown for all exceptions.
	 */
	public void readCSVFile(File file, Consumer<CommodityTradeRecord> lineConsumer) throws Exception {
		Reader reader = new FileReader(file);
		CSVReader csvReader = new CSVReader(reader);
		String[] line;
		int lineNumber = 1;

		// Read the file line by line, ignore the header line.
		while ((line = csvReader.readNext()) != null) {
			if (lineNumber > 1) {
				CommodityTradeRecord commodityTradeRecord = new CommodityTradeRecord(
						line[COUNTRY_COLUMN_INDEX],
						line[FLOW_COLUMN_INDEX],
						line[TRADE_COLUMN_INDEX]);
				lineConsumer.accept(commodityTradeRecord);
			}
			lineNumber++;
		}

		// Call the consumer with a 'complete' message.
		lineConsumer.accept(CommodityTradeRecord.createCompleteMessage());

		// Close the readers.
		reader.close();
		csvReader.close();
	}
}
