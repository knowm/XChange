package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.dto.account.BinanceMarginType;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BinanceFuturesPositionInformation {
    public final String symbol;
    public final BigDecimal positionAmt;
    public final BigDecimal entryPrice;
    public final BigDecimal markPrice;
    public final BigDecimal unRealizedProfit;
    public final BigDecimal liquidationPrice;
    public final BigDecimal leverage;
    public final BinanceMarginType marginType;
    public final BigDecimal isolatedMargin;
    public final boolean isAutoAddMargin;
    public final PositionSide positionSide;
    public final BigDecimal notional;
    public final BigDecimal isolatedWallet;
    public final long updateTime;

    public BinanceFuturesPositionInformation(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("positionAmt") BigDecimal positionAmt,
            @JsonProperty("entryPrice") BigDecimal entryPrice,
            @JsonProperty("markPrice") BigDecimal markPrice,
            @JsonProperty("unRealizedProfit") BigDecimal unRealizedProfit,
            @JsonProperty("liquidationPrice") BigDecimal liquidationPrice,
            @JsonProperty("leverage") BigDecimal leverage,
            @JsonProperty("marginType") BinanceMarginType marginType,
            @JsonProperty("isolatedMargin") BigDecimal isolatedMargin,
            @JsonProperty("isAutoAddMargin") boolean isAutoAddMargin,
            @JsonProperty("positionSide") PositionSide positionSide,
            @JsonProperty("notional") BigDecimal notional,
            @JsonProperty("isolatedWallet") BigDecimal isolatedWallet,
            @JsonProperty("updateTime") long updateTime) {
        this.symbol = symbol;
        this.positionAmt = positionAmt;
        this.entryPrice = entryPrice;
        this.markPrice = markPrice;
        this.unRealizedProfit = unRealizedProfit;
        this.liquidationPrice = liquidationPrice;
        this.leverage = leverage;
        this.marginType = marginType;
        this.isolatedMargin = isolatedMargin;
        this.isAutoAddMargin = isAutoAddMargin;
        this.positionSide = positionSide;
        this.notional = notional;
        this.isolatedWallet = isolatedWallet;
        this.updateTime = updateTime;
    }
}
