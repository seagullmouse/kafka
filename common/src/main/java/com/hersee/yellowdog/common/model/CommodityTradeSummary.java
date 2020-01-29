package com.hersee.yellowdog.common.model;

/**
 * Represents a summary of commodity trade by country_or_area.
 */
@SuppressWarnings("unused")
public class CommodityTradeSummary {
	private String countryOrArea;
	private long importsUSD;
	private long exportsUSD;

	public CommodityTradeSummary() {
	}

	/**
	 * Constructor.
	 *
	 * @param countryOrArea The country or area.
	 * @param importsUSD    The imports in USD.
	 * @param exportsUSD    The exports in USD.
	 */
	public CommodityTradeSummary(String countryOrArea, long importsUSD, long exportsUSD) {
		this.countryOrArea = countryOrArea;
		this.importsUSD = importsUSD;
		this.exportsUSD = exportsUSD;
	}

	public String getCountryOrArea() {
		return countryOrArea;
	}

	public void setCountryOrArea(String countryOrArea) {
		this.countryOrArea = countryOrArea;
	}

	public long getImportsUSD() {
		return importsUSD;
	}

	public void setImportsUSD(long importsUSD) {
		this.importsUSD = importsUSD;
	}

	public long getExportsUSD() {
		return exportsUSD;
	}

	public void setExportsUSD(long exportsUSD) {
		this.exportsUSD = exportsUSD;
	}

	@Override
	public String toString() {
		return String.format("Country or area=\"%s\", imports=$%,d, exports=$%,d", countryOrArea, importsUSD, exportsUSD);
	}
}
