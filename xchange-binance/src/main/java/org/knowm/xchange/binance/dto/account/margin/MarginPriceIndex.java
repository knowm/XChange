package org.knowm.xchange.binance.dto.account.margin;

import java.math.BigDecimal;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.currency.CurrencyPair;

public class MarginPriceIndex {

  private Long calcTime;
  private BigDecimal price;
  private CurrencyPair symbol;

  public MarginPriceIndex(Long calcTime, BigDecimal price, String symbol) {
    this.calcTime = calcTime;
    this.price = price;
    this.symbol = BinanceAdapters.adaptSymbol(symbol);
  }

  public Long getCalcTime() {
    return calcTime;
  }

  public void setCalcTime(Long calcTime) {
    this.calcTime = calcTime;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public CurrencyPair getSymbol() {
    return symbol;
  }

  public void setSymbol(CurrencyPair symbol) {
    this.symbol = symbol;
  }
}
