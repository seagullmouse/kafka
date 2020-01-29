package com.hersee.yellowdog.common.model;

/**
 * Represents a record in the Commodity Trade Statistics CSV.
 */
@SuppressWarnings("unused")
public class CommodityTradeRecord {
	private String countryOrArea;
	private Flow flow;
	private long tradeUSD;
	private boolean complete = false;

	public CommodityTradeRecord() {
	}

	/**
	 * Constructor.
	 *
	 * @param countryOrArea The country or area.
	 * @param flow          The flow.
	 * @param tradeUSD      The trade in USD.
	 */
	public CommodityTradeRecord(String countryOrArea, Flow flow, long tradeUSD) {
		this.countryOrArea = countryOrArea;
		this.flow = flow;
		this.tradeUSD = tradeUSD;
	}

	/**
	 * Constructor from String values.
	 *
	 * @param countryOrArea  The country or area String.
	 * @param flowString     The flow String.
	 * @param tradeUSDString The trade in USD String.
	 */
	public CommodityTradeRecord(String countryOrArea, String flowString, String tradeUSDString) {
		Flow flow;
		switch (flowString) {
			case "Import":
			case "Re-Import":
				flow = Flow.IMPORT;
				break;
			case "Export":
			case "Re-Export":
				flow = Flow.EXPORT;
				break;
			default:
				throw new IllegalStateException("Unexpected flow value: " + flowString);
		}
		this.countryOrArea = countryOrArea;
		this.flow = flow;
		this.tradeUSD = Long.parseLong(tradeUSDString);
	}

	/**
	 * Create a 'complete' message that can be sent on the same Kafka topic. Signifies that there are no more {@link CommodityTradeRecord}s
	 * to be sent.
	 *
	 * @return The 'complete' message.
	 */
	public static CommodityTradeRecord createCompleteMessage() {
		CommodityTradeRecord commodityTradeRecord = new CommodityTradeRecord();
		commodityTradeRecord.setComplete(true);
		return commodityTradeRecord;
	}

	public String getCountryOrArea() {
		return countryOrArea;
	}

	public void setCountryOrArea(String countryOrArea) {
		this.countryOrArea = countryOrArea;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public long getTradeUSD() {
		return tradeUSD;
	}

	public void setTradeUSD(long tradeUSD) {
		this.tradeUSD = tradeUSD;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public String toString() {
		return "CommodityTradeRecord{" +
			   "countryOrArea='" + countryOrArea + '\'' +
			   ", flow=" + flow +
			   ", tradeUSD=" + tradeUSD +
			   ", complete=" + complete +
			   '}';
	}
}
