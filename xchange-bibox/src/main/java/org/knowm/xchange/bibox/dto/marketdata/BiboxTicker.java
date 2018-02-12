package org.knowm.xchange.bibox.dto.marketdata;

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
    "buy",
    "high",
    "last",
    "low",
    "sell",
    "timestamp",
    "vol",
    "last_cny",
    "last_usd",
    "percent"
})
public class BiboxTicker {

    @JsonProperty("buy")
    private BigDecimal buy;
    @JsonProperty("high")
    private BigDecimal high;
    @JsonProperty("last")
    private BigDecimal last;
    @JsonProperty("low")
    private BigDecimal low;
    @JsonProperty("sell")
    private BigDecimal sell;
    @JsonProperty("timestamp")
    private long timestamp;
    @JsonProperty("vol")
    private BigDecimal vol;
    @JsonProperty("last_cny")
    private BigDecimal lastCny;
    @JsonProperty("last_usd")
    private BigDecimal lastUsd;
    // yes, I'm lazy
    @JsonProperty("percent")
    private String percent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The last
     */
    @JsonProperty("last")
    public BigDecimal getLast() {
        return last;
    }

    /**
     * 
     * @param last
     *     The last
     */
    @JsonProperty("last")
    public void setLast(BigDecimal last) {
        this.last = last;
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
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
     *     The lastCny
     */
    @JsonProperty("last_cny")
    public BigDecimal getLastCny() {
        return lastCny;
    }

    /**
     * 
     * @param lastCny
     *     The last_cny
     */
    @JsonProperty("last_cny")
    public void setLastCny(BigDecimal lastCny) {
        this.lastCny = lastCny;
    }

    /**
     * 
     * @return
     *     The lastUsd
     */
    @JsonProperty("last_usd")
    public BigDecimal getLastUsd() {
        return lastUsd;
    }

    /**
     * 
     * @param lastUsd
     *     The last_usd
     */
    @JsonProperty("last_usd")
    public void setLastUsd(BigDecimal lastUsd) {
        this.lastUsd = lastUsd;
    }

    /**
     * 
     * @return
     *     The percent
     */
    @JsonProperty("percent")
    public String getPercent() {
        return percent;
    }

    /**
     * 
     * @param percent
     *     The percent
     */
    @JsonProperty("percent")
    public void setPercent(String percent) {
        this.percent = percent;
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
