package org.knowm.xchange.coindeal.dto.trade;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "clientOrderId",
        "orderId",
        "symbol",
        "side",
        "quantity",
        "fee",
        "price",
        "timestamp"
})
public class CoindealTradeHistory {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("clientOrderId")
    private String clientOrderId;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("side")
    private String side;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("fee")
    private String fee;
    @JsonProperty("price")
    private String price;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("clientOrderId")
    public String getClientOrderId() {
        return clientOrderId;
    }

    @JsonProperty("clientOrderId")
    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("side")
    public String getSide() {
        return side;
    }

    @JsonProperty("side")
    public void setSide(String side) {
        this.side = side;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("fee")
    public String getFee() {
        return fee;
    }

    @JsonProperty("fee")
    public void setFee(String fee) {
        this.fee = fee;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "CoindealTradeHistory{" +
                "id=" + id +
                ", clientOrderId='" + clientOrderId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", side='" + side + '\'' +
                ", quantity='" + quantity + '\'' +
                ", fee='" + fee + '\'' +
                ", price='" + price + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}