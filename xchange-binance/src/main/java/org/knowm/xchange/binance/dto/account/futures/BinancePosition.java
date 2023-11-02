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

    private final BigDecimal markPrice;

    private final BigDecimal liquidationPrice;

    private final BigDecimal notional;

    public BinancePosition(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("unrealizedProfit") BigDecimal unrealizedProfit,
            @JsonProperty("leverage") BigDecimal leverage,
            @JsonProperty("markPrice") BigDecimal markPrice,
            @JsonProperty("notional") BigDecimal notional,
            @JsonProperty("liquidationPrice") BigDecimal liquidationPrice,
            @JsonProperty("isolated") boolean isolated,
            @JsonProperty("positionSide") String positionSide,
            @JsonProperty("positionAmt") BigDecimal positionAmt,
            @JsonProperty("entryPrice") BigDecimal entryPrice) {
        this.symbol = symbol;
        this.unrealizedProfit = unrealizedProfit;
        this.leverage = leverage;
        this.markPrice = markPrice;
        this.isolated = isolated;
        this.positionSide = positionSide;
        this.positionAmt = positionAmt;
        this.entryPrice = entryPrice;
        this.liquidationPrice=liquidationPrice;
        this.notional=notional;
    }
}
