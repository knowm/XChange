package org.knowm.xchange.therock.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TheRockTrade {

    private final BigDecimal amount;
    private final long date;
    private final BigDecimal price;
    private final long tid;

    public TheRockTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long date, @JsonProperty("price") BigDecimal price,
                        @JsonProperty("tid") long tid) {

        this.amount = amount;
        this.date = date;
        this.price = price;
        this.tid = tid;
    }

    public BigDecimal getAmount() {

        return amount;
    }

    public long getDate() {

        return date;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public long getTid() {

        return tid;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("ItBitTrade [amount=");
        builder.append(amount);
        builder.append(", timestamp=");
        builder.append(date);
        builder.append(", date=");
        builder.append(price);
        builder.append(", tid=");
        builder.append(tid);
        builder.append("]");
        return builder.toString();
    }
}
