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

    @JsonProperty("clientOrderId")
    public String getClientOrderId() {
        return clientOrderId;
    }

    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
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