
package org.knowm.xchange.bitcoinde.trade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * @author kaiserfr
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "order_id",
    "trading_pair",
    "type",
    "max_amount",
    "min_amount",
    "price",
    "max_volume",
    "min_volume",
    "order_requirements",
    "new_order_for_remaining_amount",
    "state",
    "end_datetime",
    "created_at"
})
public class BitcoindeMyOrder {

    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("trading_pair")
    private String tradingPair;
    @JsonProperty("type")
    private String type;
    @JsonProperty("max_amount")
    private BigDecimal maxAmount;
    @JsonProperty("min_amount")
    private BigDecimal minAmount;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("max_volume")
    private Integer maxVolume;
    @JsonProperty("min_volume")
    private Integer minVolume;
    @JsonProperty("order_requirements")
    private BitcoindeOrderRequirements orderRequirements;
    @JsonProperty("new_order_for_remaining_amount")
    private Boolean newOrderForRemainingAmount;
    @JsonProperty("state")
    private Integer state;
    @JsonProperty("end_datetime")
    private String endDatetime;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BitcoindeMyOrder() {
    }

    /**
     * 
     * @param state
     * @param maxAmount
     * @param endDatetime
     * @param type
     * @param tradingPair
     * @param minAmount
     * @param price
     * @param orderRequirements
     * @param createdAt
     * @param maxVolume
     * @param newOrderForRemainingAmount
     * @param minVolume
     * @param orderId
     */
    public BitcoindeMyOrder(String orderId, String tradingPair, String type, BigDecimal maxAmount, BigDecimal minAmount, BigDecimal price, Integer maxVolume, Integer minVolume, BitcoindeOrderRequirements orderRequirements, Boolean newOrderForRemainingAmount, Integer state, String endDatetime, String createdAt) {
        super();
        this.orderId = orderId;
        this.tradingPair = tradingPair;
        this.type = type;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.price = price;
        this.maxVolume = maxVolume;
        this.minVolume = minVolume;
        this.orderRequirements = orderRequirements;
        this.newOrderForRemainingAmount = newOrderForRemainingAmount;
        this.state = state;
        this.endDatetime = endDatetime;
        this.createdAt = createdAt;
    }

    @JsonProperty("order_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("trading_pair")
    public String getTradingPair() {
        return tradingPair;
    }

    @JsonProperty("trading_pair")
    public void setTradingPair(String tradingPair) {
        this.tradingPair = tradingPair;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("max_amount")
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    @JsonProperty("max_amount")
    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    @JsonProperty("min_amount")
    public BigDecimal getMinAmount() {
        return minAmount;
    }

    @JsonProperty("min_amount")
    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    @JsonProperty("price")
    public BigDecimal getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonProperty("max_volume")
    public Integer getMaxVolume() {
        return maxVolume;
    }

    @JsonProperty("max_volume")
    public void setMaxVolume(Integer maxVolume) {
        this.maxVolume = maxVolume;
    }

    @JsonProperty("min_volume")
    public Integer getMinVolume() {
        return minVolume;
    }

    @JsonProperty("min_volume")
    public void setMinVolume(Integer minVolume) {
        this.minVolume = minVolume;
    }

    @JsonProperty("order_requirements")
    public BitcoindeOrderRequirements getOrderRequirements() {
        return orderRequirements;
    }

    @JsonProperty("order_requirements")
    public void setOrderRequirements(BitcoindeOrderRequirements orderRequirements) {
        this.orderRequirements = orderRequirements;
    }

    @JsonProperty("new_order_for_remaining_amount")
    public Boolean getNewOrderForRemainingAmount() {
        return newOrderForRemainingAmount;
    }

    @JsonProperty("new_order_for_remaining_amount")
    public void setNewOrderForRemainingAmount(Boolean newOrderForRemainingAmount) {
        this.newOrderForRemainingAmount = newOrderForRemainingAmount;
    }

    @JsonProperty("state")
    public Integer getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Integer state) {
        this.state = state;
    }

    @JsonProperty("end_datetime")
    public String getEndDatetime() {
        return endDatetime;
    }

    @JsonProperty("end_datetime")
    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
