package org.knowm.xchange.koineks.dto.marketdata;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;

/** Created by semihunaldi on 05/12/2017 */
public abstract class BaseKoineksTicker {

  private final Currency shortCode;

  private final String name;

  private final Currency currency;

  private final BigDecimal current;

  private final String changeAmount;

  private final BigDecimal changePercentage;

  private final BigDecimal high;

  private final BigDecimal low;

  private final BigDecimal volume;

  private final BigDecimal ask;

  private final BigDecimal bid;

  private final String timestamp;

  public BaseKoineksTicker(
      Currency shortCode,
      String name,
      Currency currency,
      BigDecimal current,
      String changeAmount,
      BigDecimal changePercentage,
      BigDecimal high,
      BigDecimal low,
      BigDecimal volume,
      BigDecimal ask,
      BigDecimal bid,
      String timestamp) {
    this.shortCode = shortCode;
    this.name = name;
    this.currency = currency;
    this.current = current;
    this.changeAmount = changeAmount;
    this.changePercentage = changePercentage;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.ask = ask;
    this.bid = bid;
    this.timestamp = timestamp;
  }

  public Currency getShortCode() {
    return shortCode;
  }

  public String getName() {
    return name;
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigDecimal getCurrent() {
    return current;
  }

  public String getChangeAmount() {
    return changeAmount;
  }

  public BigDecimal getChangePercentage() {
    return changePercentage;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public String getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + " {"
        + "shortCode="
        + shortCode
        + ", name='"
        + name
        + '\''
        + ", currency="
        + currency
        + ", current="
        + current
        + ", changeAmount='"
        + changeAmount
        + '\''
        + ", changePercentage="
        + changePercentage
        + ", high="
        + high
        + ", low="
        + low
        + ", volume="
        + volume
        + ", ask="
        + ask
        + ", bid="
        + bid
        + ", timestamp="
        + timestamp
        + '}'
        + "\n\n";
  }
}
