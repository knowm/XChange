package org.knowm.xchange.coinsetter.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The latest Level 2 market data for the top n price levels of the book.
 */
public class CoinsetterPairedDepth {

  private final CoinsetterPair[] topNBidAsks;
  private final int depth;
  private final String exchangeId;
  private final int sequenceNumber;

  public CoinsetterPairedDepth(@JsonProperty("topNBidAsks") CoinsetterPair[] topNBidAsks, @JsonProperty("depth") int depth,
      @JsonProperty("exchangeId") String exchangeId, @JsonProperty("sequenceNumber") int sequenceNumber) {

    this.topNBidAsks = topNBidAsks;
    this.depth = depth;
    this.exchangeId = exchangeId;
    this.sequenceNumber = sequenceNumber;
  }

  public CoinsetterPair[] getTopNBidAsks() {

    return topNBidAsks;
  }

  /**
   * Returns the depth of book returned.
   */
  public int getDepth() {

    return depth;
  }

  /**
   * Returns the exchange.
   */
  public String getExchangeId() {

    return exchangeId;
  }

  /**
   * Returns the sequence number (for COINSETTER exchange only) for synchronizing with
   * <a href="https://www.coinsetter.com/api/websockets/levels">incremental ticks stream</a> (See web sockets documentation)
   */
  public int getSequenceNumber() {

    return sequenceNumber;
  }

  @Override
  public String toString() {

    return "CoinsetterPairedDepth [topNBidAsks=" + Arrays.toString(topNBidAsks) + ", depth=" + depth + ", exchangeId=" + exchangeId
        + ", sequenceNumber=" + sequenceNumber + "]";
  }

}
