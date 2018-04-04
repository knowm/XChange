package org.knowm.xchange.therock.dto.marketdata;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** @author Matija Mazi */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class TheRockTicker {

  @JsonDeserialize(using = CurrencyPairDeserializer.class)
  private CurrencyPair fundId;

  private Date date;

  private BigDecimal bid, ask, last, volume, volumeTraded, open, high, low, close;

  public CurrencyPair getFundId() {
    return fundId;
  }

  public Date getDate() {
    return date;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeTraded() {
    return volumeTraded;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getClose() {
    return close;
  }

  @Override
  public String toString() {
    return String.format(
        "TheRockTicker{currencyPair=%s, date=%s, bid=%s, ask=%s, last=%s, volume=%s, volumeTraed=%s, open=%s, high=%s, low=%s, close=%s}",
        fundId, date, bid, ask, last, volume, volumeTraded, open, high, low, close);
  }
}
