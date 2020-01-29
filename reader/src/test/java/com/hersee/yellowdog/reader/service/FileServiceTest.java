package com.hersee.yellowdog.reader.service;

import com.hersee.yellowdog.common.model.CommodityTradeRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Objects;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestApplicationConfiguration.class)
public class FileServiceTest {
	@Autowired
	private FileService fileService;

	@Test
	public void whenReadCSVFile_with1000Lines_thenSend1000Messages() throws Exception {
		// Given
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("commodity_trade_statistics_data_1000.csv")).getFile());
		Consumer<CommodityTradeRecord> mockConsumer = mock(Consumer.class);

		// When
		fileService.readCSVFile(file, mockConsumer);

		// Then
		verify(mockConsumer, times(1000)).accept(any());
	}
}
