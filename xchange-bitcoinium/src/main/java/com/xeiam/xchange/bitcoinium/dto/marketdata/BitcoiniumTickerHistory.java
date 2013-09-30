
package com.xeiam.xchange.bitcoinium.dto.marketdata;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Ticker History from Bitcoinium Web Service
 * </p>
 */
public final class BitcoiniumTickerHistory {
    private final ArrayList<BigDecimal> pp;
    private final long t;
    private final ArrayList<BigDecimal> tt;

    public BitcoiniumTickerHistory(
            @JsonProperty("pp") ArrayList<BigDecimal> pp, 
            @JsonProperty("t") long t, 
            @JsonProperty("tt") ArrayList<BigDecimal> tt) {

        this.pp = pp;
        this.t = t;
        this.tt = tt;
    }

    public ArrayList<BigDecimal> getPriceHistoryList() {
        return this.pp;
    }

    public long getBaseTimestamp() {
        return this.t;
    }

    public ArrayList<BigDecimal> getTimeStampOffsets() {
        return this.tt;
    }

	@Override
	public String toString() {
		return "BitcoiniumTickerHistory [priceList=" + pp + ", timestamp=" + t + ", timeOffsets=" + tt
				+ "]";
	}

}
