package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by cyril on 10-Oct-17.
 */
public class BinanceOrderBook {
  private Long lastUpdatedId;
  private BinanceOrderBookEntry[] bids;
  private BinanceOrderBookEntry[] asks;

  public BinanceOrderBook(
      @JsonProperty("lastUpdateId") Long lastUpdatedId,
      @JsonProperty("bids") Object[][] bids,
      @JsonProperty("asks") Object[][] asks) {
    this.lastUpdatedId = lastUpdatedId;
    if (bids != null && bids.length > 0) {
      this.bids = new BinanceOrderBookEntry[bids.length];
      for (int i = 0; i < bids.length; i++) {
        this.bids[i] = convertToOrderBookEntry(bids[i]);
      }
    } else {
      this.bids = null;
    }

    if (asks != null && asks.length > 0) {
      this.asks = new BinanceOrderBookEntry[asks.length];
      for (int i = 0; i < asks.length; i++) {
        this.asks[i] = convertToOrderBookEntry(asks[i]);
      }
    } else {
      this.asks = null;
    }
  }

  private static BinanceOrderBookEntry convertToOrderBookEntry(Object[] dataObject) {
    if (dataObject != null) {
      BigDecimal volume = new BigDecimal((String) dataObject[0]);
      BigDecimal price = new BigDecimal((String) dataObject[1]);

      return new BinanceOrderBookEntry(price, volume);

    }
    return null;
  }

  public Long getLastUpdatedId() {
    return lastUpdatedId;
  }

  public void setLastUpdatedId(Long lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }

  public BinanceOrderBookEntry[] getBids() {
    return bids;
  }

  public void setBids(BinanceOrderBookEntry[] bids) {
    this.bids = bids;
  }

  public BinanceOrderBookEntry[] getAsks() {
    return asks;
  }

  public void setAsks(BinanceOrderBookEntry[] asks) {
    this.asks = asks;
  }
}
