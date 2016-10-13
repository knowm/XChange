package org.knowm.xchange.coinsetter.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * cumulative available quantity in real time based on price level.
 */
public class CoinsetterLevel {

  private final int sequenceNumber;
  private final String side;
  private final int level;
  private final BigDecimal size;
  private final String exchangeId;
  private final long timeStamp;

  public CoinsetterLevel(@JsonProperty("sequenceNumber") int sequenceNumber, @JsonProperty("side") String side, @JsonProperty("level") int level, @JsonProperty("size") BigDecimal size,
      @JsonProperty("exchangeId") String exchangeId, @JsonProperty("timeStamp") long timeStamp) {

    this.sequenceNumber = sequenceNumber;
    this.side = side;
    this.level = level;
    this.size = size;
    this.exchangeId = exchangeId;
    this.timeStamp = timeStamp;
  }

  public int getSequenceNumber() {

    return sequenceNumber;
  }

  public String getSide() {

    return side;
  }

  public int getLevel() {

    return level;
  }

  public BigDecimal getSize() {

    return size;
  }

  public String getExchangeId() {

    return exchangeId;
  }

  public long getTimeStamp() {

    return timeStamp;
  }

  @Override
  public String toString() {

    return "CoinsetterLevel [sequenceNumber=" + sequenceNumber + ", side=" + side + ", level=" + level + ", size=" + size + ", exchangeId=" + exchangeId + ", timeStamp=" + timeStamp + "]";
  }

}
