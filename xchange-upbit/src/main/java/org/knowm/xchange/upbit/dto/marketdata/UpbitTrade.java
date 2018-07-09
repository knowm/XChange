package org.knowm.xchange.upbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author interwater */
public class UpbitTrade {

  private final String market;
  private final String tradeDateUtc;
  private final String tradeTimeUtc;
  private final BigDecimal timestamp;
  private final BigDecimal tradePrice;
  private final BigDecimal tradeVolume;
  private final BigDecimal prevClosingPrice;
  private final BigDecimal changePrice;
  private final String askBid;

  /**
   * @param market
   * @param tradeDateUtc
   * @param tradeTimeUtc
   * @param timestamp
   * @param tradePrice
   * @param tradeVolume
   * @param prevClosingPrice
   * @param changePrice
   * @param askBid
   */
  public UpbitTrade(
      @JsonProperty("market") String market,
      @JsonProperty("trade_date_utc") String tradeDateUtc,
      @JsonProperty("trade_time_utc") String tradeTimeUtc,
      @JsonProperty("timestamp") BigDecimal timestamp,
      @JsonProperty("trade_price") BigDecimal tradePrice,
      @JsonProperty("trade_volume") BigDecimal tradeVolume,
      @JsonProperty("prev_closing_price") BigDecimal prevClosingPrice,
      @JsonProperty("change_price") BigDecimal changePrice,
      @JsonProperty("ask_bid") String askBid) {
    this.market = market;
    this.tradeDateUtc = tradeDateUtc;
    this.tradeTimeUtc = tradeTimeUtc;
    this.timestamp = timestamp;
    this.tradePrice = tradePrice;
    this.tradeVolume = tradeVolume;
    this.prevClosingPrice = prevClosingPrice;
    this.changePrice = changePrice;
    this.askBid = askBid;
  }

  public String getMarket() {
    return market;
  }

  public String getTradeDateUtc() {
    return tradeDateUtc;
  }

  public String getTradeTimeUtc() {
    return tradeTimeUtc;
  }

  public BigDecimal getTimestamp() {
    return timestamp;
  }

  public BigDecimal getTradePrice() {
    return tradePrice;
  }

  public BigDecimal getTradeVolume() {
    return tradeVolume;
  }

  public BigDecimal getPrevClosingPrice() {
    return prevClosingPrice;
  }

  public BigDecimal getChangePrice() {
    return changePrice;
  }

  public String getAskBid() {
    return askBid;
  }
}
