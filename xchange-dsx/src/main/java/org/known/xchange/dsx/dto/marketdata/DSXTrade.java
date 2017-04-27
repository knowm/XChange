package org.known.xchange.dsx.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public final class DSXTrade {

    private final BigDecimal amount;
    private final BigDecimal price;
    private final long timestamp;
    private final long tid;
    private final String type;

    public DSXTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("price") BigDecimal price,
                    @JsonProperty("timestamp") long timestamp, @JsonProperty("tid") long tid,
                    @JsonProperty("type") String type) {

        this.amount = amount;
        this.price = price;
        this.timestamp = timestamp;
        this.tid = tid;
        this.type = type;
    }

    public BigDecimal getAmount() {

        return amount;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public long getTimestamp() {

        return timestamp;
    }

    public long getTid() {

        return tid;
    }

    public String getType() {

        return type;
    }

    @Override
    public String toString() {

        return "DSXTrade{" +
                "amount=" + amount +
                ", price=" + price +
                ", timestamp=" + timestamp +
                ", tid=" + tid +
                ", type='" + type + '\'' +
                '}';
    }

}
