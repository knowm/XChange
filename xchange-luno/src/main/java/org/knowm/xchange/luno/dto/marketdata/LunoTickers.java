package org.knowm.xchange.luno.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoTickers {

    private final LunoTicker[] tickers;

    public LunoTickers(@JsonProperty(value="tickers", required=true) LunoTicker[] tickers) {
        this.tickers = tickers != null ? tickers : new LunoTicker[0];
    }

    public LunoTicker[] getTickers() {
        LunoTicker[] copy = new LunoTicker[tickers.length];
        System.arraycopy(tickers, 0, copy, 0, tickers.length);
        return copy;
    }

    @Override
    public String toString() {
        return "LunoTickers [tickers(" + tickers.length + ")=" + Arrays.toString(tickers) + "]";
    }
}
