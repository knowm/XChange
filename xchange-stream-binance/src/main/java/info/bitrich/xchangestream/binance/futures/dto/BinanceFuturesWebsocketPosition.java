package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BinanceFuturesWebsocketPosition {
    public final String symbol;
    public final BigDecimal positionAmt;
    public final BigDecimal accumulatedRealized;
    public final BigDecimal unrealizedProfit;
    public final BigDecimal entryPrice;
    public final String marginType;
    public final BigDecimal isolatedWallet;
    public final PositionSide positionSide;

    public BinanceFuturesWebsocketPosition(
            @JsonProperty("s") String symbol,
            @JsonProperty("pa") BigDecimal positionAmt,
            @JsonProperty("cr") BigDecimal accumulatedRealized,
            @JsonProperty("up") BigDecimal unrealizedProfit,
            @JsonProperty("ep") BigDecimal entryPrice,
            @JsonProperty("mt") String marginType,
            @JsonProperty("iw") BigDecimal isolatedWallet,
            @JsonProperty("ps") PositionSide positionSide
    ) {
        this.symbol = symbol;
        this.positionAmt = positionAmt;
        this.accumulatedRealized = accumulatedRealized;
        this.unrealizedProfit = unrealizedProfit;
        this.entryPrice = entryPrice;
        this.marginType = marginType;
        this.isolatedWallet = isolatedWallet;
        this.positionSide = positionSide;
    }

    @Override
    public String toString() {
        return "BinanceFuturesWebsocketPosition{" +
                "symbol='" + symbol + '\'' +
                ", positionAmt=" + positionAmt +
                ", accumulatedRealized=" + accumulatedRealized +
                ", unrealizedProfit=" + unrealizedProfit +
                ", entryPrice=" + entryPrice +
                ", marginType='" + marginType + '\'' +
                ", isolatedWallet=" + isolatedWallet +
                ", positionSide=" + positionSide +
                '}';
    }
}
