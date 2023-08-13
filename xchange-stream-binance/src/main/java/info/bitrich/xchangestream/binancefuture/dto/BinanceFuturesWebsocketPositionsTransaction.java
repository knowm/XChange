package info.bitrich.xchangestream.binancefuture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BinanceFuturesWebsocketPositionsTransaction {

  private final String symbol;
  private final BigDecimal positionAmount;
  private final BigDecimal entryPrice;
  private final BigDecimal accumulatedRealized;
  private final BigDecimal unrealizedPnl;
  private final String marginType;
  private final BigDecimal isolatedWallet;
  private final String positionSide;

  BinanceFuturesWebsocketPositionsTransaction(
      @JsonProperty("s") String symbol,
      @JsonProperty("pa") BigDecimal positionAmount,
      @JsonProperty("ep") BigDecimal entryPrice,
      @JsonProperty("cr") BigDecimal accumulatedRealized,
      @JsonProperty("up") BigDecimal unrealizedPnl,
      @JsonProperty("mt") String marginType,
      @JsonProperty("iw") BigDecimal isolatedWallet,
      @JsonProperty("ps") String positionSide
  ) {
    this.symbol = symbol;
    this.positionAmount = positionAmount;
    this.entryPrice = entryPrice;
    this.accumulatedRealized = accumulatedRealized;
    this.unrealizedPnl = unrealizedPnl;
    this.marginType = marginType;
    this.isolatedWallet = isolatedWallet;
    this.positionSide = positionSide;
  }

  public BigDecimal getIsolatedWallet() {
    return isolatedWallet;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getPositionAmount() {
    return positionAmount;
  }

  public BigDecimal getEntryPrice() {
    return entryPrice;
  }

  public BigDecimal getAccumulatedRealized() {
    return accumulatedRealized;
  }

  public BigDecimal getUnrealizedPnl() {
    return unrealizedPnl;
  }

  public String getMarginType() {
    return marginType;
  }

  public String getPositionSide() {
    return positionSide;
  }
}
