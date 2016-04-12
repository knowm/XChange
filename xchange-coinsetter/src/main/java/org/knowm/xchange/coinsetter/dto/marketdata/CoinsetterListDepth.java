package org.knowm.xchange.coinsetter.dto.marketdata;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The latest Level 2 market data for the top n price levels of the book.
 */
public class CoinsetterListDepth {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;

  private final String exchangeId;
  private final int sequenceNumber;
  private final Long timeStamp;

  public CoinsetterListDepth(@JsonProperty("asks") BigDecimal[][] asks, @JsonProperty("bids") BigDecimal[][] bids,
      @JsonProperty("exchangeId") String exchangeId, @JsonProperty("sequenceNumber") int sequenceNumber, @JsonProperty("timeStamp") Long timeStamp) {

    this.asks = asks;
    this.bids = bids;
    this.exchangeId = exchangeId;
    this.sequenceNumber = sequenceNumber;
    this.timeStamp = timeStamp;
  }

  public BigDecimal[][] getAsks() {

    return asks;
  }

  public BigDecimal[][] getBids() {

    return bids;
  }

  public String getExchangeId() {

    return exchangeId;
  }

  public int getSequenceNumber() {

    return sequenceNumber;
  }

  public Long getTimeStamp() {

    return timeStamp;
  }

  @Override
  public String toString() {

    return "CoinsetterListDepth [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + ", exchangeId=" + exchangeId
        + ", sequenceNumber=" + sequenceNumber + ", timeStamp=" + timeStamp + "]";
  }

}
