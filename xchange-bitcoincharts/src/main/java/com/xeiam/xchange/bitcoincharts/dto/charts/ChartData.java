package com.xeiam.xchange.bitcoincharts.dto.charts;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ChartData {

  private final String date;
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal close;
  private final BigDecimal volume;
  private final BigDecimal volumeCurrency;
  private final BigDecimal weightedPrice;

  public ChartData(ArrayList rawData) {
    this.date = String.valueOf(rawData.get(0));
    this.open = new BigDecimal(String.valueOf(rawData.get(1)));
    this.high = new BigDecimal(String.valueOf(rawData.get(2)));
    this.low = new BigDecimal(String.valueOf(rawData.get(3)));
    this.close = new BigDecimal(String.valueOf(rawData.get(4)));
    this.volume = new BigDecimal(String.valueOf(rawData.get(5)));
    this.volumeCurrency = new BigDecimal(String.valueOf(rawData.get(6)));
    this.weightedPrice = new BigDecimal(String.valueOf(rawData.get(7)));
  }

  public String getDate() {
    return date;
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

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeCurrency() {
    return volumeCurrency;
  }

  public BigDecimal getWeightedPrice() {
    return weightedPrice;
  }

  @Override
  public String toString() {
    return "ChartData{" + "date=" + date + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", volumeCurrency=" + volumeCurrency + ", weightedPrice=" + weightedPrice + '}';
  }
}
