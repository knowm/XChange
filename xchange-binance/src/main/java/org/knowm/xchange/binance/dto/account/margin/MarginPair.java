package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public class MarginPair {

  private Long id;
  private CurrencyPair symbol;
  private Currency base;
  private Currency quote;
  private Boolean isMarginTrade;
  private Boolean isBuyAllowed;
  private Boolean isSellAllowed;

  public MarginPair(
          @JsonProperty("id") Long id,
          @JsonProperty("symbol") String symbol,
          @JsonProperty("base") String base,
          @JsonProperty("quote") String quote,
          @JsonProperty("isMarginTrade") Boolean isMarginTrade,
          @JsonProperty("isBuyAllowed") Boolean isBuyAllowed,
          @JsonProperty("isSellAllowed") Boolean isSellAllowed) {
    this.id = id;
    this.symbol = BinanceAdapters.adaptSymbol(symbol);
    this.base = Currency.getInstance(base);
    this.quote = Currency.getInstance(quote);
    this.isMarginTrade = isMarginTrade;
    this.isBuyAllowed = isBuyAllowed;
    this.isSellAllowed = isSellAllowed;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CurrencyPair getSymbol() {
    return symbol;
  }

  public void setSymbol(CurrencyPair symbol) {
    this.symbol = symbol;
  }

  public Currency getBase() {
    return base;
  }

  public void setBase(Currency base) {
    this.base = base;
  }

  public Currency getQuote() {
    return quote;
  }

  public void setQuote(Currency quote) {
    this.quote = quote;
  }

  public Boolean getMarginTrade() {
    return isMarginTrade;
  }

  public void setMarginTrade(Boolean marginTrade) {
    isMarginTrade = marginTrade;
  }

  public Boolean getBuyAllowed() {
    return isBuyAllowed;
  }

  public void setBuyAllowed(Boolean buyAllowed) {
    isBuyAllowed = buyAllowed;
  }

  public Boolean getSellAllowed() {
    return isSellAllowed;
  }

  public void setSellAllowed(Boolean sellAllowed) {
    isSellAllowed = sellAllowed;
  }
}
