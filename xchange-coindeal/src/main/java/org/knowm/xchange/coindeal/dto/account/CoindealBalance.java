package org.knowm.xchange.coindeal.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoindealBalance {

    @JsonProperty("currency")
    private final String currency;

    @JsonProperty("available")
    private final String available;

    @JsonProperty("reserved")
    private final String reserved;

    public CoindealBalance(
            @JsonProperty("currency") String currency,
            @JsonProperty("available") String available,
            @JsonProperty("reserved") String reserved) {
        this.currency = currency;
        this.available = available;
        this.reserved = reserved;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAvailable() {
        return available;
    }

    public String getReserved() {
        return reserved;
    }

    @Override
    public String toString() {
        return "CoindealBalance{" +
                "currency='" + currency + '\'' +
                ", available='" + available + '\'' +
                ", reserved='" + reserved + '\'' +
                '}';
    }
}
