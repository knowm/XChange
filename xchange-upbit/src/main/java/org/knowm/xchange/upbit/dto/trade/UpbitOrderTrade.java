package org.knowm.xchange.upbit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class UpbitOrderTrade {

    private final String marketId;

    private final String uuid;

    private final BigDecimal volume;

    private final BigDecimal price;

    private final BigDecimal funds;

    private final String side;

    public UpbitOrderTrade(@JsonProperty("market") String marketId, @JsonProperty("uuid") String uuid,
                           @JsonProperty("volume") BigDecimal volume, @JsonProperty("price") BigDecimal price,
                           @JsonProperty("funds") BigDecimal funds, @JsonProperty("side") String side) {
        this.marketId = marketId;
        this.side = side;
        this.volume = volume;
        this.price = price;
        this.funds = funds;
        this.uuid = uuid;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getUuid() {
        return uuid;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    public String getSide() {
        return side;
    }
}
