package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CryptowatchOrderBook {

  private final List<CryptowatchPublicOrder> asks;
  private final List<CryptowatchPublicOrder> bids;
  private final int seqNum;

  public CryptowatchOrderBook(
      @JsonProperty("asks") List<CryptowatchPublicOrder> asks,
      @JsonProperty("bids") List<CryptowatchPublicOrder> bids,
      @JsonProperty("seqNum") int seqNum) {
    this.asks = asks;
    this.bids = bids;
    this.seqNum = seqNum;
  }

  public List<CryptowatchPublicOrder> getAsks() {
    return asks;
  }

  public List<CryptowatchPublicOrder> getBids() {
    return bids;
  }

  public int getSeqNum() {
    return seqNum;
  }

  @Override
  public String toString() {
    return "CryptowatchOrderBook{" + "asks=" + asks + ", bids=" + bids + '}';
  }
}
