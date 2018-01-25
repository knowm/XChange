
package org.knowm.xchange.kucoin.dto.trading;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

import org.knowm.xchange.kucoin.dto.KucoinOrderType;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "createdAt",
    "amount",
    "dealValue",
    "dealPrice",
    "fee",
    "feeRate",
    "oid",
    "orderOid",
    "coinType",
    "coinTypePair",
    "direction",
    "dealDirection"
})
public class KucoinDealtOrder {

    @JsonProperty("createdAt")
    private Long createdAt;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("dealValue")
    private BigDecimal dealValue;
    @JsonProperty("dealPrice")
    private BigDecimal dealPrice;
    @JsonProperty("fee")
    private BigDecimal fee;
    @JsonProperty("feeRate")
    private BigDecimal feeRate;
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("orderOid")
    private String orderOid;
    @JsonProperty("coinType")
    private String coinType;
    @JsonProperty("coinTypePair")
    private String coinTypePair;
    @JsonProperty("direction")
    private KucoinOrderType direction;
    @JsonProperty("dealDirection")
    private KucoinOrderType dealDirection;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinDealtOrder() {
    }

    /**
     * 
     * @param coinType
     * @param createdAt
     * @param amount
     * @param dealValue
     * @param fee
     * @param coinTypePair
     * @param dealDirection
     * @param dealPrice
     * @param oid
     * @param feeRate
     * @param orderOid
     * @param direction
     */
    public KucoinDealtOrder(Long createdAt, BigDecimal amount, BigDecimal dealValue, BigDecimal dealPrice, BigDecimal fee, BigDecimal feeRate, String oid, String orderOid, String coinType, String coinTypePair, KucoinOrderType direction, KucoinOrderType dealDirection) {
        super();
        this.createdAt = createdAt;
        this.amount = amount;
        this.dealValue = dealValue;
        this.dealPrice = dealPrice;
        this.fee = fee;
        this.feeRate = feeRate;
        this.oid = oid;
        this.orderOid = orderOid;
        this.coinType = coinType;
        this.coinTypePair = coinTypePair;
        this.direction = direction;
        this.dealDirection = dealDirection;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("createdAt")
    public Long getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The createdAt
     */
    @JsonProperty("createdAt")
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The amount
     */
    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    @JsonProperty("amount")
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The dealValue
     */
    @JsonProperty("dealValue")
    public BigDecimal getDealValue() {
        return dealValue;
    }

    /**
     * 
     * @param dealValue
     *     The dealValue
     */
    @JsonProperty("dealValue")
    public void setDealValue(BigDecimal dealValue) {
        this.dealValue = dealValue;
    }

    /**
     * 
     * @return
     *     The dealPrice
     */
    @JsonProperty("dealPrice")
    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    /**
     * 
     * @param dealPrice
     *     The dealPrice
     */
    @JsonProperty("dealPrice")
    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    /**
     * 
     * @return
     *     The fee
     */
    @JsonProperty("fee")
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * 
     * @param fee
     *     The fee
     */
    @JsonProperty("fee")
    public void setFee(BigDecimal fee) {
        this.fee = fee;
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
     *     The oid
     */
    @JsonProperty("oid")
    public String getOid() {
        return oid;
    }

    /**
     * 
     * @param oid
     *     The oid
     */
    @JsonProperty("oid")
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * 
     * @return
     *     The orderOid
     */
    @JsonProperty("orderOid")
    public String getOrderOid() {
        return orderOid;
    }

    /**
     * 
     * @param orderOid
     *     The orderOid
     */
    @JsonProperty("orderOid")
    public void setOrderOid(String orderOid) {
        this.orderOid = orderOid;
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
     *     The direction
     */
    @JsonProperty("direction")
    public KucoinOrderType getDirection() {
        return direction;
    }

    /**
     * 
     * @param direction
     *     The direction
     */
    @JsonProperty("direction")
    public void setDirection(KucoinOrderType direction) {
        this.direction = direction;
    }

    /**
     * 
     * @return
     *     The dealDirection
     */
    @JsonProperty("dealDirection")
    public KucoinOrderType getDealDirection() {
        return dealDirection;
    }

    /**
     * 
     * @param dealDirection
     *     The dealDirection
     */
    @JsonProperty("dealDirection")
    public void setDealDirection(KucoinOrderType dealDirection) {
        this.dealDirection = dealDirection;
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
