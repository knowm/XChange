package org.knowm.xchange.hitbtc.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("MarketDataSnapshotFullRefresh")
public class HitbtcSnapshotFullRefresh {

  private final long snapshotSeqNo;
  private final String symbol;
  private final String exchangeStatus;
  private final List<HitbtcStreamingOrder> ask;
  private final List<HitbtcStreamingOrder> bid;

  /**
   * Constructor
   *
   * @param snapshotSeqNo
   * @param symbol
   * @param exchangeStatus "working" if working
   * @param ask
   * @param bid
   */
  public HitbtcSnapshotFullRefresh(@JsonProperty("snapshotSeqNo") long snapshotSeqNo, @JsonProperty("symbol") String symbol,
      @JsonProperty("exchangeStatus") String exchangeStatus, @JsonProperty("ask") List<HitbtcStreamingOrder> ask,
      @JsonProperty("bid") List<HitbtcStreamingOrder> bid) {

    this.snapshotSeqNo = snapshotSeqNo;
    this.symbol = symbol;
    this.exchangeStatus = exchangeStatus;
    this.ask = ask;
    this.bid = bid;
  }

  public long getSnapshotSeqNo() {

    return snapshotSeqNo;
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

  @Override
  public String toString() {

    return "HitbtcSnapshotFullRefresh{" + "snapshotSeqNo=" + snapshotSeqNo + ", symbol='" + symbol + "', exchangeStatus='" + exchangeStatus
        + "', ask=" + ask + ", bid=" + bid + "}";
  }
}
