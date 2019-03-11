package org.knowm.xchange.btcmarkets.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.utils.jackson.UnixTimestampDeserializer;

public class BTCMarketsOrderBook {

  private String currency;

  private String instrument;

  @JsonDeserialize(using = UnixTimestampDeserializer.class)
  private Date timestamp;

  /** (price, amount) pairs in units of 10e-8 */
  private List<BigDecimal[]> bids;

  /** (price, amount) pairs in units of 10e-8 */
  private List<BigDecimal[]> asks;

  public String getCurrency() {
    return currency;
  }

  public String getInstrument() {
    return instrument;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public List<BigDecimal[]> getBids() {
    return bids;
  }

  public List<BigDecimal[]> getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return String.format(
        "BTCMarketsOrderBook{currency='%s', instrument='%s', timestamp=%s, bids=%d, asks=%d}",
        currency, instrument, timestamp, bids.size(), asks.size());
  }
}
