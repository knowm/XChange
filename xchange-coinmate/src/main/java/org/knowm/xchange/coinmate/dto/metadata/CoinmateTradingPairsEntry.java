package org.knowm.xchange.coinmate.dto.metadata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinmateTradingPairsEntry {

  @JsonProperty("name")
  private final String name;

  @JsonProperty("firstCurrency")
  private final String baseCurrency;

  @JsonProperty("secondCurrency")
  private final String counterCurrency;

  @JsonProperty("priceDecimals")
  private final int priceScale;

  @JsonProperty("lotDecimals")
  private final int volumeScale;

  @JsonProperty("minAmount")
  private final double minAmount;

  @JsonProperty("tradesWebSocketChannelId")
  private final String tradesWebSocketChannelId;

  @JsonProperty("orderBookWebSocketChannelId")
  private final String orderBookWebSocketChannelId;

  @JsonProperty("tradeStatisticsWebSocketChannelId")
  private final String tradeStatisticsWebSocketChannelId;

  @JsonCreator
  public CoinmateTradingPairsEntry(
      @JsonProperty("name") String name,
      @JsonProperty("firstCurrency") String baseCurrency,
      @JsonProperty("secondCurrency") String counterCurrency,
      @JsonProperty("priceDecimals") int priceScale,
      @JsonProperty("lotDecimals") int volumeScale,
      @JsonProperty("minAmount") double minAmount,
      @JsonProperty("tradesWebSocketChannelId") String tradesWebSocketChannelId,
      @JsonProperty("orderBookWebSocketChannelId") String orderBookWebSocketChannelId,
      @JsonProperty("tradeStatisticsWebSocketChannelId") String tradeStatisticsWebSocketChannelId) {
    this.name = name;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
    this.priceScale = priceScale;
    this.volumeScale = volumeScale;
    this.minAmount = minAmount;
    this.tradesWebSocketChannelId = tradesWebSocketChannelId;
    this.orderBookWebSocketChannelId = orderBookWebSocketChannelId;
    this.tradeStatisticsWebSocketChannelId = tradeStatisticsWebSocketChannelId;
  }

  public String getName() {
    return name;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getCounterCurrency() {
    return counterCurrency;
  }

  public int getPriceScale() {
    return priceScale;
  }

  public int getVolumeScale() {
    return volumeScale;
  }

  public double getMinAmount() {
    return minAmount;
  }

  public String getTradesWebSocketChannelId() {
    return tradesWebSocketChannelId;
  }

  public String getOrderBookWebSocketChannelId() {
    return orderBookWebSocketChannelId;
  }

  public String getTradeStatisticsWebSocketChannelId() {
    return tradeStatisticsWebSocketChannelId;
  }

  @Override
  public String toString() {
    return "CoinmateTradingPairs{"
        + "name='"
        + name
        + '\''
        + ", baseCurrency='"
        + baseCurrency
        + '\''
        + ", counterCurrency='"
        + counterCurrency
        + '\''
        + ", priceScale="
        + priceScale
        + ", volumeScale="
        + volumeScale
        + ", minAmount="
        + minAmount
        + ", tradesWebSocketChannelId='"
        + tradesWebSocketChannelId
        + '\''
        + ", orderBookWebSocketChannelId='"
        + orderBookWebSocketChannelId
        + '\''
        + ", tradeStatisticsWebSocketChannelId='"
        + tradeStatisticsWebSocketChannelId
        + '\''
        + '}';
  }
}
