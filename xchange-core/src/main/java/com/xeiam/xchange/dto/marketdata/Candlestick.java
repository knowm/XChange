package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

public class Candlestick {

  private Date date;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal volume;

  public Candlestick(Date date, BigDecimal high, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal volume) {

    this.date = date;
    this.high = high;
    this.low = low;
    this.open = open;
    this.close = close;
    this.volume = volume;
  }

  public Candlestick(Builder builder) {

    this.date = builder.getDate();
    this.high = builder.getHigh();
    this.low = builder.getLow();
    this.open = builder.getOpen();
    this.close = builder.getClose();
    this.volume = builder.getVolume();
  }

  public Date getDate() {

    return date;
  }

  public void setDate(Date date) {

    this.date = date;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public void setHigh(BigDecimal high) {

    this.high = high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public void setLow(BigDecimal low) {

    this.low = low;
  }

  public BigDecimal getOpen() {

    return open;
  }

  public void setOpen(BigDecimal open) {

    this.open = open;
  }

  public BigDecimal getClose() {

    return close;
  }

  public void setClose(BigDecimal close) {

    this.close = close;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public void setVolume(BigDecimal volume) {

    this.volume = volume;
  }

  @Override
  public String toString() {

    return "Candlestick [date=" + date + ", high=" + high + ", low=" + low + ", open=" + open + ", close=" + close + ", volume=" + volume + "]";
  }

  public static class Builder {

    private Date date;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal volume;

    public Builder() {

    }

    public Candlestick build() {

      return new Candlestick(this);
    }

    public Date getDate() {

      return date;
    }

    public Builder setDate(Date date) {

      this.date = date;
      return this;
    }

    public BigDecimal getHigh() {

      return high;
    }

    public Builder setHigh(BigDecimal high) {

      this.high = high;
      return this;
    }

    public BigDecimal getLow() {

      return low;
    }

    public Builder setLow(BigDecimal low) {

      this.low = low;
      return this;
    }

    public BigDecimal getOpen() {

      return open;
    }

    public Builder setOpen(BigDecimal open) {

      this.open = open;
      return this;
    }

    public BigDecimal getClose() {

      return close;
    }

    public Builder setClose(BigDecimal close) {

      this.close = close;
      return this;
    }

    public BigDecimal getVolume() {

      return volume;
    }

    public Builder setVolume(BigDecimal volume) {

      this.volume = volume;
      return this;
    }

    @Override
    public String toString() {

      return "Candlestick.Builder [date=" + date + ", high=" + high + ", low=" + low + ", open=" + open + ", close=" + close + ", volume=" + volume + "]";
    }

  }

}
