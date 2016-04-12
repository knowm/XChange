package org.knowm.xchange.hitbtc.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("MarketDataIncrementalRefresh")
public class HitbtcIncrementalRefresh {

  private final long seqNo;
  private final String symbol;
  private final String exchangeStatus;
  private final List<HitbtcStreamingOrder> ask;
  private final List<HitbtcStreamingOrder> bid;
  private final List<HitbtcStreamingTrade> trade;

  /**
   * Constructor
   *
   * @param seqNo
   * @param symbol
   * @param exchangeStatus "working" if working
   * @param ask
   * @param bid
   * @param trade
   */
  public HitbtcIncrementalRefresh(@JsonProperty("seqNo") long seqNo, @JsonProperty("symbol") String symbol,
      @JsonProperty("exchangeStatus") String exchangeStatus, @JsonProperty("ask") List<HitbtcStreamingOrder> ask,
      @JsonProperty("bid") List<HitbtcStreamingOrder> bid, @JsonProperty("trade") List<HitbtcStreamingTrade> trade) {

    this.seqNo = seqNo;
    this.symbol = symbol;
    this.exchangeStatus = exchangeStatus;
    this.ask = ask;
    this.bid = bid;
    this.trade = trade;
  }

  public long getSeqNo() {

    return seqNo;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getExchangeStatus() {

    return exchangeStatus;
  }

  public List<HitbtcStreamingOrder> getAsk() {

    return ask;
  }

  public List<HitbtcStreamingOrder> getBid() {

    return bid;
  }

  public List<HitbtcStreamingTrade> getTrade() {

    return trade;
  }

  @Override
  public String toString() {

    return "HitbtcIncrementalRefresh{" + "seqNo=" + seqNo + ", symbol='" + symbol + "', exchangeStatus='" + exchangeStatus + "', ask=" + ask
        + ", bid=" + bid + ", trade=" + trade + "}";
  }
}
