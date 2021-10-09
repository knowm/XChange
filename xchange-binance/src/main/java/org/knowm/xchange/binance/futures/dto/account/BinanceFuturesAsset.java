package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BinanceFuturesAsset {
    public final String asset;
    public final BigDecimal walletBalance;
    public final BigDecimal unrealizedProfit;
    public final BigDecimal marginBalance;
    public final BigDecimal maintMargin;
    public final BigDecimal initialMargin;
    public final BigDecimal positionInitialMargin;
    public final BigDecimal openOrderInitialMargin;
    public final BigDecimal crossWalletBalance;
    public final BigDecimal crossUnPnl;
    public final BigDecimal availableBalance;
    public final BigDecimal maxWithdrawAmount;
    public final boolean marginAvailable;
    public final long updateTime;

    public BinanceFuturesAsset(
            @JsonProperty("asset") String asset,
            @JsonProperty("walletBalance") BigDecimal walletBalance,
            @JsonProperty("unrealizedProfit") BigDecimal unrealizedProfit,
            @JsonProperty("marginBalance") BigDecimal marginBalance,
            @JsonProperty("maintMargin") BigDecimal maintMargin,
            @JsonProperty("initialMargin") BigDecimal initialMargin,
            @JsonProperty("positionInitialMargin") BigDecimal positionInitialMargin,
            @JsonProperty("openOrderInitialMargin") BigDecimal openOrderInitialMargin,
            @JsonProperty("crossWalletBalance") BigDecimal crossWalletBalance,
            @JsonProperty("crossUnPnl") BigDecimal crossUnPnl,
            @JsonProperty("availableBalance") BigDecimal availableBalance,
            @JsonProperty("maxWithdrawAmount") BigDecimal maxWithdrawAmount,
            @JsonProperty("marginAvailable") boolean marginAvailable,
            @JsonProperty("updateTime") long updateTime) {
        this.asset = asset;
        this.walletBalance = walletBalance;
        this.unrealizedProfit = unrealizedProfit;
        this.marginBalance = marginBalance;
        this.maintMargin = maintMargin;
        this.initialMargin = initialMargin;
        this.positionInitialMargin = positionInitialMargin;
        this.openOrderInitialMargin = openOrderInitialMargin;
        this.crossWalletBalance = crossWalletBalance;
        this.crossUnPnl = crossUnPnl;
        this.availableBalance = availableBalance;
        this.maxWithdrawAmount = maxWithdrawAmount;
        this.marginAvailable = marginAvailable;
        this.updateTime = updateTime;
    }
}
