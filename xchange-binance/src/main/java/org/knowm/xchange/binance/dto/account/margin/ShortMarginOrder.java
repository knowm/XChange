package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

public class ShortMarginOrder {

  private Long id;
  private BigDecimal price;
  private BigDecimal qty;
  private BigDecimal quoteQty;
  private CurrencyPair symbol;
  private Long time;

  public ShortMarginOrder(
          @JsonProperty("id") Long id,
          @JsonProperty("price") BigDecimal price,
          @JsonProperty("qty") BigDecimal qty,
          @JsonProperty("quoteQty") BigDecimal quoteQty,
          @JsonProperty("symbol") String symbol,
          @JsonProperty("time") Long time) {
    this.id = id;
    this.price = price;
    this.qty = qty;
    this.quoteQty = quoteQty;
    this.symbol = BinanceAdapters.adaptSymbol(symbol);
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public void setQty(BigDecimal qty) {
    this.qty = qty;
  }

  public BigDecimal getQuoteQty() {
    return quoteQty;
  }

  public void setQuoteQty(BigDecimal quoteQty) {
    this.quoteQty = quoteQty;
  }

  public CurrencyPair getSymbol() {
    return symbol;
  }

  public void setSymbol(CurrencyPair symbol) {
    this.symbol = symbol;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }
}
