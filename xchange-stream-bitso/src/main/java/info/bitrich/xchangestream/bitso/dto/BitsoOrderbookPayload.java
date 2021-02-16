package info.bitrich.xchangestream.bitso.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitsoOrderbookPayload {

    private final List<BitsoOrderBook> bids;
    private final List<BitsoOrderBook> asks;
    private final Long orderId;
    private final String amount;
    private final String rate;
    private final String value;
    private final String makerOrderId;
    private final String takerOrderId;
    private final String type;

    public BitsoOrderbookPayload(@JsonProperty("bids") List<BitsoOrderBook> bids,
                                 @JsonProperty("asks") List<BitsoOrderBook> asks,
                                 @JsonProperty("i") Long orderId,
                                 @JsonProperty("a") String amount,
                                 @JsonProperty("r") String rate,
                                 @JsonProperty("v") String value,
                                 @JsonProperty("mo") String makerOrderId,
                                 @JsonProperty("to") String takerOrderId,
                                 @JsonProperty("t") String type){
        this.bids=bids;
        this.asks=asks;
        this.orderId=orderId;
        this.amount=amount;
        this.rate=rate;
        this.value=value;
        this.makerOrderId=makerOrderId;
        this.takerOrderId=takerOrderId;
        this.type=type;
    }

    public List<BitsoOrderBook> getBids() {
        return bids;
    }

    public List<BitsoOrderBook> getAsks() {
        return asks;
    }

    @Override
    public String toString() {
        return "BitsoOrderbookPayload{" +
                "bids=" + bids +
                ", asks=" + asks +
                ", orderId=" + orderId +
                ", amount='" + amount + '\'' +
                ", rate='" + rate + '\'' +
                ", value='" + value + '\'' +
                ", makerOrderId='" + makerOrderId + '\'' +
                ", takerOrderId='" + takerOrderId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getAmount() {
        return amount;
    }

    public String getRate() {
        return rate;
    }

    public String getValue() {
        return value;
    }

    public String getMakerOrderId() {
        return makerOrderId;
    }

    public String getTakerOrderId() {
        return takerOrderId;
    }

    public String getType() {
        return type;
    }
}
