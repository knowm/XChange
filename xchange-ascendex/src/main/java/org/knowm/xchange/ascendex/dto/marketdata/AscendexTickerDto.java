package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.dto.marketdata
 * Description:
 *
 * @date:2022/7/14 18:07
 * @author:wodepig
 */
public class AscendexTickerDto {
    private   String	symbol;
    private   BigDecimal	open;
    private   BigDecimal	close;
    private   BigDecimal	high;
    private   BigDecimal	low;
    private   BigDecimal	volume;
    private   List<BigDecimal> ask;
    private   List<BigDecimal>	bid;


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public void setAsk(List<BigDecimal> ask) {
        this.ask = ask;
    }

    public void setBid(List<BigDecimal> bid) {
        this.bid = bid;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public List<BigDecimal> getAsk() {
        return ask;
    }

    public List<BigDecimal> getBid() {
        return bid;
    }

    @Override
    public String toString() {
        return "AscendexTickerDto{" +
                "symbol='" + symbol + '\'' +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", volume=" + volume +
                ", ask=" + ask +
                ", bid=" + bid +
                '}';
    }
}
