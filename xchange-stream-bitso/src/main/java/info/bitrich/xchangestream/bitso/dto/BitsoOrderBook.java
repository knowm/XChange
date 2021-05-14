package info.bitrich.xchangestream.bitso.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitsoOrderBook {

    private final String rate;
    private final String amount;
    private final String value;
    private final Integer side;
    private final Long timestamp;
    private final String orderId;
    private final String s;

    BitsoOrderBook(@JsonProperty("r") String rate,
                   @JsonProperty("a") String amount,
                   @JsonProperty("v") String value,
                   @JsonProperty("t") Integer side,
                   @JsonProperty("d") Long timestamp,
                   @JsonProperty("o") String orderId,
                   @JsonProperty("s") String s){
        this.rate=rate;
        this.amount=amount;
        this.value=value;
        this.side=side;
        this.timestamp=timestamp;
        this.orderId=orderId;
        this.s=s;
    }

    public String getRate() {
        return rate;
    }

    public String getAmount() {
        return amount;
    }

    public String getValue() {
        return value;
    }

    public Integer getSide() {
        return side;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "BitsoOrderBook{" +
                "rate='" + rate + '\'' +
                ", amount='" + amount + '\'' +
                ", value='" + value + '\'' +
                ", side=" + side +
                ", timestamp=" + timestamp +
                ", orderId='" + orderId + '\'' +
                ", s='" + s + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public String getS() {
        return s;
    }

}
