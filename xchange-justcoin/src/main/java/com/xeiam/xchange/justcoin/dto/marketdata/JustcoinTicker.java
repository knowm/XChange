package com.xeiam.xchange.justcoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public final class JustcoinTicker {

  private final String id;
  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal volume;
  private final int scale;

  /**
   * Constructor
   * 
   * @param id
   * @param high
   * @param low
   * @param volume
   * @param last
   * @param bid
   * @param ask
   * @param scale
   */
  public JustcoinTicker(final @JsonProperty("id") String id, final @JsonProperty("high") BigDecimal high, final @JsonProperty("low") BigDecimal low, final @JsonProperty("volume") BigDecimal volume,
      final @JsonProperty("last") BigDecimal last, final @JsonProperty("bid") BigDecimal bid, final @JsonProperty("ask") BigDecimal ask, final @JsonProperty("scale") int scale) {

    this.id = id;
    this.high = high;
    this.low = low;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.volume = volume;
    this.scale = scale;
  }

  public String getId() {

    return id;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public int getScale() {

    return scale;
  }

  @Override
  public String toString() {

    return "JustcoinTicker [id=" + id + ", last=" + last + ", high=" + high + ", low=" + low + ", bid=" + bid + ", ask=" + ask + ", volume=" + volume + ", scale=" + scale + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    JustcoinTicker other = (JustcoinTicker) obj;
    if (ask == null) {
      if (other.ask != null) {
        return false;
      }
    }
    else if (ask.compareTo(other.ask) != 0) {
      return false;
    }
    if (bid == null) {
      if (other.bid != null) {
        return false;
      }
    }
    else if (bid.compareTo(other.bid) != 0) {
      return false;
    }
    if (high == null) {
      if (other.high != null) {
        return false;
      }
    }
    else if (high.compareTo(other.high) != 0) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!id.equals(other.id)) {
      return false;
    }
    if (last == null) {
      if (other.last != null) {
        return false;
      }
    }
    else if (last.compareTo(other.last) != 0) {
      return false;
    }
    if (low == null) {
      if (other.low != null) {
        return false;
      }
    }
    else if (low.compareTo(other.low) != 0) {
      return false;
    }
    if (scale != other.scale) {
      return false;
    }
    if (volume == null) {
      if (other.volume != null) {
        return false;
      }
    }
    else if (volume.compareTo(other.volume) != 0) {
      return false;
    }
    return true;
  }

}
