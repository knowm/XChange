package org.knowm.xchange.binance.dto.account.futures;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinancePosition {

    private final String symbol;

    private final BigDecimal unrealizedProfit;

    private final BigDecimal leverage;

    private final boolean isolated;

    private final String positionSide;

    private final BigDecimal positionAmt;

    private final BigDecimal entryPrice;

    public BinancePosition(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("unrealizedProfit") BigDecimal unrealizedProfit,
            @JsonProperty("leverage") BigDecimal leverage,
            @JsonProperty("isolated") boolean isolated,
            @JsonProperty("positionSide") String positionSide,
            @JsonProperty("positionAmt") BigDecimal positionAmt,
            @JsonProperty("entryPrice") BigDecimal entryPrice) {
        this.symbol = symbol;
        this.unrealizedProfit = unrealizedProfit;
        this.leverage = leverage;
        this.isolated = isolated;
        this.positionSide = positionSide;
        this.positionAmt = positionAmt;
        this.entryPrice = entryPrice;
    }
}
