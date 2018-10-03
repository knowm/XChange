package org.knowm.xchange.utils;

/** A mutable Ticker class used for HttpTemplate testing. */
public class DummyTicker {

  long last;
  long volume;

  public long getLast() {

    return last;
  }

  public void setLast(long last) {

    this.last = last;
  }

  public long getVolume() {

    return volume;
  }

  public void setVolume(long volume) {

    this.volume = volume;
  }
}
