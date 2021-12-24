package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BinanceFuturesIncomeHistoryRecord {
    public enum Type {
        TRANSFER,
        WELCOME_BONUS,
        REALIZED_PNL,
        FUNDING_FEE,
        COMMISSION,
        INSURANCE_CLEAR,
        REFERRAL_KICKBACK,
        COMMISSION_REBATE,
        DELIVERED_SETTELMENT,
        COIN_SWAP_DEPOSIT,
        COIN_SWAP_WITHDRAW
    }
    public final String symbol;
    public final Type incomeType;
    public final BigDecimal income;
    public final String asset;
    public final long time;
    public final String info;
    public final long tranId;
    public final String tradeId;

    public BinanceFuturesIncomeHistoryRecord(@JsonProperty("symbol") String symbol,
                                             @JsonProperty("incomeType") Type incomeType,
                                             @JsonProperty("income") BigDecimal income,
                                             @JsonProperty("asset") String asset,
                                             @JsonProperty("time") long time,
                                             @JsonProperty("info") String info,
                                             @JsonProperty("tranId") long tranId,
                                             @JsonProperty("tradeId") String tradeId) {
        this.symbol = symbol;
        this.incomeType = incomeType;
        this.income = income;
        this.asset = asset;
        this.time = time;
        this.info = info;
        this.tranId = tranId;
        this.tradeId = tradeId;
    }
}