package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class BinanceFuturesAccountInformation {
    public final int feeTier;
    public final boolean canTrade;
    public final boolean canDeposit;
    public final boolean canWithdraw;
    public final long updateTime;
    public final BigDecimal totalInitialMargin;
    public final BigDecimal totalMaintMargin;
    public final BigDecimal totalWalletBalance;
    public final BigDecimal totalUnrealizedProfit;
    public final BigDecimal totalMarginBalance;
    public final BigDecimal totalPositionInitialMargin;
    public final BigDecimal totalOpenOrderInitialMargin;
    public final BigDecimal totalCrossWalletBalance;
    public final BigDecimal totalCrossUnPnl;
    public final BigDecimal availableBalance;
    public final BigDecimal maxWithdrawAmount;
    public final List<BinanceFuturesAsset> assets;
    public final List<BinanceFuturesPosition> positions;

    public BinanceFuturesAccountInformation(
            @JsonProperty("feeTier") int feeTier,
            @JsonProperty("canTrade") boolean canTrade,
            @JsonProperty("canDeposit") boolean canDeposit,
            @JsonProperty("canWithdraw") boolean canWithdraw,
            @JsonProperty("updateTime") long updateTime,
            @JsonProperty("totalInitialMargin") BigDecimal totalInitialMargin,
            @JsonProperty("totalMaintMargin") BigDecimal totalMaintMargin,
            @JsonProperty("totalWalletBalance") BigDecimal totalWalletBalance,
            @JsonProperty("totalUnrealizedProfit") BigDecimal totalUnrealizedProfit,
            @JsonProperty("totalMarginBalance") BigDecimal totalMarginBalance,
            @JsonProperty("totalPositionInitialMargin") BigDecimal totalPositionInitialMargin,
            @JsonProperty("totalOpenOrderInitialMargin") BigDecimal totalOpenOrderInitialMargin,
            @JsonProperty("totalCrossWalletBalance") BigDecimal totalCrossWalletBalance,
            @JsonProperty("totalCrossUnPnl") BigDecimal totalCrossUnPnl,
            @JsonProperty("availableBalance") BigDecimal availableBalance,
            @JsonProperty("maxWithdrawAmount") BigDecimal maxWithdrawAmount,
            @JsonProperty("assets") List<BinanceFuturesAsset> assets,
            @JsonProperty("positions") List<BinanceFuturesPosition> positions) {
        this.feeTier = feeTier;
        this.canTrade = canTrade;
        this.canDeposit = canDeposit;
        this.canWithdraw = canWithdraw;
        this.updateTime = updateTime;
        this.totalInitialMargin = totalInitialMargin;
        this.totalMaintMargin = totalMaintMargin;
        this.totalWalletBalance = totalWalletBalance;
        this.totalUnrealizedProfit = totalUnrealizedProfit;
        this.totalMarginBalance = totalMarginBalance;
        this.totalPositionInitialMargin = totalPositionInitialMargin;
        this.totalOpenOrderInitialMargin = totalOpenOrderInitialMargin;
        this.totalCrossWalletBalance = totalCrossWalletBalance;
        this.totalCrossUnPnl = totalCrossUnPnl;
        this.availableBalance = availableBalance;
        this.maxWithdrawAmount = maxWithdrawAmount;
        this.assets = assets;
        this.positions = positions;
    }
}
