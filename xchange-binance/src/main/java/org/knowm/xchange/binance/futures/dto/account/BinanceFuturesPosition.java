package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;

import java.math.BigDecimal;

public class BinanceFuturesPosition {
    public final String symbol;
    public final BigDecimal initialMargin;
    public final BigDecimal maintMargin;
    public final BigDecimal unrealizedProfit;
    public final BigDecimal positionInitialMargin;
    public final BigDecimal openOrderInitialMargin;
    public final BigDecimal leverage;
    public final boolean isolated;
    public final BigDecimal entryPrice;
    public final BigDecimal maxNotional;
    public final PositionSide positionSide;
    public final BigDecimal positionAmt;
    public final long updateTime;

    public BinanceFuturesPosition(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("initialMargin") BigDecimal initialMargin,
            @JsonProperty("maintMargin") BigDecimal maintMargin,
            @JsonProperty("unrealizedProfit") BigDecimal unrealizedProfit,
            @JsonProperty("positionInitialMargin") BigDecimal positionInitialMargin,
            @JsonProperty("openOrderInitialMargin") BigDecimal openOrderInitialMargin,
            @JsonProperty("leverage") BigDecimal leverage,
            @JsonProperty("isolated") boolean isolated,
            @JsonProperty("entryPrice") BigDecimal entryPrice,
            @JsonProperty("maxNotional") BigDecimal maxNotional,
            @JsonProperty("positionSide") PositionSide positionSide,
            @JsonProperty("positionAmt") BigDecimal positionAmt,
            @JsonProperty("updateTime") long updateTime) {
        this.symbol = symbol;
        this.initialMargin = initialMargin;
        this.maintMargin = maintMargin;
        this.unrealizedProfit = unrealizedProfit;
        this.positionInitialMargin = positionInitialMargin;
        this.openOrderInitialMargin = openOrderInitialMargin;
        this.leverage = leverage;
        this.isolated = isolated;
        this.entryPrice = entryPrice;
        this.maxNotional = maxNotional;
        this.positionSide = positionSide;
        this.positionAmt = positionAmt;
        this.updateTime = updateTime;
    }
}
