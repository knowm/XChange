package org.knowm.xchange.therock.dto.marketdata;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.therock.utils.jackson.TheRockTickerDeserializer;

/** @author Matija Mazi */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonDeserialize(using = TheRockTickerDeserializer.class)
public class TheRockTicker implements Serializable {
  /** */
  private static final long serialVersionUID = -4662345648477360478L;

  private CurrencyPair fundId;
  private Date date;
  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal last;
  private BigDecimal volume;
  private BigDecimal volumeTraded;
  private BigDecimal open;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal close;

  /**
   * This constructor is called to create a TheRockTicker object
   *
   * @param fundId The trade type (BID side or ASK side)
   * @param date The depth of this trade
   * @param bid The price (either the bid or the ask)
   * @param ask The timestamp of the trade according to the exchange's server, null if not provided
   * @param last The id of the trade
   * @param volume The orderId of the maker in the trade
   * @param volumeTraded The orderId of the taker in the trade
   * @param open The orderId of the taker in the trade
   * @param high The orderId of the taker in the trade
   * @param low The orderId of the taker in the trade
   * @param close The orderId of the taker in the trade
   */
  public TheRockTicker(
      CurrencyPair fundId,
      Date date,
      BigDecimal bid,
      BigDecimal ask,
      BigDecimal last,
      BigDecimal volume,
      BigDecimal volumeTraded,
      BigDecimal open,
      BigDecimal high,
      BigDecimal low,
      BigDecimal close) {

    this.fundId = fundId;
    this.date = date;
    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.volume = volume;
    this.volumeTraded = volumeTraded;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
  }

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

  public static class Builder {
    protected CurrencyPair fundId;
    protected Date date;
    protected BigDecimal bid;
    protected BigDecimal ask;
    protected BigDecimal last;
    protected BigDecimal volume;
    protected BigDecimal volumeTraded;
    protected BigDecimal open;
    protected BigDecimal high;
    protected BigDecimal low;
    protected BigDecimal close;

    public static Builder from(TheRockTicker theRockTicker) {
      return new Builder()
          .fundId(theRockTicker.getFundId())
          .date(theRockTicker.getDate())
          .bid(theRockTicker.getBid())
          .ask(theRockTicker.getAsk())
          .last(theRockTicker.getLast())
          .volume(theRockTicker.getVolume())
          .volumeTraded(theRockTicker.getVolumeTraded())
          .open(theRockTicker.getOpen())
          .high(theRockTicker.getHigh())
          .low(theRockTicker.getLow())
          .close(theRockTicker.getClose());
    }

    public Builder fundId(CurrencyPair fundId) {

      this.fundId = fundId;
      return this;
    }

    public Builder date(Date date) {

      this.date = date;
      return this;
    }

    public Builder bid(BigDecimal bid) {

      this.bid = bid;
      return this;
    }

    public Builder ask(BigDecimal ask) {

      this.ask = ask;
      return this;
    }

    public Builder last(BigDecimal last) {

      this.last = last;
      return this;
    }

    public Builder volume(BigDecimal volume) {

      this.volume = volume;
      return this;
    }

    public Builder volumeTraded(BigDecimal volumeTraded) {

      this.volumeTraded = volumeTraded;
      return this;
    }

    public Builder open(BigDecimal open) {

      this.open = open;
      return this;
    }

    public Builder high(BigDecimal high) {

      this.high = high;
      return this;
    }

    public Builder low(BigDecimal low) {

      this.low = low;
      return this;
    }

    public Builder close(BigDecimal close) {

      this.close = close;
      return this;
    }

    public TheRockTicker build() {

      return new TheRockTicker(
          fundId, date, bid, ask, last, volume, volumeTraded, open, high, low, close);
    }
  }
}
