package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BybitBalance {

    private final String coin;
    private final String coinId;
    private final String coinName;
    private final String total;
    private final String free;
    private final String locked;

    @JsonCreator
    public BybitBalance(
            @JsonProperty("coin") String coin,
            @JsonProperty("coinId") String coinId,
            @JsonProperty("coinName") String coinName,
            @JsonProperty("total") String total,
            @JsonProperty("free") String free,
            @JsonProperty("locked") String locked
    ) {
        this.coin = coin;
        this.coinId = coinId;
        this.coinName = coinName;
        this.total = total;
        this.free = free;
        this.locked = locked;
    }

    public String getCoin() {
        return coin;
    }

    public String getCoinId() {
        return coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public String getTotal() {
        return total;
    }

    public String getFree() {
        return free;
    }

    public String getLocked() {
        return locked;
    }

    @Override
    public String toString() {
        return "BybitBalance{" +
                "coin='" + coin + '\'' +
                ", coinId='" + coinId + '\'' +
                ", coinName='" + coinName + '\'' +
                ", total='" + total + '\'' +
                ", free='" + free + '\'' +
                ", locked='" + locked + '\'' +
                '}';
    }
}
