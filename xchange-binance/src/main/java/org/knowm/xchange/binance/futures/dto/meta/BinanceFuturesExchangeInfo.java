package org.knowm.xchange.binance.futures.dto.meta;

import org.knowm.xchange.binance.dto.meta.exchangeinfo.RateLimit;

import java.util.Arrays;

public class BinanceFuturesExchangeInfo {
    private BinanceFuturesAsset[] assets;
    private String[] exchangeFilters;
    private String futuresType;
    private RateLimit[] rateLimits;
    private long serverTime;
    private FuturesSymbol[] symbols;
    private String timezone;

    public BinanceFuturesAsset[] getAssets() {
        return assets;
    }

    public void setAssets(BinanceFuturesAsset[] assets) {
        this.assets = assets;
    }

    public String[] getExchangeFilters() {
        return exchangeFilters;
    }

    public void setExchangeFilters(String[] exchangeFilters) {
        this.exchangeFilters = exchangeFilters;
    }

    public String getFuturesType() {
        return futuresType;
    }

    public void setFuturesType(String futuresType) {
        this.futuresType = futuresType;
    }

    public RateLimit[] getRateLimits() {
        return rateLimits;
    }

    public void setRateLimits(RateLimit[] rateLimits) {
        this.rateLimits = rateLimits;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public FuturesSymbol[] getSymbols() {
        return symbols;
    }

    public void setSymbols(FuturesSymbol[] symbols) {
        this.symbols = symbols;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BinanceFuturesExchangeInfo{");
        sb.append("assets=").append(Arrays.toString(assets));
        sb.append(", exchangeFilters=").append(Arrays.toString(exchangeFilters));
        sb.append(", futuresType='").append(futuresType).append('\'');
        sb.append(", rateLimits=").append(Arrays.toString(rateLimits));
        sb.append(", serverTime=").append(serverTime);
        sb.append(", symbols=").append(Arrays.toString(symbols));
        sb.append(", timezone='").append(timezone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
