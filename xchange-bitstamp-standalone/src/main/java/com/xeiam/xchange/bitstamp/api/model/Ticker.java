package com.xeiam.xchange.bitstamp.api.model;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 7:12 PM
 */
public class Ticker {

  double last;
  double high;
  double low;
  double volume;
  double bid;
  double ask;

  public double getLast() {

    return last;
  }

  public void setLast(double last) {

    this.last = last;
  }

  public double getHigh() {

    return high;
  }

  public void setHigh(double high) {

    this.high = high;
  }

  public double getLow() {

    return low;
  }

  public void setLow(double low) {

    this.low = low;
  }

  public double getVolume() {

    return volume;
  }

  public void setVolume(double volume) {

    this.volume = volume;
  }

  public double getBid() {

    return bid;
  }

  public void setBid(double bid) {

    this.bid = bid;
  }

  public double getAsk() {

    return ask;
  }

  public void setAsk(double ask) {

    this.ask = ask;
  }

  @Override
  public String toString() {

    return String.format("Ticker{last=%s, high=%s, low=%s, volume=%s, bid=%s, ask=%s}", last, high, low, volume, bid, ask);
  }
}
