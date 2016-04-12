package org.knowm.xchange.coinsetter.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Bid and ask pair for {@link CoinsetterPairedDepth}.
 */
public class CoinsetterPair {

  private final CoinsetterTrade bid;
  private final CoinsetterTrade ask;

  public CoinsetterPair(@JsonProperty("bid") CoinsetterTrade bid, @JsonProperty("ask") CoinsetterTrade ask) {

    this.bid = bid;
    this.ask = ask;
  }

  public CoinsetterTrade getBid() {

    return bid;
  }

  public CoinsetterTrade getAsk() {

    return ask;
  }

  @Override
  public String toString() {

    return "CoinsetterPair [bid=" + bid + ", ask=" + ask + "]";
  }

}
