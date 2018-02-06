
package org.knowm.xchange.kucoin.dto.marketdata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "coinType",
    "trading",
    "symbol",
    "lastDealPrice",
    "buy",
    "sell",
    "change",
    "coinTypePair",
    "sort",
    "feeRate",
    "volValue",
    "high",
    "datetime",
    "vol",
    "low",
    "changeRate"
})
public class KucoinTicker {

    @JsonProperty("coinType")
    private String coinType;
    @JsonProperty("trading")
    private Boolean trading;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("lastDealPrice")
    private BigDecimal lastDealPrice;
    @JsonProperty("buy")
    private BigDecimal buy;
    @JsonProperty("sell")
    private BigDecimal sell;
    @JsonProperty("change")
    private BigDecimal change;
    @JsonProperty("coinTypePair")
    private String coinTypePair;
    @JsonProperty("sort")
    private Integer sort;
    @JsonProperty("feeRate")
    private BigDecimal feeRate;
    @JsonProperty("volValue")
    private BigDecimal volValue;
    @JsonProperty("high")
    private BigDecimal high;
    @JsonProperty("datetime")
    private Long datetime;
    @JsonProperty("vol")
    private BigDecimal vol;
    @JsonProperty("low")
    private BigDecimal low;
    @JsonProperty("changeRate")
    private BigDecimal changeRate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinTicker() {
    }

    /**
     * 
     * @param coinType
     * @param trading
     * @param symbol
     * @param lastDealPrice
     * @param buy
     * @param sell
     * @param change
     * @param coinTypePair
     * @param sort
     * @param feeRate
     * @param volValue
     * @param high
     * @param datetime
     * @param vol
     * @param low
     * @param changeRate
     */
    public KucoinTicker(String coinType, Boolean trading, String symbol, BigDecimal lastDealPrice, BigDecimal buy, BigDecimal sell, BigDecimal change, String coinTypePair, Integer sort, BigDecimal feeRate, BigDecimal volValue, BigDecimal high, Long datetime, BigDecimal vol, BigDecimal low, BigDecimal changeRate) {
        super();
        this.coinType = coinType;
        this.trading = trading;
        this.symbol = symbol;
        this.lastDealPrice = lastDealPrice;
        this.buy = buy;
        this.sell = sell;
        this.change = change;
        this.coinTypePair = coinTypePair;
        this.sort = sort;
        this.feeRate = feeRate;
        this.volValue = volValue;
        this.high = high;
        this.datetime = datetime;
        this.vol = vol;
        this.low = low;
        this.changeRate = changeRate;
    }

    /**
     * 
     * @return
     *     The coinType
     */
    @JsonProperty("coinType")
    public String getCoinType() {
        return coinType;
    }

    /**
     * 
     * @param coinType
     *     The coinType
     */
    @JsonProperty("coinType")
    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    /**
     * 
     * @return
     *     The trading
     */
    @JsonProperty("trading")
    public Boolean getTrading() {
        return trading;
    }

    /**
     * 
     * @param trading
     *     The trading
     */
    @JsonProperty("trading")
    public void setTrading(Boolean trading) {
        this.trading = trading;
    }

    /**
     * 
     * @return
     *     The symbol
     */
    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    /**
     * 
     * @param symbol
     *     The symbol
     */
    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 
     * @return
     *     The lastDealPrice
     */
    @JsonProperty("lastDealPrice")
    public BigDecimal getLastDealPrice() {
        return lastDealPrice;
    }

    /**
     * 
     * @param lastDealPrice
     *     The lastDealPrice
     */
    @JsonProperty("lastDealPrice")
    public void setLastDealPrice(BigDecimal lastDealPrice) {
        this.lastDealPrice = lastDealPrice;
    }

    /**
     * 
     * @return
     *     The buy
     */
    @JsonProperty("buy")
    public BigDecimal getBuy() {
        return buy;
    }

    /**
     * 
     * @param buy
     *     The buy
     */
    @JsonProperty("buy")
    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    /**
     * 
     * @return
     *     The sell
     */
    @JsonProperty("sell")
    public BigDecimal getSell() {
        return sell;
    }

    /**
     * 
     * @param sell
     *     The sell
     */
    @JsonProperty("sell")
    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    /**
     * 
     * @return
     *     The change
     */
    @JsonProperty("change")
    public BigDecimal getChange() {
        return change;
    }

    /**
     * 
     * @param change
     *     The change
     */
    @JsonProperty("change")
    public void setChange(BigDecimal change) {
        this.change = change;
    }

    /**
     * 
     * @return
     *     The coinTypePair
     */
    @JsonProperty("coinTypePair")
    public String getCoinTypePair() {
        return coinTypePair;
    }

    /**
     * 
     * @param coinTypePair
     *     The coinTypePair
     */
    @JsonProperty("coinTypePair")
    public void setCoinTypePair(String coinTypePair) {
        this.coinTypePair = coinTypePair;
    }

    /**
     * 
     * @return
     *     The sort
     */
    @JsonProperty("sort")
    public Integer getSort() {
        return sort;
    }

    /**
     * 
     * @param sort
     *     The sort
     */
    @JsonProperty("sort")
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 
     * @return
     *     The feeRate
     */
    @JsonProperty("feeRate")
    public BigDecimal getFeeRate() {
        return feeRate;
    }

    /**
     * 
     * @param feeRate
     *     The feeRate
     */
    @JsonProperty("feeRate")
    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    /**
     * 
     * @return
     *     The volValue
     */
    @JsonProperty("volValue")
    public BigDecimal getVolValue() {
        return volValue;
    }

    /**
     * 
     * @param volValue
     *     The volValue
     */
    @JsonProperty("volValue")
    public void setVolValue(BigDecimal volValue) {
        this.volValue = volValue;
    }

    /**
     * 
     * @return
     *     The high
     */
    @JsonProperty("high")
    public BigDecimal getHigh() {
        return high;
    }

    /**
     * 
     * @param high
     *     The high
     */
    @JsonProperty("high")
    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    /**
     * 
     * @return
     *     The datetime
     */
    @JsonProperty("datetime")
    public Long getDatetime() {
        return datetime;
    }

    /**
     * 
     * @param datetime
     *     The datetime
     */
    @JsonProperty("datetime")
    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    /**
     * 
     * @return
     *     The vol
     */
    @JsonProperty("vol")
    public BigDecimal getVol() {
        return vol;
    }

    /**
     * 
     * @param vol
     *     The vol
     */
    @JsonProperty("vol")
    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }

    /**
     * 
     * @return
     *     The low
     */
    @JsonProperty("low")
    public BigDecimal getLow() {
        return low;
    }

    /**
     * 
     * @param low
     *     The low
     */
    @JsonProperty("low")
    public void setLow(BigDecimal low) {
        this.low = low;
    }

    /**
     * 
     * @return
     *     The changeRate
     */
    @JsonProperty("changeRate")
    public BigDecimal getChangeRate() {
        return changeRate;
    }

    /**
     * 
     * @param changeRate
     *     The changeRate
     */
    @JsonProperty("changeRate")
    public void setChangeRate(BigDecimal changeRate) {
        this.changeRate = changeRate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
